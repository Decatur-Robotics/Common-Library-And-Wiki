package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class RotateShooterMountCommand extends Command
{

	public static final float SHOOTER_MOUNT_SPEED = 0.1f;

	private double shooterMountRotation = 0.0f; // Ranges from 0 to 180

	private ShooterMountSubsystem shooterMountSubsystem;

	public RotateShooterMountCommand(ShooterMountSubsystem subsytem)
	{
		shooterMountSubsystem = ShooterMountSubsystem.getInstance();
	}

	public void execute()
	{

		shooterMountRotation = Math.min(1, shooterMountRotation + SHOOTER_MOUNT_SPEED);

		// Do not need to account for 0 here because we're just adding

	}

}