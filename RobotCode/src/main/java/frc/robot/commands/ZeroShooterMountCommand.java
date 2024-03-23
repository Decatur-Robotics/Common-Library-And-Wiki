package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class ZeroShooterMountCommand extends Command 
{

    ShooterMountSubsystem shooterMount;
    
    public ZeroShooterMountCommand(ShooterMountSubsystem shooterMount)
    {
        this.shooterMount = shooterMount;

        addRequirements(shooterMount);
    }

    @Override
    public void initialize()
    {
        shooterMount.disableShooterMount();
    }

    @Override
    public void end(boolean isFinished)
    {
        shooterMount.zeroShooterMount();
    }

}
