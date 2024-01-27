package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeOnCommand extends Command
{
	private final IntakeSubsystem Intake;

	public IntakeOnCommand(IntakeSubsystem intake)
	{
		this.Intake = intake;
		addRequirements(intake);
	}

	@Override
	public void execute()
	{
		Intake.toggleIntakeOn();
	}

	@Override
	public void end(boolean stop)
	{
		Intake.stopIntake();
	}

}
