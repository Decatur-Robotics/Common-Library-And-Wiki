package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVelocityDutyCycle;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.RobotContainer;
import frc.robot.constants.Ports;
import frc.robot.constants.ShooterConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;

public class ShooterSubsystem extends SubsystemBase {

	private double desiredShooterVelocity;

	private TeamTalonFX shooterMotorRight, shooterMotorLeft;

	private MotionMagicVelocityDutyCycle motorControlRequest;

	public ShooterSubsystem() {
		// Sets default shooter motor power
		desiredShooterVelocity = ShooterConstants.SHOOTER_REST_VELOCITY;

		// Initializes motor object
		shooterMotorRight = new TeamTalonFX("Left Shooter Motor Main", Ports.SHOOTER_MOTOR_RIGHT,
				"Default Name");
		shooterMotorLeft = new TeamTalonFX("Right Shooter Motor Main", Ports.SHOOTER_MOTOR_LEFT,
				"Default Name");

		shooterMotorRight.setNeutralMode(NeutralModeValue.Brake);
		shooterMotorLeft.setNeutralMode(NeutralModeValue.Brake);

		// create configurator
		TalonFXConfiguration mainMotorConfigs = new TalonFXConfiguration();

		// set pid profiles configs
		Slot0Configs pidSlot0Configs = mainMotorConfigs.Slot0;
		pidSlot0Configs.kP = ShooterConstants.SHOOTER_KP;
		pidSlot0Configs.kI = ShooterConstants.SHOOTER_KI;
		pidSlot0Configs.kD = ShooterConstants.SHOOTER_KD;
		pidSlot0Configs.kS = ShooterConstants.SHOOTER_KS;
		pidSlot0Configs.kV = ShooterConstants.SHOOTER_KV;
		pidSlot0Configs.kA = ShooterConstants.SHOOTER_KA;

		// set motionmagic velocity configs
		MotionMagicConfigs motionMagicVelocityConfigs = mainMotorConfigs.MotionMagic;
		motionMagicVelocityConfigs.MotionMagicCruiseVelocity = ShooterConstants.SHOOTER_CRUISE_VELOCITY;
		motionMagicVelocityConfigs.MotionMagicAcceleration = ShooterConstants.SHOOTER_ACCELERATION;

		// config the main motor
		shooterMotorRight.getConfigurator().apply(mainMotorConfigs);
		shooterMotorLeft.getConfigurator().apply(mainMotorConfigs);

		motorControlRequest = new MotionMagicVelocityDutyCycle(desiredShooterVelocity);
		shooterMotorRight.setControl(motorControlRequest.withVelocity(desiredShooterVelocity));
		shooterMotorLeft.setControl(motorControlRequest.withVelocity(desiredShooterVelocity));

		RobotContainer.getShuffleboardTab().addDouble("Actual Shooter Velocity",
				() -> shooterMotorLeft.getRotorVelocity().getValueAsDouble());
		RobotContainer.getShuffleboardTab().addDouble("Desired Shooter Velocity",
				() -> desiredShooterVelocity);
	}

	@Override
	public void periodic() {
		if (shooterMotorRight.hasResetOccurred()
				|| shooterMotorLeft.hasResetOccurred()) {
			shooterMotorRight.optimizeBusUtilization();
			shooterMotorLeft.optimizeBusUtilization();
			shooterMotorRight.getRotorPosition().setUpdateFrequency(20);
		}
	}

	public double getVelocity() {
		return shooterMotorLeft.getRotorVelocity().getValueAsDouble();
	}

	/**
	 * This is clamping the shooter motor power
	 */
	public void setShooterMotorVelocity(double desiredShooterVelocity, String reason) {
		this.desiredShooterVelocity = Math.max(
				Math.min(ShooterConstants.SHOOTER_MAX_VELOCITY, desiredShooterVelocity),
				-ShooterConstants.SHOOTER_MAX_VELOCITY);
		shooterMotorRight.setControl(motorControlRequest.withVelocity(-desiredShooterVelocity));
		shooterMotorLeft.setControl(motorControlRequest.withVelocity(desiredShooterVelocity));
	}

	public boolean isUpToSpeed() {
		return Math.abs(shooterMotorLeft.getRotorVelocity().getValueAsDouble()
				- desiredShooterVelocity) < ShooterConstants.SHOOTER_VELOCITY_TOLERANCE;
	}

}
