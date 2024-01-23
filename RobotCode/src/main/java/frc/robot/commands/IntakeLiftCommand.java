package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class IntakeLiftCommand extends Command
{

	private IntakeSubsystem intake;

	public IntakeLiftCommand(IntakeSubsystem intake)
	{
		this.intake = intake;
	}

	public void execute()
	{
		intake.raiseOrLowerIntake();
	}
	
	public void end(boolean stop){
		intake.stopIntake();
    }
}
