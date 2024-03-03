package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.MotionMagicVelocityDutyCycle;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Ports;

public class ClimberSubsystem extends SubsystemBase
{
	private Pigeon2 gyro;
	private TeamTalonFX climberMotorLeft, climberMotorRight;
	private double targetPosition, targetPositionLeft, targetPositionRight;
	private MotionMagicDutyCycle motorControlRequestLeft, motorControlRequestRight;
	private MotionMagicVelocityDutyCycle motorControlRequestLeftVelocity, motorControlRequestRightVelocity;
	private boolean override;
	private double leftPower, rightPower;
	
	public ClimberSubsystem()
	{
		gyro = new Pigeon2(Ports.PIGEON_GYRO);
		// sets extension of left and right motors to given extension length
		climberMotorLeft = new TeamTalonFX("Subsystems.Climber.ExtendRight",
				Ports.CLIMBER_MOTOR_RIGHT);
		climberMotorRight = new TeamTalonFX("Subsystems.Climber.ExtendLeft",
				Ports.CLIMBER_MOTOR_LEFT);
		climberMotorLeft.setNeutralMode(NeutralModeValue.Brake);
		climberMotorRight.setNeutralMode(NeutralModeValue.Brake);
		climberMotorLeft.setInverted(true);

		targetPosition = ClimberConstants.MIN_EXTENSION;
		targetPositionLeft = targetPosition;
		targetPositionRight = targetPosition;

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
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = ClimberConstants.CLIMBER_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration = ClimberConstants.CLIMBER_ACCELERATION;

		// config the main motor
		climberMotorLeft.getConfigurator().apply(motorConfigs);
		climberMotorRight.getConfigurator().apply(motorConfigs);

		motorControlRequestLeft = new MotionMagicDutyCycle(targetPositionLeft);
		motorControlRequestRight = new MotionMagicDutyCycle(targetPositionRight);

		motorControlRequestLeftVelocity = new MotionMagicVelocityDutyCycle(leftPower);
		motorControlRequestRightVelocity = new MotionMagicVelocityDutyCycle(rightPower);

		override = false;
	}

	public void periodic()
	{
		if (!override)
		{
			
			double gyroRoll = gyro.getRoll().getValueAsDouble();

			// left
			if (gyroRoll > ClimberConstants.DEADBAND_GYRO)
			{
				targetPositionLeft = climberMotorLeft.getCurrentEncoderValue();
			}
			// right
			else if (gyroRoll < -ClimberConstants.DEADBAND_GYRO)
			{
				targetPositionRight = climberMotorRight.getCurrentEncoderValue();
			}
			else 
			{
				targetPositionLeft = targetPosition;
				targetPositionRight = targetPosition;
			}

			// setting extension of climber arms
			climberMotorLeft.setControl(motorControlRequestLeft.withPosition(targetPositionLeft));
			climberMotorRight.setControl(motorControlRequestRight.withPosition(targetPositionRight));
		}
		else
		{
			climberMotorLeft.setControl(motorControlRequestLeftVelocity.withVelocity(leftPower));
			climberMotorRight.setControl(motorControlRequestRightVelocity.withVelocity(rightPower));

			targetPositionLeft = climberMotorLeft.getCurrentEncoderValue();
			targetPositionRight = climberMotorRight.getCurrentEncoderValue();
		}
	}

	public void setPowers(double leftPower, double rightPower, String reason)
	{
		this.leftPower = leftPower;
		this.rightPower = rightPower;

	}

	public void setPosition(double position)
	{
		targetPosition = position;
		targetPositionLeft = position;
		targetPositionRight = position;
	}

	public void setOverride(boolean override)
	{
		this.override = override;
	}

}