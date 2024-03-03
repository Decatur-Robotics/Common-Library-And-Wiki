package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.MotionMagicVelocityDutyCycle;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import frc.robot.RobotContainer;
import frc.robot.constants.Constants;
import frc.robot.constants.Ports;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.ShooterMountConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkBase;
import frc.lib.core.motors.TeamTalonFX;

public class ShooterSubsystem extends SubsystemBase
{

	private double desiredShooterVelocity;

	private TeamTalonFX shooterMotorRight, shooterMotorLeft;

	private MotionMagicVelocityDutyCycle motorControlRequest;

	public ShooterSubsystem()
	{
		// Sets default shooter motor power
		desiredShooterVelocity = ShooterConstants.SHOOTER_REST_VELOCITY;

		// Initializes motor object
		shooterMotorRight = new TeamTalonFX("Left Shooter Motor Main", Ports.SHOOTER_MOTOR_RIGHT);
		shooterMotorLeft = new TeamTalonFX("Right Shooter Motor Main", Ports.SHOOTER_MOTOR_LEFT);

		shooterMotorRight.optimizeBusUtilization();
		shooterMotorLeft.optimizeBusUtilization();
		shooterMotorRight.getRotorPosition().setUpdateFrequency(20);

		shooterMotorRight.setInverted(true);
		shooterMotorLeft.setControl(new Follower(shooterMotorRight.getDeviceID(), true));
		shooterMotorRight.setNeutralMode(NeutralModeValue.Brake);
		shooterMotorLeft.setNeutralMode(NeutralModeValue.Brake);

		// create configurator
		TalonFXConfiguration mainMotorConfigs = new TalonFXConfiguration();

		// set pid profiles configs
		Slot0Configs pidSlot0Configs = mainMotorConfigs.Slot0;
		pidSlot0Configs.kP = ShooterConstants.SHOOTER_KP;
		pidSlot0Configs.kI = ShooterConstants.SHOOTER_KI;
		pidSlot0Configs.kD = ShooterConstants.SHOOTER_KD;
		pidSlot0Configs.kS = ShooterConstants.SHOOTER_KS;
		pidSlot0Configs.kV = ShooterConstants.SHOOTER_KV;
		pidSlot0Configs.kA = ShooterConstants.SHOOTER_KA;

		// set motionmagic velocity configs
		MotionMagicConfigs motionMagicVelocityConfigs = mainMotorConfigs.MotionMagic;
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = ShooterMountConstants.SHOOTER_MOUNT_CRUISE_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration = ShooterMountConstants.SHOOTER_MOUNT_ACCELERATION;

		// config the main motor
		shooterMotorRight.getConfigurator().apply(mainMotorConfigs);

		motorControlRequest = new MotionMagicVelocityDutyCycle(desiredShooterVelocity);
		shooterMotorRight.setControl(motorControlRequest.withVelocity(desiredShooterVelocity));

		RobotContainer.getShuffleboardTab().addDouble("Actual Shooter Velocity",
				() -> shooterMotorRight.getRotorVelocity().getValueAsDouble());
		RobotContainer.getShuffleboardTab().addDouble("Desired Shooter Velocity",
				() -> desiredShooterVelocity);
	}

	public double getVelocity()
	{
		return shooterMotorRight.getRotorVelocity().getValueAsDouble();
	}

	/**
	 * This is clamping the shooter motor power
	 */
	public void setShooterMotorVelocity(double desiredShooterVelocity, String reason)
	{
		this.desiredShooterVelocity = Math.max(
				Math.min(ShooterConstants.SHOOTER_MAX_VELOCITY, desiredShooterVelocity),
				-ShooterConstants.SHOOTER_MAX_VELOCITY);
	}

	public boolean isUpToSpeed()
	{
		return Math.abs(shooterMotorRight.getRotorVelocity().getValueAsDouble()
				- desiredShooterVelocity) < ShooterConstants.SHOOTER_VELOCITY_TOLERANCE;
	}

	/**
	 * Continuously updates shooter speed based on the commands above.
	 */
	@Override
	public void periodic()
	{
		shooterMotorRight.setControl(motorControlRequest.withVelocity(desiredShooterVelocity));
	}

}
