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

		AprilTagFieldLayout aprilTagFieldLayout = AprilTagFields.k2024Crescendo.loadAprilTagLayoutField();
		Transform3d robotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); // Tune to robot
		Transform3d shooterMountToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5), new Rotation3d(0,0,0)); // Tune to robot
		
		robotPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera, robotToCam);
		shooterMountPoseEstimator = new PhotonPoseEstimator(aprilTagFieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera, shooterMountToCam);

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

	public Optional<EstimatedRobotPose> getRobotPose()
	{
		return robotPoseEstimator.update();
	}

	public Optional<EstimatedRobotPose> getShooterMountPose()
	{
		return shooterMountPoseEstimator.update();
	}

}
