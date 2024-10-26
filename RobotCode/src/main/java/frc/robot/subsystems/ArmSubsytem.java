package frc.robot.subsystems;

import org.opencv.core.Mat;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.Ports;

public class ArmSubsytem extends SubsystemBase
{
	private TalonFX armMotorLower, armMotorUpper;
	private double armLowerMinAngle, armUpperMinAngle;
	private double targetAngleLower, targetAngleUpper;

	private double offset = 0;

	private MotionMagicDutyCycle motorControlRequest;

	public ArmSubsytem()
	{

		armMotorLower = new TalonFX(Ports.ARM_LOWER_MOTOR);
		armMotorUpper = new TalonFX(Ports.ARM_UPPER_MOTOR);

		TalonFXConfiguration mainMotorConfigs = new TalonFXConfiguration();

		mainMotorConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
		mainMotorConfigs.CurrentLimits.StatorCurrentLimit = 60;

		// PIDs
		Slot0Configs pidSlot0Configs = mainMotorConfigs.Slot0;
		pidSlot0Configs.kP = ArmConstants.ARM_LOWER_KP;
		pidSlot0Configs.kI = ArmConstants.ARM_LOWER_KI;
		pidSlot0Configs.kD = ArmConstants.ARM_LOWER_KD;
		pidSlot0Configs.kS = ArmConstants.ARM_LOWER_KS;
		pidSlot0Configs.kV = ArmConstants.ARM_LOWER_KV;
		pidSlot0Configs.kA = ArmConstants.ARM_LOWER_KA;
		pidSlot0Configs.kG = ArmConstants.ARM_LOWER_KG;

		pidSlot0Configs.kP = ArmConstants.ARM_UPPER_KP;
		pidSlot0Configs.kI = ArmConstants.ARM_UPPER_KI;
		pidSlot0Configs.kD = ArmConstants.ARM_UPPER_KD;
		pidSlot0Configs.kS = ArmConstants.ARM_UPPER_KS;
		pidSlot0Configs.kV = ArmConstants.ARM_UPPER_KV;
		pidSlot0Configs.kA = ArmConstants.ARM_UPPER_KA;
		pidSlot0Configs.kG = ArmConstants.ARM_UPPER_KG;

		armMotorLower.getConfigurator().apply(mainMotorConfigs);
		armMotorUpper.getConfigurator().apply(mainMotorConfigs);

		armLowerMinAngle = 1 / 360.0;
		armUpperMinAngle = 1 / 360.0;

		targetAngleLower = 1;
		targetAngleUpper = 1;

		motorControlRequest = new MotionMagicDutyCycle(offset);

	}

	public void setLowerTargetRotation(double targetAngleLower)
	{
		targetAngleLower = Math.max(
				Math.min(targetAngleLower, ArmConstants.ARM_LOWER_MAX_ANGLE_OFFSET),
				armLowerMinAngle);

		double gravityFeedForward = Math
				.cos(ArmConstants.ARM_MIN_ANGLE_RADIANS + Math.toRadians(targetAngleLower * 360))
				* ArmConstants.ARM_LOWER_KG;

		armMotorLower.setControl(motorControlRequest.withPosition(targetAngleLower + offset)
				.withFeedForward(gravityFeedForward));

	}

	public void setUpperTargetRotation(double targetAngleUpper)
	{
		targetAngleUpper = Math.max(
				Math.min(targetAngleUpper, ArmConstants.ARM_UPPER_MAX_ANGLE_OFFSET),
				armUpperMinAngle);

		double gravityFeedForward = Math
				.cos(ArmConstants.ARM_MIN_ANGLE_RADIANS + Math.toRadians(targetAngleUpper * 360))
				* ArmConstants.ARM_UPPER_KG;

		armMotorUpper.setControl(motorControlRequest.withPosition(targetAngleUpper + offset)
				.withFeedForward(gravityFeedForward));

	}
}
