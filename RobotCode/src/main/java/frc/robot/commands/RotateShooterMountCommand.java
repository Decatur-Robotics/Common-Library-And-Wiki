package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class RotateShooterMountCommand extends Command {


	private double shooterMountRotation = 0.0f; // Ranges from 0 to 180

	public void execute() {
		
		shooterMountRotation = Math.min(1, shooterMountRotation + 0.1f);
		// Do not need to account for 0 here because we're just adding

	}


	

}
