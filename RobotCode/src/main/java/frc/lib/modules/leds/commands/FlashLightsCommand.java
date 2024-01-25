package frc.lib.modules.leds.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.leds.subsystems.LedSubsystem;
import frc.lib.core.LogitechControllerButtons;

public class FlashLightsCommand extends Command
{
	private LedSubsystem ledSubsystem;
	public static double progress = 0;

	public FlashLightsCommand(LedSubsystem ledSubsystem)
	{
		this.ledSubsystem = ledSubsystem;
	}

	public void execute()
	{
		boolean note = false;
		// ^^^^^^^^^^^^^^^^^^^^
		// TODO ADD THIS LATER
		//
		if (note)
		{
			progress = 1;
			ledSubsystem.setAllPixels(0, 0, 255);
		}
		else
		{
			// if NOTE = false
			progress = 1;
			ledSubsystem.setAllPixels(255, 255, 0, 0, 100, 100);
		}
	}

	public void end(boolean interrupted)
	{

	}
}
