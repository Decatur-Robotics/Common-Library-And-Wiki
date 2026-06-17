package frc.robot.subsystems.superstructure;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LEDs.LED;
import frc.robot.subsystems.LEDs.LEDConstants;
import frc.robot.subsystems.superstructure.arm.Arm;
import frc.robot.subsystems.superstructure.elevator.Elevator;
import frc.robot.subsystems.superstructure.intake.Intake;
import frc.robot.subsystems.superstructure.intake.IntakeConstants;
import frc.robot.subsystems.superstructure.wrist.Wrist;
import frc.robot.util.SuperstructureState;

public class Superstructure {
    private Elevator elevator;
    private Arm arm;
    private Wrist wrist;
    private Intake intake;
    private LED led;

    private SuperstructureState targetState;
    
    public Superstructure(Elevator elevator, Arm arm, Intake intake, Wrist wrist, LED led) {
        this.elevator = elevator;
        this.arm = arm;
        this.wrist = wrist;
        this.intake = intake;
        this.led = led;

        targetState = SuperstructureConstants.CORAL_STOWED_STATE;
        led.setAllPixels(LEDConstants.BLUE);
    }

    public void setState(SuperstructureState targetState) {
        this.targetState = targetState.copyInstance();

        elevator.setPosition(targetState.elevatorPosition);
        arm.setPosition(targetState.armPosition);
        wrist.setVolts(targetState.wristCurrent);
        intake.setVelocity(targetState.intakeVelocity);
    }

    // Is at targets

    public boolean isAtTargetState() {
        return isElevatorAtTargetPosition() &&
                isArmAtTargetPosition() &&
                isWristAtTargetPosition();
    }

    public boolean isElevatorAtTargetPosition() {
        if (Math.abs(getActualElevatorPosition() - targetState.elevatorPosition) > SuperstructureConstants.ELEVATOR_ERROR_MARGIN) {
            return false || Robot.isSimulation();
        }

        return true;
    }

    public boolean isArmAtTargetPosition() {
        if (Math.abs(getActualArmPosition() - targetState.armPosition) > SuperstructureConstants.ARM_ERROR_MARGIN) {
            return false || Robot.isSimulation();
        }

        return true;
    }

    public boolean isWristAtTargetPosition() {
        return wrist.isSlammed() || Robot.isSimulation();
    }

    public SuperstructureState getGoalState() {
        return targetState;
    }

    // Get actual states

    public SuperstructureState getActualState() {
        return new SuperstructureState(getActualElevatorPosition(), getActualArmPosition(), 
            getActualWristCurrent(), getActualIntakeVelocity());
    }

    public double getActualElevatorPosition() {
        return elevator.getPosition();
    }

    public double getActualArmPosition() {
        return arm.getPosition();
    }

    public double getActualWristCurrent() {
        return wrist.getCurrent();
    }

    public double getActualIntakeVelocity() {
        return intake.getVelocity();
    }

    public void setElevatorPosition(double position) {
        targetState.elevatorPosition = position;
        targetState.elevatorPosition = position;
        
        elevator.setPosition(position);
    }

    public void setArmPosition(double position) {
        targetState.armPosition = position;
        targetState.armPosition = position;
        
        arm.setPosition(position);
    }

    public void setWristCurrent(double current) {
        targetState.wristCurrent = current;
        targetState.wristCurrent = current;
        
        wrist.setCurrent(current);
    }

    public void setIntakeVelocity(double velocity) {
        targetState.intakeVelocity = velocity;
        targetState.intakeVelocity = velocity;
        
        intake.setVelocity(velocity);
    }

     public Command intakeCoralGroundCommand() {
        return intakeCoralCommand(() -> SuperstructureConstants.CORAL_GROUND_INTAKING_STATE, 
            SuperstructureConstants.CORAL_STOWED_STATE);
    }

    public Command intakeCoralHumanPlayerCommand() {
        return intakeCoralCommand(() -> SuperstructureConstants.CORAL_HUMAN_PLAYER_INTAKING_STATE, 
            SuperstructureConstants.CORAL_STOWED_STATE);
    }

    public Command intakeCoralCommand(Supplier<SuperstructureState> intakingState, SuperstructureState stowedState) {
        Debouncer debouncer = new Debouncer(IntakeConstants.CORAL_STALL_DEBOUNCE_TIME, DebounceType.kRising);

        return Commands.sequence(
            Commands.runOnce(() -> {
                debouncer.calculate(false);
                setState(intakingState.get());
            }, elevator, arm, wrist, intake),
            Commands.waitUntil(() -> (debouncer.calculate((
                (Math.abs(intake.getFilteredCurrentLeft()) > IntakeConstants.CORAL_STALL_CURRENT)
                || (Math.abs(intake.getFilteredCurrentRight()) > IntakeConstants.CORAL_STALL_CURRENT))
                && !Robot.isSimulation()) 
                || Robot.isSimulation() && true)),
            Commands.runOnce(() -> led.flashAllPixels(LEDConstants.BLUE, 5), led),
            Commands.waitSeconds(0.1)
        )
        .finallyDo(
            () -> setState(stowedState)
        );
    }

     // Scoring commands

     public Command scoreCoralL1Command(Supplier<Boolean> isNearTargetPose, Supplier<Boolean> isAtTargetPose, 
     Supplier<Boolean> overrideNearPose, Supplier<Boolean> overrideAtPose) {
 return scoreEjectCommand(SuperstructureConstants.STAGE_L1_STATE, SuperstructureConstants.EJECT_L1_STATE,
     SuperstructureConstants.CORAL_STOWED_STATE, 
     isNearTargetPose, isAtTargetPose, () -> true, overrideAtPose);
}

public Command scoreCoralL2Command(Supplier<Boolean> isNearTargetPose, Supplier<Boolean> isAtTargetPose, 
     Supplier<Boolean> overrideNearPose, Supplier<Boolean> overrideAtPose) {
 return scorePlaceCommand(SuperstructureConstants.TRAVEL_L2_STATE, SuperstructureConstants.STAGE_L2_STATE, 
     SuperstructureConstants.SECOND_STAGE_L2_STATE,
     SuperstructureConstants.PLACE_L2_STATE, SuperstructureConstants.RETRACT_L2_STATE, SuperstructureConstants.CORAL_STOWED_STATE, 
     isNearTargetPose, isAtTargetPose, 
     overrideNearPose, overrideAtPose);
}

public Command scoreCoralL3Command(Supplier<Boolean> isNearTargetPose, Supplier<Boolean> isAtTargetPose, 
     Supplier<Boolean> overrideNearPose, Supplier<Boolean> overrideAtPose) {
 return scorePlaceCommand(SuperstructureConstants.TRAVEL_L3_STATE, SuperstructureConstants.STAGE_L3_STATE, 
     SuperstructureConstants.SECOND_STAGE_L3_STATE,
     SuperstructureConstants.PLACE_L3_STATE, SuperstructureConstants.RETRACT_L3_STATE, SuperstructureConstants.CORAL_STOWED_STATE, 
     isNearTargetPose, isAtTargetPose, 
     overrideNearPose, overrideAtPose);
}

public Command scoreCoralL4Command(Supplier<Boolean> isNearTargetPose, Supplier<Boolean> isAtTargetPose, 
     Supplier<Boolean> overrideNearPose, Supplier<Boolean> overrideAtPose) {
 return scorePlaceCommand(SuperstructureConstants.TRAVEL_L4_STATE, SuperstructureConstants.STAGE_L4_STATE, 
     SuperstructureConstants.SECOND_STAGE_L4_STATE,
     SuperstructureConstants.PLACE_L4_STATE, SuperstructureConstants.RETRACT_L4_STATE, SuperstructureConstants.CORAL_STOWED_STATE, 
     isNearTargetPose, isAtTargetPose, 
     overrideNearPose, overrideAtPose);
}

public Command scorePlaceCommand(SuperstructureState travelState, SuperstructureState stagingState, 
SuperstructureState secondStagingState,
SuperstructureState placingState, SuperstructureState retractingState, SuperstructureState stowedState, 
Supplier<Boolean> isNearTargetPose, Supplier<Boolean> isAtTargetPose, 
Supplier<Boolean> overrideNearPose, Supplier<Boolean> overrideAtPose) {
return Commands.sequence(
Commands.waitUntil(() -> (isNearTargetPose.get() || overrideNearPose.get())),
Commands.runOnce(() -> setState(travelState),
    elevator, arm, wrist, intake),
Commands.waitUntil(() -> isElevatorAtTargetPosition()),
Commands.runOnce(() -> setState(stagingState),
    elevator, arm, wrist, intake),
Commands.waitUntil(() -> (isAtTargetState() && (isAtTargetPose.get() || overrideAtPose.get()))),
Commands.runOnce(() -> setState(secondStagingState),
    elevator, arm, wrist, intake),
Commands.waitUntil(() -> (isAtTargetState() && (isAtTargetPose.get() || overrideAtPose.get()))),
Commands.runOnce(() -> setState(placingState),
    elevator, arm, wrist, intake),
Commands.race(Commands.waitUntil(() -> isAtTargetState()), Commands.waitSeconds(0.5)),
Commands.runOnce(() -> {
    setState(retractingState);
    led.flashAllPixels(LEDConstants.YELLOW, 5);
},
    elevator, arm, wrist, intake, led),
Commands.waitUntil(() -> isAtTargetState())
)
.finallyDo(() -> {
setState(stowedState);
});
}

public Command scoreEjectCommand(SuperstructureState stagingState, SuperstructureState ejectingState, 
            SuperstructureState stowedState, 
            Supplier<Boolean> isNearTargetPose, Supplier<Boolean> isAtTargetPose, 
            Supplier<Boolean> overrideNearPose, Supplier<Boolean> overrideAtPose) {
        return Commands.sequence(
            Commands.waitUntil(() -> (isNearTargetPose.get() || overrideNearPose.get())),
            Commands.runOnce(() -> setState(stagingState),
                elevator, arm, wrist, intake),
            Commands.waitUntil(() -> (isAtTargetState() && isAtTargetPose.get()) || overrideAtPose.get()),
            Commands.run(() -> {
                setState(ejectingState);
                led.flashAllPixels(LEDConstants.YELLOW, 5);
            },
                elevator, arm, wrist, intake, led)
        )
        .finallyDo(() -> {
            setState(stowedState);
        });
    }

public Command dealgifyLowCommand() {
    return dealgifyCommand(SuperstructureConstants.LOW_DEALGIFY_STATE,
        SuperstructureConstants.CORAL_STOWED_STATE);
}

public Command dealgifyHighCommand() {
    return dealgifyCommand(SuperstructureConstants.HIGH_DEALGIFY_STATE,
        SuperstructureConstants.CORAL_STOWED_STATE);
}

public Command dealgifyCommand(SuperstructureState stagingState,
        SuperstructureState stowedState) {
    return Commands.sequence(
        Commands.run(() -> setState(stagingState), elevator, arm, wrist, intake)
    )
    .finallyDo(() -> {
        setState(stowedState);
    });
}

}
