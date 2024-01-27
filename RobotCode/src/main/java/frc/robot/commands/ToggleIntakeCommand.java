package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakeCommand extends Command
{

	private final IntakeSubsystem Intake;

	public ToggleIntakeCommand(IntakeSubsystem intake)
	{
		this.Intake = intake;
	}

	@Override
	public void execute()
	{
		Intake.raiseOrLowerIntake();
	}

	@Override
	public void end(boolean stop)
	{
		Intake.stopIntake();
	}

	@Override
	public boolean isFinished()
	{
		return Intake.isStopped();
	}
}
