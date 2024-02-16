package frc.robot.commands;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
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

	private InterpolatingDoubleTreeMap gravityCompensationTreeMap;

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

		for (int i = 0; i < ShooterMountConstants.GRAVITY_COMPENSATION_TREE_MAP_KEYS.length; i++)
		{
			gravityCompensationTreeMap.put(
					ShooterMountConstants.GRAVITY_COMPENSATION_TREE_MAP_KEYS[i],
					ShooterMountConstants.GRAVITY_COMPENSATION_TREE_MAP_VALUES[i]);
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

		// Get the robots velocity
		Translation2d chassisVelocity = swerveDrive.getVelocity().getTranslation();

		// Calculate the distance from the shooter mount to the base of the speaker
		double groundDistance = speakerPose.getDistance(shooterMountPose.getTranslation());

		// Calculate the distance from the shooter mount to speaker opening
		double hypotenuse = Math.sqrt(Math.pow(ShooterMountConstants.SHOOTER_MOUNT_TO_SPEAKER, 2)
				+ Math.pow(groundDistance, 2));

		// Calculate shooter mount pose adjusted by velocity and distance to speaker
		Translation2d velocityAdjustedShooterMountPose = new Translation2d(
				shooterMountPose.getX()
						+ ((hypotenuse * ShooterMountConstants.EJECTED_NOTE_VELOCITY)
								* chassisVelocity.getX()),
				shooterMountPose.getY()
						+ ((hypotenuse / ShooterMountConstants.EJECTED_NOTE_VELOCITY)
								* chassisVelocity.getY()));

		// Recalculate ground distance and hypotenuse adjusted for velocity
		double velocityAdjustedGroundDistance = speakerPose.getDistance(velocityAdjustedShooterMountPose);
		double velocityAdjustedHypotenuse = Math.sqrt(Math.pow(ShooterMountConstants.SHOOTER_MOUNT_TO_SPEAKER, 2)
				+ Math.pow(velocityAdjustedGroundDistance, 2));

		// Calculate the target rotation of the shooter mount
		double targetRotation = Math.atan(
				ShooterMountConstants.SHOOTER_MOUNT_TO_SPEAKER / velocityAdjustedGroundDistance) * (180 / Math.PI);

		shooterMount.setTargetRotation(targetRotation + gravityCompensationTreeMap.get(velocityAdjustedHypotenuse));
	}

	@Override
	public void end(boolean interrupted)
	{
		shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY,
				"Ending auto aiming at speaker");
		shooterMount.setTargetRotation(ShooterMountConstants.SHOOTER_MOUNT_MIN_ANGLE);
	}

}
