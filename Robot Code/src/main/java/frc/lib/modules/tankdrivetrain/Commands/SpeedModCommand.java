package frc.lib.modules.tankdrivetrain.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.tankdrivetrain.TankDrivetrainSubsystem;

public class SpeedModCommand extends CommandBase {
    private TankDrivetrainSubsystem drivetrain;
    private double speedMod;

    public SpeedModCommand(double speedMod, TankDrivetrainSubsystem drivetrain) {
        this.speedMod = speedMod;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    public void initialize() {
        // Set the speed mod in the drivetrain subsystem
        drivetrain.setSpeedMod(speedMod);
    }

    public boolean isFinished() {
        // End the command immediately
        return true;
    }
}
