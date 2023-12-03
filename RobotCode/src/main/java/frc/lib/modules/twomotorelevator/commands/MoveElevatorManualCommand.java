package frc.lib.modules.twomotorelevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.twomotorelevator.TwoMotorElevatorConstants;
import frc.lib.modules.twomotorelevator.TwoMotorElevatorSubsystem;
import java.util.function.DoubleSupplier;

public class MoveElevatorManualCommand extends CommandBase {

    private TwoMotorElevatorSubsystem elevator;
    private DoubleSupplier power;

    public MoveElevatorManualCommand(DoubleSupplier power, TwoMotorElevatorSubsystem elevator) {
        this.elevator = elevator;
        this.power = power;

        addRequirements(elevator);
    }

	private double calculateDeadZonedPower(double input) {
		return Math.abs(input) > TwoMotorElevatorConstants.ELEVATOR_JOYSTICK_DEADBAND ? input : 0;
	}

    public void execute() {
		if(elevator.getTargetOverridden()) {
        	elevator.setPower(Math.pow(calculateDeadZonedPower(power.getAsDouble()), TwoMotorElevatorConstants.ELEVATOR_POWER_EXPONENT));
		}
	}

}