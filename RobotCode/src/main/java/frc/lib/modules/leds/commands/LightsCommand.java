package frc.lib.modules.leds.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.leds.NamedColor;
import frc.lib.modules.leds.subsystems.LedSubsystem;
import frc.lib.core.LogitechControllerButtons;

public class LightsCommand extends CommandBase
{
	private LedSubsystem leds;
	public static double progress = 0;

	public void AltLightsCommand(LedSubsystem ledSubsystem)
	{
		this.leds = ledSubsystem;
	}

	public void execute()
	{
		// if NOTE = false
		progress = 1;
		leds.setAllPixels(NamedColor.BLUE);

		// if NOTE = true
		progress = 1;
		leds.setAllPixels(255, 255, 0);
	}

	public void end(boolean interrupted)
	{

	}

}
