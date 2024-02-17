package frc.robot.commands;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.ShooterMountConstants;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * Points shooter mount at speaker Returns shooter mount to rest position when done
 */
public class AimShooterCommand extends Command
{

	private ShooterSubsystem shooter;
	private ShooterMountSubsystem shooterMount;
	private VisionSubsystem vision;
	private SwerveDriveSubsystem swerveDrive;

	private AprilTagFieldLayout aprilTagFieldLayout;

	private InterpolatingDoubleTreeMap gravityCompensationTreeMap, noteVelocityEstimateTreeMap;

	public AimShooterCommand(ShooterSubsystem shooter, ShooterMountSubsystem shooterMount,
			VisionSubsystem vision, SwerveDriveSubsystem swerveDrive)
	{
		this.shooter = shooter;
		this.shooterMount = shooterMount;
		this.vision = vision;
		this.swerveDrive = swerveDrive;

		addRequirements(shooter, shooterMount, vision);

		aprilTagFieldLayout = vision.getAprilTagFieldLayout();

		gravityCompensationTreeMap = new InterpolatingDoubleTreeMap();

		for (int i = 0; i < ShooterMountConstants.SPEAKER_DISTANCE_TREE_MAP_KEYS.length; i++)
		{
			gravityCompensationTreeMap.put(
					ShooterMountConstants.SPEAKER_DISTANCE_TREE_MAP_KEYS[i],
					ShooterMountConstants.GRAVITY_COMPENSATION_TREE_MAP_VALUES[i]);
		}

		noteVelocityEstimateTreeMap = new InterpolatingDoubleTreeMap();

		for (int i = 0; i < ShooterMountConstants.SPEAKER_DISTANCE_TREE_MAP_KEYS.length; i++)
		{
			noteVelocityEstimateTreeMap.put(
				ShooterMountConstants.SPEAKER_DISTANCE_TREE_MAP_KEYS[i],
				ShooterMountConstants.NOTE_VELOCITY_ESTIMATE_TREE_MAP_VALUES[i]);
		}
	}

	@Override
	public void initialize()
	{
		shooter.setShooterMotorVelocity(IndexerConstants.INDEXER_SHOOT_VELOCITY,
				"Auto aiming at speaker");
	}

	@Override
	public void execute()
	{
		DriverStation.Alliance allianceColor = DriverStation.getAlliance().orElse(null);

		Translation2d speakerPose = new Translation2d();

		// Get speaker pose
		if (allianceColor == DriverStation.Alliance.Red)
		{
			Pose3d tagPose = aprilTagFieldLayout.getTagPose(4).orElse(new Pose3d());

			speakerPose = new Translation2d(tagPose.getX(), tagPose.getY());
		}
		else if (allianceColor == DriverStation.Alliance.Blue)
		{
			Pose3d tagPose = aprilTagFieldLayout.getTagPose(7).orElse(new Pose3d());

			speakerPose = new Translation2d(tagPose.getX(), tagPose.getY());
		}

		// Get the shooter mount pose
		Pose2d shooterMountPose = vision.getShooterMountPose().orElse(new Pose2d());

		// Calculate the distance from the shooter mount to the base of the speaker
		double groundDistance = shooterMountPose.getTranslation().getDistance(speakerPose);

		// Get the robots velocity
		Translation2d chassisVelocity = swerveDrive.getVelocity().getTranslation();

		// Calculate the estimated time for the note to reach the speaker
		double noteFlightTime = noteVelocityEstimateTreeMap.get(groundDistance);

		// Calculate shooter mount pose adjusted by velocity and time for note to reach speaker
		Translation2d velocityAdjustedSpeakerPose = new Translation2d(
				speakerPose.getX() - (noteFlightTime * chassisVelocity.getX()),
				speakerPose.getY() - (noteFlightTime * chassisVelocity.getY()));

		// Recalculate ground distance adjusting for velocity
		double velocityAdjustedGroundDistance = shooterMountPose.getTranslation()
				.getDistance(velocityAdjustedSpeakerPose);

		// Calculate the target rotation of the shooter mount in degrees
		double targetRotation = Units.radiansToDegrees(Math.atan(
				ShooterMountConstants.SHOOTER_MOUNT_TO_SPEAKER / velocityAdjustedGroundDistance));

		shooterMount.setTargetRotation(
				targetRotation + gravityCompensationTreeMap.get(velocityAdjustedGroundDistance));
	}

	@Override
	public void end(boolean interrupted)
	{
		shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY,
				"Ending auto aiming at speaker");
		shooterMount.setTargetRotation(ShooterMountConstants.SHOOTER_MOUNT_MIN_ANGLE);
	}

}
