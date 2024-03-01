package frc.lib.modules.tankdrivetrain.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.tankdrivetrain.TankDrivetrainSubsystem;

public class SpeedModCommand extends Command
{

    private TankDrivetrainSubsystem drivetrain;
    private double speedMod;

    public SpeedModCommand(double speedMod, TankDrivetrainSubsystem drivetrain)
    {
        this.speedMod = speedMod;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize()
    {
        // Set the speed mod in the drivetrain subsystem
        drivetrain.setSpeedMod(speedMod);
    }

}
