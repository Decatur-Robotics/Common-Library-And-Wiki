package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeReverseCommand extends Command {
    
    private IntakeSubsystem intake;
    private IndexerSubsystem indexer;
    private ShooterSubsystem shooter;

    private double desiredSpeedIndexer, desiredSpeedIntake, desiredSpeedShooter;
    private int deploymentPIDSlotUp = 0;
    private int deploymentPIDSlotDown = 1;

    public IntakeReverseCommand(IntakeSubsystem intake, IndexerSubsystem indexer, ShooterSubsystem shooter,
            double desiredSpeedIndexer, double desiredSpeedIntake, double desiredSpeedShooter)
    {
        this.intake = intake;
        this.indexer = indexer;
        this.shooter = shooter;
        this.desiredSpeedIndexer = desiredSpeedIndexer;
        this.desiredSpeedIntake = desiredSpeedIntake;
        this.desiredSpeedShooter = desiredSpeedShooter;
        addRequirements(intake, indexer, shooter);
    }

    @Override
    public void initialize()
    {
        intake.setDesiredVelocity(desiredSpeedIntake, deploymentPIDSlotDown);
        indexer.setIndexerMotorVelocity(desiredSpeedIndexer, "Reverse");
        shooter.setShooterMotorVelocity(desiredSpeedShooter, "Reverse");
    }

    @Override
    public void end(boolean isFinished)
    {
        intake.setDesiredVelocity(0, deploymentPIDSlotUp);
        indexer.setIndexerMotorVelocity(0, "End reverse");
        shooter.setShooterMotorVelocity(0, "End reverse");
    }

}
