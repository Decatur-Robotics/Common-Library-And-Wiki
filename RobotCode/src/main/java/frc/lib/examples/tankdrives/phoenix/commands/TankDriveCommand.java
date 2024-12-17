package frc.lib.examples.tankdrives.phoenix.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.examples.tankdrives.phoenix.TankDriveSubsystem;

public class TankDriveCommand extends Command {
    
    private TankDriveSubsystem tankDrive;

    private double rightSpeed, leftSpeed;
    
    public TankDriveCommand(double rightSpeed, double leftSpeed, TankDriveSubsystem tankDrive) {
        this.rightSpeed = rightSpeed;
        this.leftSpeed = leftSpeed;
        
        this.tankDrive = tankDrive;

        addRequirements(tankDrive);
    }

    @Override
    public void initialize() {
        tankDrive.setChassisSpeeds(leftSpeed, rightSpeed);
    }

}
