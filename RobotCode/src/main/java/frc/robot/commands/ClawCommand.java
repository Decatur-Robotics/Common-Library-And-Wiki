package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ClawConstants;
import frc.robot.subystems.ClawSubsystem;

public class ClawCommand extends Command
{
	private ClawSubsystem claw;

	public ClawCommand(ClawSubsystem claw)
	{
		this.claw = claw;
		addRequirements(claw);
	}

	@Override
	public void initialize()
	{
		claw.desiredVelocity(ClawConstants.CLAW_DESIRED_VELOCITY);
	}

	@Override
	public void end(boolean stop)
	{
		claw.desiredVelocity(ClawConstants.CLAW_REST_VELOCITY);

	}
}
