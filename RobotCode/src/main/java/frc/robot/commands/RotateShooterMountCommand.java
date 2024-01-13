package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class RotateShooterMountCommand extends Command {

	private ShooterMountSubsystem shooterMountSubsystem;

	public RotateShooterMountCommand(ShooterMountSubsystem subsytem) {
		shooterMountSubsystem = ShooterMountSubsystem.getInstance();
	}

	private double shooterMountRotation = 0.0f; // Ranges from 0 to 180

	public void execute() {
		
		shooterMountRotation = Math.min(1, shooterMountRotation + 0.1);
		shooterMountSubsystem.setGoalRotation(shooterMountRotation);
		// Do not need to account for 0 here because we're just adding

	}


	

}
