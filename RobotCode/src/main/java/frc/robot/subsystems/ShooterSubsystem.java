package frc.robot.subsystems;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

import frc.robot.constants.Constants;
import frc.robot.constants.Ports;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.ShooterMountConstants;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase
{

	private double desiredShooterVelocity;

	private SparkPIDController shooterPid;
	private TeamSparkMAX shooterMotorMain, shooterMotorSub;

	/** Key: distance to speaker in meters, Value: Rotation compensation in degrees */
	private InterpolatingDoubleTreeMap gravityCompensationTreeMap;
	/** Key: distance to speaker in meters, Value: Note velocity in meters per second */
	private InterpolatingDoubleTreeMap noteVelocityEstimateTreeMap;

	public ShooterSubsystem()
	{
		// Sets default shooter motor power
		desiredShooterVelocity = ShooterConstants.SHOOTER_REST_VELOCITY;

		// Initializes motor object
		shooterMotorMain = new TeamSparkMAX("Left Shooter Motor Main", Ports.SHOOTER_MOTOR_MAIN);
		shooterMotorSub = new TeamSparkMAX("Right Shooter Motor Main", Ports.SHOOTER_MOTOR_SUB);

		shooterMotorSub.follow(shooterMotorMain, true);
		shooterMotorMain.setIdleMode(IdleMode.kBrake);
		shooterMotorSub.setIdleMode(IdleMode.kBrake);
		shooterMotorMain.setSmartCurrentLimit(Constants.MAX_CURRENT);
		shooterMotorSub.setSmartCurrentLimit(Constants.MAX_CURRENT);

		shooterPid = shooterMotorMain.getPidController();

		shooterPid.setP(ShooterConstants.SHOOTER_KP);
		shooterPid.setI(ShooterConstants.SHOOTER_KI);
		shooterPid.setD(ShooterConstants.SHOOTER_KD);
		shooterPid.setFF(ShooterConstants.SHOOTER_KF);

		// Populate tree maps
		gravityCompensationTreeMap = new InterpolatingDoubleTreeMap();
		noteVelocityEstimateTreeMap = new InterpolatingDoubleTreeMap();
		for (int i = 0; i < ShooterMountConstants.SpeakerDistanceTreeMapKeys.length; i++)
		{
			double key = ShooterMountConstants.SpeakerDistanceTreeMapKeys[i];
			gravityCompensationTreeMap.put(key,
					ShooterMountConstants.GravityCompensationTreeMapValues[i]);
			noteVelocityEstimateTreeMap.put(key,
					ShooterMountConstants.NoteVelocityEstimateTreeMapValues[i]);
		}
	}

	public double getShooterMotorVelocityError()
	{
		return shooterMotorMain.getVelocityError();
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

	/**
	 * Continuously updates shooter speed based on the commands above.
	 */
	@Override
	public void periodic()
	{
		shooterPid.setReference(desiredShooterVelocity, ControlType.kVelocity);
	}

	/**
	 * @return A map where: Key: distance to speaker in meters, Value: Rotation compensation in
	 *         degrees
	 */
	public InterpolatingDoubleTreeMap getGravityCompensationTreeMap()
	{
		return gravityCompensationTreeMap;
	}

	/**
	 * @return A map where: Key: distance to speaker in meters, Value: Note velocity in meters per
	 *         second
	 */
	public InterpolatingDoubleTreeMap getNoteVelocityEstimateTreeMap()
	{
		return noteVelocityEstimateTreeMap;
	}
}
