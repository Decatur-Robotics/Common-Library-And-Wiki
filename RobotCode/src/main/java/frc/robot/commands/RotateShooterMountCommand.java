package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class RotateShooterMountCommand extends Command
{

	private double shooterMountSpeed;

	private double shooterMountRotation = 0.0f; // Ranges from 0 to 180

	private ShooterMountSubsystem shooterMountSubsystem;

	public RotateShooterMountCommand(ShooterMountSubsystem subsytem, double speed)
	{
		shooterMountSubsystem = subsytem;
		shooterMountSpeed = speed;
	}

	public void execute()
	{

		shooterMountRotation = Math.max(0, Math.min(180, shooterMountRotation + shooterMountSpeed));
		shooterMountSubsystem.setGoalRotation(shooterMountRotation);

	}

}