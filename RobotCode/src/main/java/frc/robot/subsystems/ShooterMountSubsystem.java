package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.RobotContainer;
import frc.robot.constants.Ports;
import frc.robot.constants.ShooterMountConstants;

public class ShooterMountSubsystem extends SubsystemBase
{

	private TeamTalonFX shooterMountMotorLeft, shooterMountMotorRight;

	private MotionMagicDutyCycle motorControlRequest;

	/** Target rotation in encoder ticks (2048 encoder ticks to 1 degree) */
	private double targetRotation;

	/** Key: distance to speaker in meters, Value: Rotation compensation in degrees */
	private InterpolatingDoubleTreeMap shooterMountAngleTreeMap;
	/** Key: distance to speaker in meters, Value: Note velocity in meters per second */
	private InterpolatingDoubleTreeMap noteVelocityEstimateTreeMap;

	public ShooterMountSubsystem()
	{

		shooterMountMotorLeft = new TeamTalonFX("SHOOTER_MOUNT_MOTOR_LEFT", Ports.SHOOTER_MOUNT_MOTOR_LEFT);
		shooterMountMotorRight = new TeamTalonFX("SHOOTER_MOUNT_MOTOR_RIGHT", Ports.SHOOTER_MOUNT_MOTOR_RIGHT);

		shooterMountMotorLeft.optimizeBusUtilization();
		shooterMountMotorRight.optimizeBusUtilization();
		shooterMountMotorLeft.getRotorPosition().setUpdateFrequency(20);

		shooterMountMotorRight.setControl(new Follower(shooterMountMotorLeft.getDeviceID(), true));

		// create configurator
		TalonFXConfiguration mainMotorConfigs = new TalonFXConfiguration();

		// set pid profiles configs
		Slot0Configs pidSlot0Configs = mainMotorConfigs.Slot0;
		pidSlot0Configs.kP = ShooterMountConstants.SHOOTER_MOUNT_KP;
		pidSlot0Configs.kI = ShooterMountConstants.SHOOTER_MOUNT_KI;
		pidSlot0Configs.kD = ShooterMountConstants.SHOOTER_MOUNT_KD;
		pidSlot0Configs.kS = ShooterMountConstants.SHOOTER_MOUNT_KS;
		pidSlot0Configs.kV = ShooterMountConstants.SHOOTER_MOUNT_KV;
		pidSlot0Configs.kA = ShooterMountConstants.SHOOTER_MOUNT_KA;

		// set motionmagic velocity configs
		MotionMagicConfigs motionMagicVelocityConfigs = mainMotorConfigs.MotionMagic;
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = ShooterMountConstants.SHOOTER_MOUNT_CRUISE_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration = ShooterMountConstants.SHOOTER_MOUNT_ACCELERATION;

		// config the main motor
		shooterMountMotorLeft.getConfigurator().apply(mainMotorConfigs);

		targetRotation = ShooterMountConstants.SHOOTER_MOUNT_MIN_ANGLE;

		// Populate tree maps
		shooterMountAngleTreeMap = new InterpolatingDoubleTreeMap();
		noteVelocityEstimateTreeMap = new InterpolatingDoubleTreeMap();
		for (int i = 0; i < ShooterMountConstants.SpeakerDistanceTreeMapKeys.length; i++)
		{
			double key = ShooterMountConstants.SpeakerDistanceTreeMapKeys[i];
			shooterMountAngleTreeMap.put(key,
					ShooterMountConstants.ShooterMountAngleTreeMapValues[i]);
			noteVelocityEstimateTreeMap.put(key,
					ShooterMountConstants.NoteVelocityEstimateTreeMapValues[i]);
		}

		motorControlRequest = new MotionMagicDutyCycle(targetRotation);
		shooterMountMotorLeft.setControl(motorControlRequest.withPosition(targetRotation));

		RobotContainer.getShuffleboardTab().addDouble("Actual Shooter Mount Rotation",
				() -> (shooterMountMotorLeft.getCurrentEncoderValue()));
		RobotContainer.getShuffleboardTab().addDouble("Desired Shooter Mount Rotation",
				() -> targetRotation);
	}

	@Override
	public void periodic()
	{
		shooterMountMotorLeft.setControl(motorControlRequest.withPosition(targetRotation));
	}

	/**
	 * Set the desired rotation of the shooter mount
	 * 
	 * @param targetRotation the desired rotation in degrees
	 */
	public void setTargetRotation(double targetRotation)
	{
		this.targetRotation = Math.max(targetRotation,
				ShooterMountConstants.SHOOTER_MOUNT_MIN_ANGLE);
	}

	/**
	 * @return A map where: Key: distance to speaker in meters, Value: Rotation compensation in
	 *         degrees
	 */
	public InterpolatingDoubleTreeMap getShooterMountAngleTreeMap()
	{
		return shooterMountAngleTreeMap;
	}

	/**
	 * @return A map where: Key: distance to speaker in meters, Value: Note velocity in meters per
	 *         second
	 */
	public InterpolatingDoubleTreeMap getNoteVelocityEstimateTreeMap()
	{
		return noteVelocityEstimateTreeMap;
	}

	public boolean isAtTargetRotation()
	{
		return Math.abs((shooterMountMotorLeft.getCurrentEncoderValue() * 2048)
				- targetRotation) < ShooterMountConstants.AIMING_DEADBAND;
	}

}