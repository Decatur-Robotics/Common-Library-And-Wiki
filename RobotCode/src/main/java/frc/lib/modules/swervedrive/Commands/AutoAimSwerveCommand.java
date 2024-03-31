package frc.lib.modules.swervedrive.Commands;

import java.util.Optional;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.lib.core.ILogSource;
import frc.lib.core.util.TeamCountdown;

/**
 * Rotates the chassis towards the speaker. Intended to work with PathPlanner paths. Will end once
 * the note is fired. See {@link ShooterConstants#SHOOT_TIME} for the time to wait after shooting.
 */
public class AutoAimSwerveCommand extends Command implements ILogSource
{

	private final SwerveDriveSubsystem Swerve;
	private double angle;

	public AutoAimSwerveCommand(SwerveDriveSubsystem swerve, double angle)
	{
		Swerve = swerve;
		this.angle = angle;
	}

	@Override
	public void initialize()
	{
		logInfo("Starting AutoAimSwerveCommand");

		
	}

	@Override
	public void execute()
	{
		Swerve.drive(new Translation2d(), Swerve.getRotationalVelocityToAngle(angle), true, false);
	}

	@Override
	public void end(boolean interrupted)
	{
		logInfo("Ending AutoAimSwerveCommand");
	}

	@Override
	public boolean isFinished()
	{
		return Swerve.atRotation(angle);
	}
}