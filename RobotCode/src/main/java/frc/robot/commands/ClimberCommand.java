package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.constants.ClimberConstants;

public class ClimberCommand extends Command
{

	private ClimberSubsystem climber;
	private DoubleSupplier climberToggle;

	public ClimberCommand(ClimberSubsystem climberSubsystemImTaking, DoubleSupplier climberToggle)
	{
		climber = climberSubsystemImTaking;
		this.climberToggle = climberToggle;
		addRequirements(climber);
	}

}
