package frc.lib.modules.tankdrivetrain;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;

public class TankDrivetrainSubsystem extends SubsystemBase {

	private TeamTalonFX RightDriveFalconMain, LeftDriveFalconMain, RightDriveFalconSub,
			LeftDriveFalconSub;

	private double leftPowerDesired;
	private double rightPowerDesired;
	private double speedMod;

	private String reason;

	public TankDrivetrainSubsystem(int rightMainPort, int leftMainPort, int rightSubPort,
			int leftSubPort) {
		RightDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.RightMain", rightMainPort);
		LeftDriveFalconMain = new TeamTalonFX("Subsystems.DriveTrain.LeftMain", leftMainPort);
		RightDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.RightSub", rightSubPort);
		LeftDriveFalconSub = new TeamTalonFX("Subsystems.DriveTrain.LeftSub", leftSubPort);

		// IMPLEMENT AND TEST A SLEW RATE LIMITER ON THE SHOWBOT BEFORE WE ADD THIS TO COMMON LIB
		// powerRamping = new SlewRateLimiter(TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);

		// create a motor config object
		TalonFXConfiguration config = new TalonFXConfiguration();

		// This configures the falcons to limit their current
		config.CurrentLimits.SupplyCurrentLimitEnable = true;
		config.CurrentLimits.SupplyCurrentLimit = 55;
		config.CurrentLimits.SupplyTimeThreshold = TankDrivetrainConstants.CURRENT_TIMEOUT_MS;

		// This configures the sub motors to follow the main falcons
		LeftDriveFalconSub.setControl(new Follower(LeftDriveFalconMain.getDeviceID(), false));
		RightDriveFalconSub.setControl(new Follower(RightDriveFalconMain.getDeviceID(), false));

		// This inverts the left falcons
		RightDriveFalconMain.setInverted(false);
		RightDriveFalconSub.setInverted(false);
		LeftDriveFalconMain.setInverted(true);
		LeftDriveFalconSub.setInverted(true);

		// This sets the neutral mode of the falcons to brake
		RightDriveFalconMain.setNeutralMode(NeutralModeValue.Brake);
		RightDriveFalconSub.setNeutralMode(NeutralModeValue.Brake);
		LeftDriveFalconMain.setNeutralMode(NeutralModeValue.Brake);
		LeftDriveFalconSub.setNeutralMode(NeutralModeValue.Brake);

		// config the current limits
		RightDriveFalconMain.getConfigurator().apply(config);
		RightDriveFalconSub.getConfigurator().apply(config);
		LeftDriveFalconMain.getConfigurator().apply(config);
		LeftDriveFalconSub.getConfigurator().apply(config);

		speedMod = 1;
	}

	public void setSpeedMod(double speedMod) {
		// Sets a new speed mod
		this.speedMod = speedMod;
	}

	public double getSpeedMod() {
		// Returns the current speed mod
		return speedMod;
	}

	private double calculateClampedPower(double powerDesired) {
		// Clamp given power between -1 and 1
		return Math.max(Math.min(1, powerDesired), -1);
	}

	private double calculateRampedPower(double powerDesired, double powerCurrent) {
		// Check if the power change exceeded the max power change, and limit the power change if so
		if (powerDesired < powerCurrent) {
			return Math.max(powerDesired,
					powerCurrent - TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);
		} else if (powerDesired < powerCurrent) {
			return Math.min(powerDesired,
					powerCurrent + TankDrivetrainConstants.DRIVETRAIN_MAX_POWER_CHANGE);
		}
		return powerDesired;
	}

	public void setMotorPowers(double leftPowerDesired, double rightPowerDesired, String reason) {
		// Set desired motor powers
		LeftDriveFalconMain.set(leftPowerDesired);
		RightDriveFalconMain.set(rightPowerDesired);

		// Set reason for desiring those motor powers
		this.reason = reason;
	}

	@Override
	public void periodic() {
		// Get the current motor powers
		double rightPowerCurrent = RightDriveFalconMain.get();
		double leftPowerCurrent = LeftDriveFalconMain.get();

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
		LeftDriveFalconMain.set(finalLeftPower, reason);
		RightDriveFalconMain.set(finalRightPower, reason);
	}

}