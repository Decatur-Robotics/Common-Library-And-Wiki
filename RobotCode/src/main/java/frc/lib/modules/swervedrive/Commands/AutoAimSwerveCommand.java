package frc.lib.modules.swervedrive.Commands;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.lib.core.ILogSource;
import frc.lib.core.util.Timer;

/**
 * Rotates the chassis towards the speaker. Intended to work with PathPlanner paths. Will end once
 * the note is fired. See {@link ShooterConstants#SHOOT_TIME} for the time to wait after shooting.
 */
public class AutoAimSwerveCommand extends Command implements ILogSource
{

    private final SwerveDriveSubsystem Swerve;
    private final VisionSubsystem Vision;
    private final IndexerSubsystem Indexer;
    private final ShooterMountSubsystem ShooterMount;

    /** Used to prevent ending before the note has left the shooter */
    private Optional<Timer> timer;

    public AutoAimSwerveCommand(SwerveDriveSubsystem swerve, VisionSubsystem vision,
            IndexerSubsystem indexer, ShooterMountSubsystem shooter)
    {
        Swerve = swerve;
        Vision = vision;
        Indexer = indexer;
        ShooterMount = shooter;

        timer = Optional.empty();

        addRequirements(Indexer);
    }

    @Override
    public void initialize()
    {
        logInfo("Starting AutoAimSwerveCommand");
        Swerve.setRotationController(() -> Swerve.getRotationalVelocityToSpeaker(Vision));
    }

    @Override
    public void execute()
    {
        // Spin feeder motors if in target
        if (Vision.isInShooterRange() && Math
                .abs(Swerve.getRotationToSpeaker(Vision)) < VisionConstants.CHASSIS_AIM_THRESHOLD)
        {
            // Spin feeder motors
            Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_SHOOT_VELOCITY,
                    "Within aim threshold");

            if (timer.isEmpty())
            {
                logInfo("Starting shooter...");
                timer = Optional.of(new Timer(ShooterConstants.SHOOT_TIME));
            }
        }
        else if (!Indexer.hasNote())
        {
            if (timer.isPresent())
                logInfo("Stopping shooter...");
            Indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY,
                    "No note in indexer");
        }
    }

    @Override
    public void end(boolean interrupted)
    {
        logInfo("Ending AutoAimSwerveCommand");
        Swerve.setRotationController(null);
        timer = Optional.empty(); // We need to reset timer so we can reuse this instance
    }

    @Override
    public boolean isFinished()
    {
        return timer.isPresent() && timer.get().isDone();
    }
}