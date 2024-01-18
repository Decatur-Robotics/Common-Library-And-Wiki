package frc.lib.modules.swervedrive.Commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveConstants;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;

@Deprecated
public class DriveDistanceAuto extends Command
{

	private SwerveDriveSubsystem swerve;

	private double speed, distanceX, distanceY, targetPositionX, targetPositionY;

	public DriveDistanceAuto(double distanceX, double distanceY, double speed,
			SwerveDriveSubsystem swerve)
	{
		this.distanceX = distanceX;
		this.distanceY = distanceY;

		this.speed = speed;
		this.swerve = swerve;

		addRequirements(swerve);
	}

	@Override
	public void initialize()
	{
		targetPositionX = swerve.getPose().getX() + distanceX;
		targetPositionY = swerve.getPose().getY() + distanceY;
	}

	@Override
	public void execute()
	{
		SmartDashboard.putString("Auto Drive Target",
				"(" + targetPositionX + ", " + targetPositionY + ")");
		SmartDashboard.putString("Swerve Pose",
				"(" + swerve.getPose().getX() + ", " + swerve.getPose().getY() + ")");

		// Calculate speed
		Translation2d speedVector = new Translation2d(distanceX > 0 ? speed : -speed,
				distanceY > 0 ? speed : -speed).times(SwerveConstants.AUTO_SPEED);

		swerve.drive(speedVector, 0, true, true);
	}

	@Override
	public boolean isFinished()
	{
		// Check if both x and y are finished

		boolean xFinished = false;
		if (distanceX > 0)
			xFinished = swerve.getPose().getX() > targetPositionX;
		else
			xFinished = swerve.getPose().getX() < targetPositionX;

		boolean yFinished = false;
		if (distanceY > 0)
			yFinished = swerve.getPose().getY() > targetPositionY;
		else
			yFinished = swerve.getPose().getY() < targetPositionY;

		return xFinished && yFinished;
	}
}
