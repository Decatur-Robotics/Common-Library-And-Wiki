package frc.robot.subsystems;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.ElevatorPosition;
import frc.robot.constants.Ports;

public class ElevatorSubsystem extends SubsystemBase {


	private TalonFX leftMotor, rightMotor;
	private double elevatorPosition = ElevatorPosition.LOWER;

	private MotionMagicDutyCycle motorControlRequest;

	public ElevatorSubsystem() {

		// Arbitrary values :D
		this.leftMotor = new TalonFX(Ports.ELEVATOR_MOTOR_LEFT);
		this.rightMotor = new TalonFX(Ports.ELEVATOR_MOTOR_RIGHT);

		this.rightMotor.setControl(new Follower(Ports.ELEVATOR_MOTOR_LEFT, true));

		TalonFXConfiguration mainMotorConfigs = new TalonFXConfiguration();

		Slot0Configs pidSlot0Configs = mainMotorConfigs.Slot0;
		pidSlot0Configs.kP = ElevatorConstants.ELEVATOR_MOTOR_KP;
		pidSlot0Configs.kI = ElevatorConstants.ELEVATOR_MOTOR_KI;
		pidSlot0Configs.kD = ElevatorConstants.ELEVATOR_MOTOR_KD;
		pidSlot0Configs.kS = ElevatorConstants.ELEVATOR_MOTOR_KS;
		pidSlot0Configs.kV = ElevatorConstants.ELEVATOR_MOTOR_KV;
		pidSlot0Configs.kA = ElevatorConstants.ELEVATOR_MOTOR_KA;

		MotionMagicConfigs motionMagicVelocityConfigs = mainMotorConfigs.MotionMagic;
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = ElevatorConstants.ELEVATOR_CRUISE_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration   = ElevatorConstants.ELEVATOR_ACCELERATION;

		leftMotor.getConfigurator().apply(mainMotorConfigs);

		motorControlRequest = new MotionMagicDutyCycle(elevatorPosition);
		leftMotor.setControl(motorControlRequest.withPosition(elevatorPosition));

	}



	/**
	 * Sets the elevator's target to a preset position.
	 * @param position The preset position to set the robot to.
	 */
	public void setTargetPosition(double position) {

		this.elevatorPosition = position;
		leftMotor.setControl(motorControlRequest.withPosition(this.elevatorPosition) );

	}



	/**
	 * @return Returns where the motor is in encoder ticks.
	 */
	public double getMotorPosition() {
		
		return leftMotor.getRotorPosition().getValueAsDouble();

	}


}
