package frc.lib.modules.tankdrivetrain;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TankDrivetrainSubsystem extends SubsystemBase
{

    private TeamTalonFX rightDriveFalconMain, leftDriveFalconMain, rightDriveFalconSub,
            leftDriveFalconSub;

    private double leftPowerDesired;
    private double rightPowerDesired;

    private String reason;

    private SlewRateLimiter powerRamping;

    private double speedMod;

    public TankDrivetrainSubsystem(int rightMainPort, int leftMainPort, int rightSubPort,
            int leftSubPort)
    {
        rightDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.RightMain", rightMainPort);
        leftDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.LeftMain", leftMainPort);
        rightDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.RightSub", rightSubPort);
        leftDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.LeftSub", leftSubPort);

        // IMPLEMENT AND TEST A SLEW RATE LIMITER ON THE SHOWBOT BEFORE WE ADD THIS TO COMMON LIB
        powerRamping = new SlewRateLimiter(TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);

        // create a motor config object
        TalonFXConfiguration config = new TalonFXConfiguration();

        // This configures the falcons to limit their current
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.SupplyCurrentLimit = 55;
        config.CurrentLimits.SupplyTimeThreshold = TankDrivetrainConstants.CURRENT_TIMEOUT_MS;

        // This configures the sub motors to follow the main falcons
        leftDriveFalconSub.setControl(new Follower(leftDriveFalconMain.getDeviceID(), false));
        rightDriveFalconSub.setControl(new Follower(rightDriveFalconMain.getDeviceID(), false));

        // This inverts the left falcons
        rightDriveFalconMain.setInverted(false);
        rightDriveFalconSub.setInverted(false);
        leftDriveFalconMain.setInverted(true);
        leftDriveFalconSub.setInverted(true);

        // This sets the neutral mode of the falcons to brake
        rightDriveFalconMain.setNeutralMode(NeutralModeValue.Brake);
        rightDriveFalconSub.setNeutralMode(NeutralModeValue.Brake);
        leftDriveFalconMain.setNeutralMode(NeutralModeValue.Brake);
        leftDriveFalconSub.setNeutralMode(NeutralModeValue.Brake);

        // config the current limits
        rightDriveFalconMain.getConfigurator().apply(config);
        rightDriveFalconSub.getConfigurator().apply(config);
        leftDriveFalconMain.getConfigurator().apply(config);
        leftDriveFalconSub.getConfigurator().apply(config);
    }

    public void setSpeedMod(double speedMod)
    {
        // Sets a new speed mod
        this.speedMod = speedMod;
    }

    public double getSpeedMod()
    {
        // Returns the current speed mod
        return speedMod;
    }

    private double calculateClampedPower(double powerDesired)
    {
        // Clamp given power between -1 and 1
        return Math.max(Math.min(1, powerDesired), -1);
    }

    private double calculateRampedPower(double powerDesired, double powerCurrent)
    {
        // Check if the power change exceeded the max power change, and limit the power change if so
        if (powerDesired < powerCurrent)
        {
            return Math.max(powerDesired,
                    powerCurrent - TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        else if (powerDesired < powerCurrent)
        {
            return Math.min(powerDesired,
                    powerCurrent + TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);
        }
        return powerDesired;
    }

    public void setMotorPowers(double leftPowerDesired, double rightPowerDesired, String reason)
    {
        // Set desired motor powers
        this.leftPowerDesired = leftPowerDesired;
        this.rightPowerDesired = rightPowerDesired;

        // Set reason for desiring those motor powers
        this.reason = reason;
    }

    @Override
    public void periodic()
    {
        // Get the current motor powers
        double rightPowerCurrent = rightDriveFalconMain.get();
        double leftPowerCurrent = leftDriveFalconMain.get();

        // Create final power variables to perform math on
        double finalLeftPower = leftPowerDesired;
        double finalRightPower = rightPowerDesired;

        // Multiply motor powers by the speed mod
        finalLeftPower *= speedMod;
        finalRightPower *= speedMod;

        // Calculate ramped motor powers
        finalLeftPower = calculateRampedPower(finalLeftPower, leftPowerCurrent);
        finalRightPower = calculateRampedPower(finalRightPower, rightPowerCurrent);

        // Calculate clamped motor powers
        finalLeftPower = calculateClampedPower(finalLeftPower);
        finalRightPower = calculateClampedPower(finalRightPower);

        // Set final motor powers
        leftDriveFalconMain.set(finalLeftPower, reason);
        rightDriveFalconMain.set(finalRightPower, reason);
    }

}