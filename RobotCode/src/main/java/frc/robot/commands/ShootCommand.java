package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.lib.core.util.Timer;

/**
 * <p>
 * Sets the shooter motors at {@link ShooterConstants#SHOOTER_SPEAKER_VELOCITY}, waits for
 * {@link ShooterConstants#SHOOT_TIME}, and then ends.
 * </p>
 * <p>
 * Requires {@link ShooterSubsystem}, {@link IndexerSubsystem}.
 * </p>
 */
public class ShootCommand extends Command
{

    private final ShooterSubsystem Shooter;
    private final IndexerSubsystem Indexer;

    private Timer endTimer;

    public ShootCommand(ShooterSubsystem shooter, IndexerSubsystem indexer)
    {
        Shooter = shooter;
        Indexer = indexer;

        addRequirements(Shooter, Indexer);
    }

    @Override
    public void initialize()
    {
        Shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_SPEAKER_VELOCITY,
                "Running ShooterInstantCommand");
        endTimer = new Timer(ShooterConstants.SHOOT_TIME);
    }

    @Override
    public void execute()
    {
        if (Shooter.isUpToSpeed())
            Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_SHOOT_VELOCITY,
                    "Shooter is up to speed");
        else
            Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY,
                    "Shooter is not up to speed");

    }

    @Override
    public void end(boolean interrupted)
    {
        Shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY,
                "ShooterInstantCommand ended");
        Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY,
                "ShooterInstantCommand ended");
    }

    @Override
    public boolean isFinished()
    {
        return endTimer.isDone();
    }

}
