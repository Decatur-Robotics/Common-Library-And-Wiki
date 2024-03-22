package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.constants.Constants;
import frc.robot.constants.VisionConstants;

public class VisionSubsystem extends SubsystemBase
{

	// private final PhotonCamera Camera;
	// private PhotonPoseEstimator robotPoseEstimator;

	private final SwerveDriveSubsystem Swerve;

	public VisionSubsystem(SwerveDriveSubsystem swerve)
	{
		Swerve = swerve;

		// Camera = new PhotonCamera(VisionConstants.CAMERA_TABLE_NAME);

		// robotPoseEstimator = new PhotonPoseEstimator(Constants.AprilTagFieldLayout,
		// 		PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, Camera,
		// 		VisionConstants.ROBOT_TO_CAMERA_OFFSET);
	}

	@Override
	public void periodic()
	{
		// UNCOMMENT THIS IF WE GET VISION WORKING

		// Swerve.updatePoseWithVision(robotPoseEstimator.update());
	}

}