package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ClimberSubsystem;


public class ClimberOverrideCommand extends Command
{
	public ClimberOverrideCommand(ClimberSubsystem ClimberSubsystem)
	{
		ClimberSubsystem.setOverride(true);

	}
	public void end()
	{
		ClimberSubsystem.setOverride(false);
	}
}
