package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberOverrideCommand extends Command {
	private ClimberSubsystem climber;

	public ClimberOverrideCommand(ClimberSubsystem climber) {
		this.climber = climber;
		addRequirements(climber);
	}

	// forgot what this does but renato said its important
	@Override
	public void initialize() {
		climber.setOverride(true);
	}

	@Override
	public void end(boolean interrupted) {
		climber.setOverride(false);
	}
}
