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
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.RobotContainer;
import frc.robot.constants.Constants;
import frc.robot.constants.ShooterMountConstants;
import frc.robot.constants.VisionConstants;

public class VisionSubsystem extends SubsystemBase
{

	private final PhotonCamera Camera;
	private PhotonPoseEstimator robotPoseEstimator;

	private PhotonPipelineResult latestPipelineResult;

	private final SwerveDriveSubsystem Swerve;

	public VisionSubsystem(SwerveDriveSubsystem swerve)
	{
		Swerve = swerve;

		Camera = new PhotonCamera(VisionConstants.CameraTableName);

		robotPoseEstimator = new PhotonPoseEstimator(Constants.AprilTagFieldLayout,
				PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera, VisionConstants.ROBOT_TO_CAMERA_OFFSET);
	}

	@Override
	public void periodic()
	{
		latestPipelineResult = Camera.getLatestResult();

		Swerve.updatePoseWithVision(robotPoseEstimator.update());
	}

}