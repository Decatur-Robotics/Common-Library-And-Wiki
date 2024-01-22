package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class RotateShooterMountCommand extends Command
{

	/** In degrees per {@link #execute()} call */
	private double shooterMountSpeed;

	private ShooterMountSubsystem shooterMountSubsystem;

	/** @param speed in degrees per second */
	public RotateShooterMountCommand(ShooterMountSubsystem subsytem, double speed)
	{
		shooterMountSubsystem = subsytem;
		shooterMountSpeed = speed / 50;
	}

	public void execute()
	{
		double rotation = Math.max(0,
				Math.min(180, shooterMountSubsystem.getCurrentRotation() + shooterMountSpeed));
		shooterMountSubsystem.setGoalRotation(rotation);
	}

}