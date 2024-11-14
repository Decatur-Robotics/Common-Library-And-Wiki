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
	private ElevatorPosition elevatorPosition = ElevatorPosition.LOWER;

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

		motorControlRequest = new MotionMagicDutyCycle(elevatorPosition.getRotation());
		leftMotor.setControl(motorControlRequest.withPosition(elevatorPosition.getRotation()));

	}

	@Override
	public void periodic() {
		
		double position = getMotorPosition();
		double target = elevatorPosition.getRotation();

		leftMotor.set(Math.signum(target - position));

	}



	/**
	 * Sets the elevator's target to a preset position.
	 * @param position The preset position to set the robot to.
	 */
	public void setTargetPosition(ElevatorPosition position) {

		this.elevatorPosition = position;

		// I totally copied all of tihs from last year's shooter mount subsystem.
		double gravityFeedForward = ElevatorConstants.ELEVATOR_MOTOR_KG 
				* Math.cos(ElevatorConstants.MIN_ANGLE_RAD 
				+ ((this.elevatorPosition.getRotation() - ElevatorConstants.MIN_ANGLE) * ElevatorConstants.MOTOR_ROTATIONS_IN_RADIANS));

		leftMotor.setControl(motorControlRequest.withPosition(this.elevatorPosition.getRotation()).withFeedForward(gravityFeedForward) );
		
	}



	/**
	 * @return Returns where the motor is in encoder ticks.
	 */
	public double getMotorPosition() {
		
		return leftMotor.getRotorPosition().getValueAsDouble();

	}


}
