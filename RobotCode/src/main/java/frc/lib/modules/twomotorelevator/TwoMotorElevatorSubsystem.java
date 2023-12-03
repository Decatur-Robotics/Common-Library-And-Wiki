package frc.lib.modules.twomotorelevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.ITeamTalon;
import frc.lib.core.motors.TeamTalonFX;

public class TwoMotorElevatorSubsystem extends SubsystemBase {

    private ITeamTalon elevatorMotorRight, elevatorMotorLeft;
    private double targetPosition;

	private double minimumElevatorPosition;
	private double maximumElevatorPosition;

    private AnalogPotentiometer potentiometer;

    private boolean targetOverridden;

    private double power;

	private String reason;

    private DigitalInput elevatorLimitSwitch;

	private double elevatorDeadband;
	private double maxElevatorMotorPower;
	private double maxElevatorPowerChange;

    private PIDController pid;

    public TwoMotorElevatorSubsystem(double minimumElevatorPosition, double maximumElevatorPosition, double startingElevatorPosition, double kP, double kI, double kD, double elevatorDeadband, double maxElevatorMotorPower, double maxElevatorPowerChange) {
        elevatorMotorRight = new TeamTalonFX("Subsystem.Elevator.elevatorMotorRight",
                TwoMotorElevatorConstants.RIGHT_ELEVATOR_MOTOR);
        elevatorMotorLeft = new TeamTalonFX("Subsystem.Elevator.elevatorMotorLeft",
				TwoMotorElevatorConstants.LEFT_ELEVATOR_MOTOR);

		// Configure right motor
        elevatorMotorRight.resetEncoder();
        elevatorMotorRight.enableVoltageCompensation(true);
        elevatorMotorRight.setInverted(false);
        elevatorMotorRight.setNeutralMode(NeutralMode.Brake);

		// Configure left motor
        elevatorMotorLeft.resetEncoder();
        elevatorMotorLeft.enableVoltageCompensation(true);
        elevatorMotorLeft.setInverted(true);
        elevatorMotorLeft.setNeutralMode(NeutralMode.Brake);
        elevatorMotorLeft.follow(elevatorMotorRight);

        elevatorLimitSwitch = new DigitalInput(TwoMotorElevatorConstants.ELEVATOR_LIMIT_SWITCH);

        potentiometer = new AnalogPotentiometer(TwoMotorElevatorConstants.ELEVATOR_POTENTIOMETER, 100);

		this.minimumElevatorPosition = minimumElevatorPosition;
		this.maximumElevatorPosition = maximumElevatorPosition;

		targetPosition = startingElevatorPosition;

		power = 0;

		this.elevatorDeadband = elevatorDeadband;
		this.maxElevatorMotorPower = maxElevatorMotorPower;
		this.maxElevatorPowerChange = maxElevatorPowerChange;

		pid = new PIDController(kP, kI, kD);
    }

	// Set a position for elevator to move to
    public void setTargetPosition(double newTargetPosition) {
        targetPosition = newTargetPosition;
    }

	// Directly set the power for the elevator
    public void setPower(double power) {
        this.power = power;
		reason = "Manual override";
	}

	// Override the target position to allow manual control
	public void setTargetOverridden(boolean targetOverridden) {
		this.targetOverridden = targetOverridden;
	}

	public boolean getTargetOverridden() {
		return targetOverridden;
	}

	// Ensure elevator does not move faster than its maximum speed
	private double getCappedPower(double input) {
        return Math.min(maxElevatorMotorPower,
                Math.max(input, -maxElevatorMotorPower));
    }

	// Ensure elevator power does not change too quickly
	private double getRampedPower(double input) {
		double currentPower = elevatorMotorRight.get();
		
		if (input < currentPower) {
            return Math.max(input, currentPower - maxElevatorPowerChange);
        }
        else if (input > currentPower) {
            return Math.min(input, currentPower + maxElevatorPowerChange);
        } 
		else {
            return input;
        }
	}

    public void periodic() {
		// If manual control is off and elevator is outside the target position, move elevator to target position
        if (!targetOverridden) {
            if (!isInTarget()) {
                power = pid.calculate(potentiometer.get(), targetPosition);
            } 
			else {
                power = 0;
            }

			reason = "Moving to position";
        }
		power *= maxElevatorMotorPower;

		power = getRampedPower(power);

		// Stop elevator from moving too far down or up
		if ((potentiometer.get() > maximumElevatorPosition && power > 0) 
				|| (potentiometer.get() < minimumElevatorPosition && power < 0) 
				|| (elevatorLimitSwitch.get() && power < 0)) {
			power = 0;
		}

		power = getCappedPower(power);

		elevatorMotorRight.set(power, reason);
		elevatorMotorLeft.set(power, reason);
    }

    public boolean isInTarget() {
        double delta = targetPosition - potentiometer.get();
        return Math.abs(delta) < elevatorDeadband;
    }

    public void resetTarget() {
        targetPosition = potentiometer.get();
    }

}
