package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;

public class IntakeCommand extends Command
{
	private final IntakeSubsystem intake;

	public IntakeCommand(IntakeSubsystem intake)
	{
		this.intake = intake;
		
	}

	@Override
	public void initialize() {
		this.intake.raiseOrLowerIntakeMount(true);
		this.intake.turnOnOrStopIntakeMotors(true);
	}

	@Override
	public void end(boolean stop)
	{
		this.intake.turnOnOrStopIntakeMotors(false);
		this.intake.raiseOrLowerIntakeMount(true);
	}

	@Override
	public boolean isFinished()
	{
		return Math.abs(intake.goalRotation - intake.getPosition()) > IntakeSubsystem.DEADBAND;
	}
}
