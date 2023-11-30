package frc.lib.modules.tankdrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.ITeamTalon;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.constants.Ports;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;



public class TankDriveSubsystem extends SubsystemBase {
    ITeamTalon rightDriveFalconMain;
    ITeamTalon leftDriveFalconMain;
    ITeamTalon rightDriveFalconSub;
    ITeamTalon leftDriveFalconSub;

    public double speedMod;

    public TankDriveSubsystem(int rightMainPort, int leftMainPort, int rightSubPort, int leftSubPort) {
        rightDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.RightMain", TankDriveConstants.RIGHT_DRIVE_FALCON_MAIN);
        leftDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.LeftMain", TankDriveConstants.LEFT_DRIVE_FALCON_MAIN);
        rightDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.RightSub", TankDriveConstants.RIGHT_DRIVE_FALCON_SUB);
        leftDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.LeftSub", TankDriveConstants.LEFT_DRIVE_FALCON_SUB);
        
        // This configures the falcons to use their internal encoders
        TalonFXConfiguration configs = new TalonFXConfiguration();

        rightDriveFalconMain.configBaseAllSettings(configs);
        leftDriveFalconMain.configBaseAllSettings(configs);

        leftDriveFalconSub.follow(leftDriveFalconMain);
        rightDriveFalconSub.follow(rightDriveFalconMain);

        rightDriveFalconMain.setInverted(false);
        rightDriveFalconSub.setInverted(false);
        
        leftDriveFalconMain.setInverted(true);
        leftDriveFalconSub.setInverted(true);

        leftDriveFalconMain.setNeutralMode(NeutralMode.Brake);
        rightDriveFalconMain.setNeutralMode(NeutralMode.Brake);
    }

    public void setSpeedMod(double newSpeedMod) {
        speedMod = newSpeedMod;
    }

    private double getCappedPower(double powerDesired) {
        return Math.max(Math.min(1, powerDesired), -1);
    }

    private double getRampedPower(double powerDesired, double powerCurrent) {
        if (powerDesired < powerCurrent) {
            return Math.max(powerDesired, powerCurrent - TankDriveConstants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        else if (powerDesired < powerCurrent) {
            return Math.min(powerDesired, powerCurrent + TankDriveConstants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        return powerDesired;
    }

    public void setMotorPowers(double leftPowerDesired, double rightPowerDesired, String reason) {
        double rightPowerCurrent = rightDriveFalconMain.get();
        double leftPowerCurrent = leftDriveFalconMain.get();

        leftPowerDesired *= speedMod;
        rightPowerDesired *= speedMod;

        leftPowerDesired = getRampedPower(leftPowerDesired, leftPowerCurrent);
        rightPowerDesired = getRampedPower(rightPowerDesired, rightPowerCurrent);

        leftPowerDesired = getCappedPower(leftPowerDesired);
        rightPowerDesired = getCappedPower(rightPowerDesired);

        rightDriveFalconMain.set(rightPowerDesired, reason);
        leftDriveFalconMain.set(leftPowerDesired, reason);
        
    }
}