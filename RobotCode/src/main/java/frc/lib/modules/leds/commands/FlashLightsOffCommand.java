package frc.lib.modules.leds.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.leds.subsystems.LedSubsystem;
import frc.robot.RobotContainer;

import java.util.Random;
import java.lang.Thread;

import frc.lib.modules.leds.commands.FlashLightsCommand;

public class FlashLightsOffCommand
{
	private LedSubsystem led;

	public FlashLightsOffCommand(LedSubsystem ledSubsystem)
	{
		this.led = ledSubsystem;
	}

	public void execute()
	{
		// If NOTE = true
		// Also add controls here
		if (FlashLightsCommand.progress <= 0)
			return;
		FlashLightsCommand.progress -= 0.02;
		led.setAllPixels(
				LedSubsystem.calcBlending(0, 0, 255, 0, 0, 0, FlashLightsCommand.progress));

		// If NOTE = false

		if (FlashLightsCommand.progress <= 0)
			return;
		FlashLightsCommand.progress -= 0.02;
		led.setAllPixels(
				LedSubsystem.calcBlending(255, 255, 0, 0, 0, 0, FlashLightsCommand.progress));

		// If signal button is pressed
		if (FlashLightsCommand.progress <= 0)
			return;
		FlashLightsCommand.progress -= 0.02;
		led.setAllPixels(
				LedSubsystem.calcBlending(255, 0, 0, 0, 0, 0, FlashLightsCommand.progress));

	}

	public void end(boolean interrupted)
	{

	}
}
