package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class RotateShooterMountReverseCommand extends Command
{

	private double shooterMountRotation = 0.0f; // Ranges from 0 to 180

	private ShooterMountSubsystem shooterMountSubsystem;

	public RotateShooterMountReverseCommand(ShooterMountSubsystem subsytem)
	{
		shooterMountSubsystem = subsytem;
	}

	public void execute()
	{

		shooterMountRotation = Math.min(1,
				shooterMountRotation - RotateShooterMountCommand.SHOOTER_MOUNT_SPEED);

		// Do not need to account for 0 here because we're just adding

	}

}