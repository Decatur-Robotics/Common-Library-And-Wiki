package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ClawConstants;
import frc.robot.subystems.ClawSubsystem;

public class ClawCommand extends Command
{
	private ClawSubsystem claw;
	private double desiredVelocity;

	public ClawCommand(ClawSubsystem claw, double desiredVelocity)
	{
		this.claw = claw;
		this.desiredVelocity = desiredVelocity;
		addRequirements(claw);
	}

	@Override
	public void initialize()
	{
		claw.setClawSpeed(desiredSpeed);
	}

	@Override
	public void end(boolean stop)
	{
		claw.setClawSpeed(ClawConstants.CLAW_REST_VELOCITY);

	}
}
