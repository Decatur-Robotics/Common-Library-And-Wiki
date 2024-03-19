package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterOverrideCommand extends Command
{
	// Initializes the subsystem objects
	private ShooterSubsystem shooter;
	private IndexerSubsystem indexer;

	private double desiredShooterVelocity;

	private boolean endAutomatically;

	public ShooterOverrideCommand(ShooterSubsystem shooter, IndexerSubsystem indexer,
			double desiredShooterVelocity, boolean endAutomatically)
	{
		this.desiredShooterVelocity = desiredShooterVelocity;
		this.endAutomatically = endAutomatically;

		this.shooter = shooter;
		this.indexer = indexer;
		addRequirements(shooter, indexer);
	}

	@Override
	public void execute()
	{
		// Spins up the motor
		shooter.setShooterMotorVelocity(desiredShooterVelocity);

		// If-statement to see if motor is spun up
		// if (shooter.isUpToSpeed())
		if (shooter.isUpToSpeed())
		{
			indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_SHOOT_VELOCITY);
		}
	}

	@Override
	public void end(boolean interrupted)
	{
		shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY);
		indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY);
	}

	@Override
	public boolean isFinished()
	{
		return endAutomatically && !indexer.hasNote();
	}
}
