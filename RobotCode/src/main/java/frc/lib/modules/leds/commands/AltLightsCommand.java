package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LEDSubsystem;

import java.util.Random;
import java.lang.Thread;

public class AltLightsCommand extends CommandBase {
	
	private LEDSubsystem leds;
	public static double progress = 0;

	public AltLightsCommand(LEDSubsystem ledSubsystem) {
		this.leds = ledSubsystem;
	}

	public void execute() {
        progress = 1;
        leds.setAllPixels(0, 0, 255);
	}

	public void end(boolean interrupted) {
        
	}

}
