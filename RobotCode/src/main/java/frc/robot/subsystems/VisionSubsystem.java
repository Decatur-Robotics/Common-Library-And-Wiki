package frc.robot.subsystems;

import java.sql.Driver;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.RobotContainer;
import frc.robot.constants.ShooterMountConstants;
import frc.robot.constants.VisionConstants;

public class VisionSubsystem extends SubsystemBase
{

	private final PhotonCamera Camera;
	private PhotonPoseEstimator robotPoseEstimator;

	private PhotonPipelineResult latestPipelineResult;
	private Optional<PhotonTrackedTarget> bestTarget;

	private final AprilTagFieldLayout AprilTagFieldLayout;

	private final SwerveDriveSubsystem Swerve;
	private final ShooterMountSubsystem ShooterMount;

	public VisionSubsystem(SwerveDriveSubsystem swerve, ShooterMountSubsystem shooterMount)
	{
		Swerve = swerve;
		ShooterMount = shooterMount;

		Camera = new PhotonCamera(VisionConstants.CameraTableName);

		AprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();

		robotPoseEstimator = new PhotonPoseEstimator(AprilTagFieldLayout,
				PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera, VisionConstants.ROBOT_TO_CAMERA_OFFSET);
	}

	@Override
	public void periodic()
	{
		latestPipelineResult = Camera.getLatestResult();

		// Pay attention to PhotonTargetSortMode!
		bestTarget = Optional.ofNullable(latestPipelineResult.getBestTarget());

		Swerve.updatePoseWithVision(getRobotPose());
	}

	/**
	 * @return the yaw offset of the {@link #bestTarget}, or 0 if no target is found
	 */
	public double getYawOffset()
	{
		return bestTarget.map(PhotonTrackedTarget::getYaw).orElse(0.0);
	}

	/**
	 * @return the pitch offset of the {@link #bestTarget} , or 0 if no target is found
	 */
	public double getPitchOffset()
	{
		return bestTarget.map(PhotonTrackedTarget::getPitch).orElse(0.0);
	}

	/**
	 * @return the position of the speaker april tag for our alliance, or empty if the tag is not
	 *         found
	 */
	public Optional<Pose3d> getSpeakerPose()
	{
		return AprilTagFieldLayout.getTagPose(DriverStation.getAlliance().get() == Alliance.Blue
				? VisionConstants.BLUE_SPEAKER_TAG_ID
				: VisionConstants.RED_SPEAKER_TAG_ID);
	}

	/** @return Pose2d of estimated robot pose */
	public Optional<Pose2d> getRobotPose2d()
	{
		Optional<EstimatedRobotPose> estPose = getRobotPose();

		if (estPose.isPresent())
		{
			EstimatedRobotPose pose = estPose.get();
			Pose3d pose3d = pose.estimatedPose;

			return Optional.of(pose3dtoPose2d(pose3d));
		}

		return Optional.empty();
	}

	/** @return EstimatedRobotPose of robot */
	public Optional<EstimatedRobotPose> getRobotPose()
	{
		return robotPoseEstimator.update();
	}

	public Optional<Pose2d> adjustPoseForVelocity(Optional<Pose2d> pose)
	{
		if (pose.isEmpty())
		{
			return Optional.empty();
		}

		// Adjust for the velocity of the robot
		Pose2d velocity = Swerve.getVelocity();
		Pose2d adjustedPose = new Pose2d(pose.get().getX() + velocity.getX(),
				pose.get().getY() + velocity.getY(), pose.get().getRotation());
		return Optional.of(adjustedPose);
	}

	/**
	 * Convert a Pose3d to a Pose2d. We only care about the x and y, and the yaw
	 */
	private Pose2d pose3dtoPose2d(Pose3d pose3d)
	{
		// We only care about the x and y, and the yaw
		Rotation2d rot2d = new Rotation2d(pose3d.getRotation().getY());
		return new Pose2d(pose3d.getX(), pose3d.getY(), rot2d);
	}

	private Pose2d translation2dToPose2d(Translation2d translation)
	{
		return new Pose2d(translation, new Rotation2d());
	}

	public double angleTowardsPose(Pose2d targetPose, boolean adjustForVelocity)
	{
		Optional<Pose2d> robotPoseOptional = adjustForVelocity
				? adjustPoseForVelocity(getRobotPose2d())
				: getRobotPose2d();

		if (robotPoseOptional.isEmpty())
		{
			return 0.0;
		}

		return angleBetweenPoses(robotPoseOptional.get(), targetPose);
	}

	/**
	 * @return the angle to the target pose in radians. Counterclockwise rotation is negative.
	 */
	public static double angleBetweenPoses(Pose2d thisPose, Pose2d targetPose)
	{
		// Calculate the distance to the target
		Pose2d distance = new Pose2d(targetPose.getX() - thisPose.getX(),
				targetPose.getY() - thisPose.getY(), new Rotation2d());

		// Use the inverse tangent to calculate the angle
		// Atan2 accounts for the sign of the x and y values
		// We want the angle for (0, 0) to (0, 1) to be 0, so we add 90 degrees
		// We multiply by -1 to make the angle negative when the target is to the left
		return -Math.atan2(distance.getY(), distance.getX()) + Math.PI / 2;
	}

	/** @return What angle to turn to to face the speaker. Adjusts for velocity */
	public double getAngleToSpeaker()
	{
		Translation2d targetPose = getSpeakerPoseAdjustedForVelocity();

		double angle = angleTowardsPose(translation2dToPose2d(targetPose), true);

		SmartDashboard.putNumber("Angle to Speaker", angle);
		return angle;
	}

	public AprilTagFieldLayout getAprilTagFieldLayout()
	{
		return AprilTagFieldLayout;
	}

	/** @return boolean indicating whether robot is within shooting range of speaker */
	public boolean isInShooterRange()
	{
		double max = ShooterMountConstants.SpeakerDistanceTreeMapKeys[ShooterMountConstants.SpeakerDistanceTreeMapKeys.length
				- 1];
		double distance = getSpeakerPose().get().getTranslation().toTranslation2d()
				.getDistance(Swerve.getPose().getTranslation());

		return distance <= max;
	}

	/**
	 * NOTE: Velocity adjustment can be toggled with
	 * {@link VisionConstants#ADJUST_SPEAKER_POSE_FOR_VELOCITY}. Takes our current position. Then,
	 * calculates how long it will take for the note to reach the speaker and uses that time to find
	 * how far the note will move due to the robot's velocity. Finally, offsets the speaker's
	 * position by that amount.
	 */
	public Translation2d getSpeakerPoseAdjustedForVelocity()
	{
		// Get speaker pose
		Optional<Pose3d> speakerPoseOptional = getSpeakerPose();
		Translation2d speakerPose = !speakerPoseOptional.isEmpty() ? new Translation2d(
				speakerPoseOptional.get().getX(), speakerPoseOptional.get().getY())
				: new Translation2d();

		if (!VisionConstants.ADJUST_SPEAKER_POSE_FOR_VELOCITY)
			return speakerPose;

		// Get the shooter mount pose
		Pose2d shooterMountPose = getRobotPose2d().orElse(new Pose2d());

		// Calculate the distance from the shooter mount to the base of the speaker
		double groundDistance = shooterMountPose.getTranslation().getDistance(speakerPose);

		// Get the robots velocity
		Translation2d chassisVelocity = Swerve.getVelocity().getTranslation();

		// Calculate the estimated time for the note to reach the speaker
		double noteFlightTime = ShooterMount.getNoteVelocityEstimateTreeMap().get(groundDistance);

		// Calculate shooter mount pose adjusted by velocity and time for note to reach speaker
		Translation2d velocityAdjustedSpeakerPose = new Translation2d(
				speakerPose.getX() - (noteFlightTime * chassisVelocity.getX()),
				speakerPose.getY() - (noteFlightTime * chassisVelocity.getY()));

		return velocityAdjustedSpeakerPose;
	}

}