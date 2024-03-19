package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class IntakeReverseCommand extends Command {
    
    private IntakeSubsystem intake;
    private IndexerSubsystem indexer;
    private ShooterSubsystem shooter;

    public IntakeReverseCommand(IntakeSubsystem intake, IndexerSubsystem indexer, ShooterSubsystem shooter)
    {
        this.intake = intake;
        this.indexer = indexer;
        this.shooter = shooter;
        addRequirements(intake, indexer, shooter);
    }

    @Override
    public void initialize()
    {
        intake.setDesiredVelocity(IntakeConstants.INTAKE_REVERSE_VELOCITY);
		intake.setDesiredRotation(IntakeConstants.INTAKE_DEPLOYED_ROTATION, IntakeConstants.INTAKE_DEPLOYMENT_SLOT_DOWN);
        indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REVERSE_VELOCITY);
        shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REVERSE_VELOCITY);
    }

    @Override
    public void end(boolean isFinished)
    {
        intake.setDesiredVelocity(IntakeConstants.INTAKE_REST_VELOCITY);
		intake.setDesiredRotation(IntakeConstants.INTAKE_RETRACTED_ROTATION, IntakeConstants.INTAKE_DEPLOYMENT_SLOT_UP);
        indexer.setIndexerMotorVelocity(IndexerConstants.INDEXER_REST_VELOCITY);
        shooter.setShooterMotorVelocity(ShooterConstants.SHOOTER_REST_VELOCITY);
    }

}
