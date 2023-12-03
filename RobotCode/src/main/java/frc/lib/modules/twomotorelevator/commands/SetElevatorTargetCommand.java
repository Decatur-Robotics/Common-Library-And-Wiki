package frc.lib.modules.twomotorelevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.twomotorelevator.TwoMotorElevatorSubsystem;

public class SetElevatorTargetCommand extends CommandBase {
    TwoMotorElevatorSubsystem elevator;

    private double targetPosition;
    private boolean wait;

    public SetElevatorTargetCommand(double targetPosition, TwoMotorElevatorSubsystem elevator) {
        this.elevator = elevator;
        this.targetPosition = targetPosition;
        this.wait = false;
        addRequirements(elevator);
    }

    public SetElevatorTargetCommand(double targetPosition, boolean wait, TwoMotorElevatorSubsystem elevator) {
        this.elevator = elevator;
        this.targetPosition = targetPosition;
        this.wait = wait;
        addRequirements(elevator);
    }

    public void initialize() {
        elevator.setTargetPosition(targetPosition);
    }

    public boolean isFinished() {
        return !wait || elevator.isInTarget();
    }
}
