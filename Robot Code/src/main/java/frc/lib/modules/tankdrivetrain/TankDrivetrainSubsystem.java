package frc.lib.modules.tankdrivetrain;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.ITeamTalon;
import frc.lib.core.motors.TeamTalonFX;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;



public class TankDrivetrainSubsystem extends SubsystemBase {
    ITeamTalon rightDriveFalconMain;
    ITeamTalon leftDriveFalconMain;
    ITeamTalon rightDriveFalconSub;
    ITeamTalon leftDriveFalconSub;

    SlewRateLimiter powerRamping;

    public double speedMod;

    public TankDrivetrainSubsystem(int rightMainPort, int leftMainPort, int rightSubPort, int leftSubPort) {
        rightDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.RightMain", rightMainPort);
        leftDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.LeftMain", leftMainPort);
        rightDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.RightSub", rightSubPort);
        leftDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.LeftSub", leftSubPort);
        
        // IMPLEMENT AND TEST A SLEW RATE LIMITER ON THE SHOWBOT BEFORE WE ADD THIS TO COMMON LIB
        powerRamping = new SlewRateLimiter(TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);

        TalonFXConfiguration config = new TalonFXConfiguration();
        SupplyCurrentLimitConfiguration currentConfig = new SupplyCurrentLimitConfiguration();
        currentConfig.currentLimit = 55;

        // This configures the falcons to use their internal encoders
        rightDriveFalconMain.configBaseAllSettings(config);
        rightDriveFalconSub.configBaseAllSettings(config);
        leftDriveFalconMain.configBaseAllSettings(config);
        leftDriveFalconSub.configBaseAllSettings(config);

        // This configures the falcons to limit their current
        rightDriveFalconMain.configSupplyCurrentLimit(currentConfig, TankDrivetrainConstants.CURRENT_TIMEOUT_MS);
        rightDriveFalconSub.configSupplyCurrentLimit(currentConfig, TankDrivetrainConstants.CURRENT_TIMEOUT_MS);
        leftDriveFalconMain.configSupplyCurrentLimit(currentConfig, TankDrivetrainConstants.CURRENT_TIMEOUT_MS);
        leftDriveFalconSub.configSupplyCurrentLimit(currentConfig, TankDrivetrainConstants.CURRENT_TIMEOUT_MS);

        // This configures the sub motors to follow the main falcons
        leftDriveFalconSub.follow(leftDriveFalconMain);
        rightDriveFalconSub.follow(rightDriveFalconMain);

        // This inverts the left falcons
        rightDriveFalconMain.setInverted(false);
        rightDriveFalconSub.setInverted(false);
        leftDriveFalconMain.setInverted(true);
        leftDriveFalconSub.setInverted(true);

        // This sets the neutral mode of the falcons to brake
        rightDriveFalconMain.setNeutralMode(NeutralMode.Brake);
        rightDriveFalconSub.setNeutralMode(NeutralMode.Brake);
        leftDriveFalconMain.setNeutralMode(NeutralMode.Brake);
        leftDriveFalconSub.setNeutralMode(NeutralMode.Brake);
    }

    public void setSpeedMod(double newSpeedMod) {
        // Sets a new speed mod
        speedMod = newSpeedMod;
    }

    private double getClampedPower(double powerDesired) {
        // Clamp given power between -1 and 1
        return Math.max(Math.min(1, powerDesired), -1);
    }

    private double getRampedPower(double powerDesired, double powerCurrent) {
        // Check if the power change exceeded the max power change, and limit the power change if so
        if (powerDesired < powerCurrent) {
            return Math.max(powerDesired, powerCurrent - TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        else if (powerDesired < powerCurrent) {
            return Math.min(powerDesired, powerCurrent + TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        return powerDesired;
    }

    public void setMotorPowers(double leftPowerDesired, double rightPowerDesired, String reason) {
        // Get the current motor powers
        double rightPowerCurrent = rightDriveFalconMain.get();
        double leftPowerCurrent = leftDriveFalconMain.get();

        // Multiply motor powers by the speed mod
        leftPowerDesired *= speedMod;
        rightPowerDesired *= speedMod;

        // Ramp motor powers
        leftPowerDesired = getRampedPower(leftPowerDesired, leftPowerCurrent);
        rightPowerDesired = getRampedPower(rightPowerDesired, rightPowerCurrent);

        // Clamp motor powers
        leftPowerDesired = getClampedPower(leftPowerDesired);
        rightPowerDesired = getClampedPower(rightPowerDesired);

        // Set final motor powers
        rightDriveFalconMain.set(rightPowerDesired, reason);
        leftDriveFalconMain.set(leftPowerDesired, reason);
    }
}