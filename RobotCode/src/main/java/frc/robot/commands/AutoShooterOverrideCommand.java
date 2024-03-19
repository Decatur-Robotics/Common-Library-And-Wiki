package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.core.util.TeamCountdown;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.ShooterMountConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShooterOverrideCommand extends Command 
{
    
    private ShooterMountSubsystem shooterMount;
    private ShooterSubsystem shooter;
	private IndexerSubsystem indexer;
    private TeamCountdown countdown;

    public AutoShooterOverrideCommand(ShooterMountSubsystem shooterMount, ShooterSubsystem shooter, IndexerSubsystem indexer)
    {
        this.shooterMount = shooterMount;
        this.shooter = shooter;
        this.indexer = indexer;
        this.countdown = new TeamCountdown(500);

        addRequirements(shooterMount, shooter, indexer);
    }

    @Override
    public void initialize()
    {
        shooterMount.setTargetRotation(ShooterMountConstants.SHOOTER_MOUNT_SPEAKER_ANGLE_FIXED);
        shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_SPEAKER_VELOCITY);
        indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_SHOOT_VELOCITY);
    }

    @Override
    public void end(boolean isFinished)
    {
        shooterMount.setTargetRotation(ShooterMountConstants.SHOOTER_MOUNT_MIN_ANGLE);
        shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY);
        indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY);
    }

    @Override
    public boolean isFinished()
    {
        return countdown.isDone();
    }

}
