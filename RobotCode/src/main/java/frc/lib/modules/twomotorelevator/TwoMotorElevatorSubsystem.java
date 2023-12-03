package frc.lib.modules.twomotorelevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.ITeamTalon;
import frc.lib.core.motors.TeamTalonFX;

public class TwoMotorElevatorSubsystem extends SubsystemBase {

    public ITeamTalon elevatorMotorRight, elevatorMotorLeft;
    public double targetPosition = TwoMotorElevatorConstants.MINIMUM_ELEVATOR_POSITION;

    public AnalogPotentiometer potentiometer;

    public boolean clawThresholdOverridden;

    public boolean targetOverridden;

    public double power;

	public String reason;

    public DigitalInput elevatorLimitSwitch;

    private static final double kP = 1; 
    private static final double kI = 0;
    private static final double kD = 0.01; 

    PIDController pid = new PIDController(kP, kI, kD);

    public TwoMotorElevatorSubsystem() {
        elevatorMotorRight = new TeamTalonFX("Subsystem.Elevator.elevatorMotorRight",
                TwoMotorElevatorConstants.RIGHT_ELEVATOR_MOTOR);
        elevatorMotorLeft = new TeamTalonFX("Subsystem.Elevator.elevatorMotorLeft",
				TwoMotorElevatorConstants.LEFT_ELEVATOR_MOTOR);

        elevatorMotorRight.resetEncoder();
        elevatorMotorRight.enableVoltageCompensation(true);
        elevatorMotorRight.setInverted(false);
        elevatorMotorRight.setNeutralMode(NeutralMode.Brake);

        elevatorMotorLeft.resetEncoder();
        elevatorMotorLeft.enableVoltageCompensation(true);
        elevatorMotorLeft.setInverted(true);
        elevatorMotorLeft.setNeutralMode(NeutralMode.Brake);
        elevatorMotorLeft.follow(elevatorMotorRight);

        elevatorLimitSwitch = new DigitalInput(TwoMotorElevatorConstants.ELEVATOR_LIMIT_SWITCH);

        potentiometer = new AnalogPotentiometer(TwoMotorElevatorConstants.ELEVATOR_POTENTIOMETER, 100);

		power = 0;
    }

    public void setTargetPosition(double newTargetPosition) {
        targetPosition = newTargetPosition;
    }

    public void setPower(double power) {
        this.power = power;
		reason = "Manual override";
	}

	private double getCappedPower(double input) {
        return Math.min(TwoMotorElevatorConstants.MAX_ELEVATOR_MOTOR_POWER,
                Math.max(input, -TwoMotorElevatorConstants.MAX_ELEVATOR_MOTOR_POWER));
    }

	private double getRampedPower(double input) {
		double currentPower = elevatorMotorRight.get();
		
		if (input < currentPower) {
            return Math.max(input, currentPower - TwoMotorElevatorConstants.MAX_ELEVATOR_POWER_CHANGE);
        }
        else if (input > currentPower) {
            return Math.min(input, currentPower + TwoMotorElevatorConstants.MAX_ELEVATOR_POWER_CHANGE);
        } 
		else {
            return input;
        }
	}

	public void fakeMethod() {
        power *= TwoMotorElevatorConstants.MAX_ELEVATOR_MOTOR_POWER;

        power = getRampedPower(power);

        if ((potentiometer.get() > TwoMotorElevatorConstants.MAXIMUM_ELEVATOR_POSITION && power > 0) 
				|| (potentiometer.get() < TwoMotorElevatorConstants.MINIMUM_ELEVATOR_POSITION && power < 0) 
				|| (elevatorLimitSwitch.get() && power < 0)) {
            power = 0;
        }

        if (elevatorLimitSwitch.get() && power < 0) {    
            power = 0;
        }

		power = getCappedPower(power);

        elevatorMotorRight.set(power, "Joystick said so");
        elevatorMotorLeft.set(power, "Joystick said so");
    }

    public void periodic() {
        if (!targetOverridden) {
            if (!isInTarget()) {
                power = pid.calculate(potentiometer.get(), targetPosition);
            } 
			else {
                power = 0;
            }

			reason = "Moving to position";
        }
		else {
			power *= TwoMotorElevatorConstants.MAX_ELEVATOR_MOTOR_POWER;

			power = getRampedPower(power);
		}

		if ((potentiometer.get() > TwoMotorElevatorConstants.MAXIMUM_ELEVATOR_POSITION && power > 0) 
				|| (potentiometer.get() < TwoMotorElevatorConstants.MINIMUM_ELEVATOR_POSITION && power < 0) 
				|| (elevatorLimitSwitch.get() && power < 0)) {
			power = 0;
		}

		power = getCappedPower(power);

		elevatorMotorRight.set(power, reason);
		elevatorMotorLeft.set(power, reason);
		

    }

    public boolean isInTarget() {
        double delta = targetPosition - potentiometer.get();
        return Math.abs(delta) < TwoMotorElevatorConstants.ELEVATOR_DEADBAND;
    }

    public void resetTarget() {
        targetPosition = potentiometer.get();
    }

}
