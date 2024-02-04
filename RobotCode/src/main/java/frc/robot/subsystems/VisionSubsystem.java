package frc.robot.subsystems;

import java.util.Optional;

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
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;

public class VisionSubsystem extends SubsystemBase
{

	private final PhotonCamera Camera;
	private PhotonPoseEstimator robotPoseEstimator, shooterMountPoseEstimator;

	private PhotonPipelineResult latestPipelineResult;
	private Optional<PhotonTrackedTarget> bestTarget;

	public VisionSubsystem()
	{
		Camera = new PhotonCamera(VisionConstants.CameraTableName);

		AprilTagFieldLayout aprilTagFieldLayout = AprilTagFields.k2024Crescendo
				.loadAprilTagLayoutField();
		Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5),
				new Rotation3d(0, 0, 0)); // Tune to robot
		Transform3d shooterMountToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5),
				new Rotation3d(0, 0, 0)); // Tune to robot

		robotPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout,
				PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera, robotToCam);
		shooterMountPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout,
				PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera, shooterMountToCam);

		aprilTagFieldLayout.getTagPose(1);

	}

	@Override
	public void periodic()
	{
		latestPipelineResult = Camera.getLatestResult();

		// Pay attention to PhotonTargetSortMode!
		bestTarget = Optional.ofNullable(latestPipelineResult.getBestTarget());
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

	public Optional<Pose2d> getRobotPose()
	{
		Optional<EstimatedRobotPose> estPose = robotPoseEstimator.update();
		if (estPose.isPresent())
		{
			EstimatedRobotPose pose = estPose.get();
			Pose3d pose3d = pose.estimatedPose;

			// We only care about the x and z, and the yaw
			Rotation2d rot2d = new Rotation2d(pose3d.getRotation().getY());
			Pose2d pose2d = new Pose2d(pose3d.getX(), pose3d.getZ(), rot2d);

			return Optional.of(pose2d);
		}

		return Optional.empty();
	}

	public Optional<Pose2d> getShooterMountPose()
	{
		Optional<EstimatedRobotPose> estPose = shooterMountPoseEstimator.update();
		if (estPose.isPresent())
		{
			EstimatedRobotPose pose = estPose.get();
			Pose3d pose3d = pose.estimatedPose;

			// We only care about the x and z, and the yaw
			Rotation2d rot2d = new Rotation2d(pose3d.getRotation().getY());
			Pose2d pose2d = new Pose2d(pose3d.getX(), pose3d.getZ(), rot2d);

			return Optional.of(pose2d);
		}

		return Optional.empty();
	}

}
