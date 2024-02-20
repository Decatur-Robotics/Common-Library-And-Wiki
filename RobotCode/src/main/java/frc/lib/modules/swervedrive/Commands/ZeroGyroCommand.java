package frc.lib.modules.swervedrive.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;

public class ZeroGyroCommand extends Command 
{
	public ZeroGyroCommand(final SwerveDriveSubsystem Swerve)
	{
		Swerve.zeroGyro();
	}
}
