package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.leds.Color;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.ShooterMountConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.LedSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AmpCommand extends Command
{

	private ShooterSubsystem shooter;
	private IndexerSubsystem indexer;
	private ShooterMountSubsystem shooterMount;
	private LedSubsystem leds;

	private boolean adjustReady;

	public AmpCommand(ShooterMountSubsystem shooterMount, ShooterSubsystem shooter,
			IndexerSubsystem indexer, LedSubsystem leds)
	{
		this.shooterMount = shooterMount;
		this.shooter = shooter;
		this.indexer = indexer;
		this.leds = leds;

		addRequirements(shooterMount, shooter, indexer, leds);
	}

	@Override
	public void initialize()
	{
		shooterMount
				.setTargetRotation(ShooterMountConstants.SHOOTER_MOUNT_INITIAL_AMP_ANGLE_OFFSET);
		shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_AMP_VELOCITY);
		indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_AMP_VELOCITY);

		adjustReady = false;
	}

	@Override
	public void execute()
	{
		if (!indexer.hasNote())
		{
			adjustReady = true;
		}

		if (indexer.hasNote() && adjustReady)
		{
			shooterMount
					.setTargetRotation(ShooterMountConstants.SHOOTER_MOUNT_ENDING_AMP_ANGLE_OFFSET);
		}
	}

	@Override
	public void end(boolean isFinished)
	{
		shooterMount.setTargetRotation(0);
		shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY);
		indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY);
		leds.flashAllPixels(Color.Blue);
	}

}
