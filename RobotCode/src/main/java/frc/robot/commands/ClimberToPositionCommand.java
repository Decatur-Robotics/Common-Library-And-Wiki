package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberToPositionCommand extends Command
{

	public ClimberToPositionCommand(ClimberSubsystem climber, double targetPosition)
	{
		addRequirements(climber);

		climber.setPosition(targetPosition);
	}
}
