package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AimShooterCommand extends Command {

	private ShooterSubsystem shooter;
	private ShooterMountSubsystem shooterMount;

	public AimShooterCommand(ShooterSubsystem shooter, ShooterMountSubsystem shooterMount) {
		this.shooter = shooter;
		this.shooterMount = shooterMount;

		addRequirements(shooter, shooterMount);
	}

	public void initialize() {
		shooter.setShooterMotorPower(ShooterConstants.SPEAKER_SPEED, "Aiming shooter");
		shooterMount.setAutoAim(true);
	}

	public void end() {
		shooter.setShooterMotorPower(0, "Ending aiming shooter");
		shooterMount.setAutoAim(false);
	}
	
}
