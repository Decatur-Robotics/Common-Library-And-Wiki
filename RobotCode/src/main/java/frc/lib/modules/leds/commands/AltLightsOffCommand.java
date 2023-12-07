package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;

import java.util.Random;
import java.lang.Thread;

import frc.robot.commands.AltLightsCommand;

public class AltLightsCommand extends CommandBase {
	
	private LEDSubsystem leds;

	public AltLightsCommand(LEDSubsystem ledSubsystem) {
		this.leds = ledSubsystem;
	}

	public void execute() {
		if (AltLightsCommand.progress <= 0) return;
        AltLightsCommand.progress-=0.02;
		leds.setAllPixels(LEDSubsystem.calcBlending(0, 0, 255, 255, 0, 0, progress));
	}

	public void end(boolean interrupted) {
		
	}

}
