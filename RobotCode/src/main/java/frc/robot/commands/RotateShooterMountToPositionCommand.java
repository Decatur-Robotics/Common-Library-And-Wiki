package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterMountSubsystem;

public class RotateShooterMountToPositionCommand extends Command
{

    private final ShooterMountSubsystem ShooterMountSubsystem;

    private double position;

    public RotateShooterMountToPositionCommand(ShooterMountSubsystem subsystem, double position)
    {
        ShooterMountSubsystem = subsystem;
        this.position = position;

        // We do this before setting the rotation so that any other
        // command controlling the shooter mount will end before we set the rotation
        addRequirements(subsystem);
        subsystem.setGoalRotation(position);
    }

    @Override
    public boolean isFinished()
    {
        // We do the weird static access to avoid going through the variable
        return Math.abs(ShooterMountSubsystem.getCurrentRotation()
                - position) < frc.robot.subsystems.ShooterMountSubsystem.DEADBAND;
    }

}
