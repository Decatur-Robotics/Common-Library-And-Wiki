package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Constants;
import frc.robot.constants.Ports;

public class ClimberSubsystem extends SubsystemBase
{

	private TalonFX climberMotorRight;
	private TalonFX climberMotorLeft;
	private double leftTargetPosition, rightTargetPosition;
	private MotionMagicDutyCycle motorControlRequestLeft, motorControlRequestRight;
	private VelocityDutyCycle motorControlRequestLeftVelocity, motorControlRequestRightVelocity;
	private boolean override;
	private boolean isBalanced;

	public ClimberSubsystem()
	{
		// sets extension of left and right motors to given extension length
		climberMotorLeft = new TalonFX(Ports.CLIMBER_MOTOR_LEFT, Constants.CANIVORE_NAME);
		climberMotorRight = new TalonFX(Ports.CLIMBER_MOTOR_RIGHT, Constants.CANIVORE_NAME);

		climberMotorLeft.setNeutralMode(NeutralModeValue.Brake);
		climberMotorRight.setNeutralMode(NeutralModeValue.Brake);

		leftTargetPosition = ClimberConstants.LEFT_CLIMBER_MINIMUM;
		rightTargetPosition = ClimberConstants.RIGHT_CLIMBER_MINIMUM;

		// create configurator
		TalonFXConfiguration motorConfigs = new TalonFXConfiguration();

		// set pid profiles configs
		Slot0Configs pidSlot0Configs = motorConfigs.Slot0;
		pidSlot0Configs.kP = ClimberConstants.CLIMBER_KP;
		pidSlot0Configs.kI = ClimberConstants.CLIMBER_KI;
		pidSlot0Configs.kD = ClimberConstants.CLIMBER_KD;
		pidSlot0Configs.kS = ClimberConstants.CLIMBER_KS;
		pidSlot0Configs.kV = ClimberConstants.CLIMBER_KV;
		pidSlot0Configs.kA = ClimberConstants.CLIMBER_KA;

		// set motionmagic velocity configs
		MotionMagicConfigs motionMagicVelocityConfigs = motorConfigs.MotionMagic;
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = ClimberConstants.CLIMBER_CRUISE_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration = ClimberConstants.CLIMBER_ACCELERATION;

		// config the main motor
		climberMotorLeft.getConfigurator().apply(motorConfigs);
		climberMotorRight.getConfigurator().apply(motorConfigs);

		motorControlRequestLeft = new MotionMagicDutyCycle(leftTargetPosition);
		motorControlRequestRight = new MotionMagicDutyCycle(rightTargetPosition);

		motorControlRequestLeftVelocity = new VelocityDutyCycle(0);
		motorControlRequestRightVelocity = new VelocityDutyCycle(0);

		override = false;

		RobotContainer.getShuffleboardTab().addNumber("L Climber Pos",
				() -> climberMotorLeft.getPosition().getValueAsDouble());
		RobotContainer.getShuffleboardTab().addNumber("R Climber Pos",
				() -> climberMotorRight.getPosition().getValueAsDouble());
		RobotContainer.getShuffleboardTab().addBoolean("Climber Override", () -> override);

		// climberMotorLeft.setControl(motorControlRequestLeft.withPosition(leftTargetPosition));
		// climberMotorRight.setControl(motorControlRequestRight.withPosition(rightTargetPosition));
	}

	@Override
	public void periodic()
	{
		if (climberMotorLeft.hasResetOccurred() || climberMotorRight.hasResetOccurred())
		{
			climberMotorLeft.optimizeBusUtilization();
			climberMotorRight.optimizeBusUtilization();
			climberMotorLeft.getRotorPosition().setUpdateFrequency(20);
			climberMotorRight.getRotorPosition().setUpdateFrequency(20);
		}

		// Climber auto-balance while climbing
		// if (isBalanced && RobotContainer.getGyro().getRoll()
		// 		.getValueAsDouble() > ClimberConstants.BALANCE_DEADBAND)
		// {
		// 	isBalanced = false;
		// 	climberMotorLeft.setControl(motorControlRequestLeft.withPosition(leftTargetPosition));
		// 	climberMotorRight.setControl(motorControlRequestRight
		// 			.withPosition(climberMotorRight.getRotorPosition().getValueAsDouble()));
		// }

		// if (isBalanced && RobotContainer.getGyro().getRoll()
		// 		.getValueAsDouble() < -ClimberConstants.BALANCE_DEADBAND)
		// {
		// 	isBalanced = false;
		// 	climberMotorRight
		// 			.setControl(motorControlRequestRight.withPosition(rightTargetPosition));
		// 	climberMotorLeft.setControl(motorControlRequestLeft
		// 			.withPosition(climberMotorLeft.getRotorPosition().getValueAsDouble()));
		// }

		// if (!isBalanced
		// 		&& RobotContainer.getGyro().getRoll()
		// 				.getValueAsDouble() >= -ClimberConstants.BALANCE_DEADBAND
		// 		&& RobotContainer.getGyro().getRoll()
		// 				.getValueAsDouble() <= ClimberConstants.BALANCE_DEADBAND)
		// {
		// 	isBalanced = true;
		// 	climberMotorLeft.setControl(motorControlRequestLeft.withPosition(leftTargetPosition));
		// 	climberMotorRight
		// 			.setControl(motorControlRequestRight.withPosition(rightTargetPosition));
		// }

	}

	public void setPowers(double leftPower, double rightPower, String reason)
	{
		if (true)
		{
			climberMotorLeft.setControl(motorControlRequestLeftVelocity.withVelocity(-leftPower));
			climberMotorRight.setControl(motorControlRequestRightVelocity.withVelocity(-rightPower));

			leftTargetPosition = climberMotorLeft.getRotorPosition().getValueAsDouble();
			rightTargetPosition = climberMotorRight.getRotorPosition().getValueAsDouble();
		}
	}

	public void setPosition(double leftTargetPosition, double rightTargetPosition)
	{
		// this.leftTargetPosition = leftTargetPosition;
		// this.rightTargetPosition = rightTargetPosition;

		// climberMotorLeft.setControl(motorControlRequestLeft.withPosition(leftTargetPosition));
		// climberMotorRight.setControl(motorControlRequestRight.withPosition(rightTargetPosition));
	}

	public void setOverride(boolean override)
	{
		System.out.println("Setting climber override to " + override);

		this.override = override;

		if (override == false)
			setPosition(leftTargetPosition, rightTargetPosition);
	}

}