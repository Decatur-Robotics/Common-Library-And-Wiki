package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.leds.Color;
import frc.robot.constants.IndexerConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.LedSubsystem;

/**
 * <p>
 * Spins indexer motors until the note has been fired.
 * </p>
 * <p>
 * Requires {@link IndexerSubsystem}.
 * </p>
 */
public class ShootCommand extends Command
{

    private final IndexerSubsystem Indexer;
    private final LedSubsystem Leds;

    public ShootCommand(IndexerSubsystem indexer, LedSubsystem leds)
    {
        Indexer = indexer;
        Leds = leds;

        addRequirements(Indexer);
    }

    @Override
    public void initialize()
    {
        Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_SHOOT_VELOCITY,
                "Shooter is up to speed");
    }

    @Override
    public void end(boolean interrupted)
    {
        Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY,
                "ShooterInstantCommand ended");
        Leds.flashAllPixels(Color.Blue);
    }

    @Override
    public boolean isFinished()
    {
        return !Indexer.hasNote();
    }

}
