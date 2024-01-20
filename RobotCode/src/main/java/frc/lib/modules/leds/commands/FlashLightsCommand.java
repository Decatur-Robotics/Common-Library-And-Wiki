package frc.lib.modules.leds.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.leds.subsystems.LedSubsystem;
import frc.lib.core.LogitechControllerButtons;

public class FlashLightsCommand
{
	private LedSubsystem ledSubsystem;
	public static double progress = 0;

	public FlashLightsCommand(LedSubsystem ledSubsystem)
	{
		this.ledSubsystem = ledSubsystem;
	}

	public void execute()
	{
		// if NOTE = true
		progress = 1;
		ledSubsystem.setAllPixels(0, 0, 255);

		// if NOTE = false
		progress = 1;
		ledSubsystem.setAllPixels(255, 255, 0);
	}

	public void end(boolean interrupted)
	{

	}
}
