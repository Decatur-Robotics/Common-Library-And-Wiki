package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.RobotContainer;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.Ports;

public class ArmSubsytem extends SubsystemBase {
	private TalonFX armMotorRight, armMotorLeft;
	private double armRightMinAngle, armLeftMinAngle;
	private double targetAngleLower, targetAngle;

	private MotionMagicDutyCycle motorControlRequest;

	public ArmSubsytem()
	{

		armMotorRight = new TalonFX(Ports.ARM_LOWER_MOTOR);
		armMotorLeft = new TalonFX(Ports.ARM_UPPER_MOTOR);

		armMotorRight.setControl(new Follower(armMotorLeft.getDeviceID(), true));
		armMotorRight.setInverted(true);

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

		armMotorRight.getConfigurator().apply(mainMotorConfigs);
		armMotorLeft.getConfigurator().apply(mainMotorConfigs);

		armLeftMinAngle = 1 / 360.0;

		targetAngle = 1;

	

		RobotContainer.getShuffleboardTab().add("Actual Arm Mount Lower Rotation", armMotorRight.getRotorPosition().getValueAsDouble());
		RobotContainer.getShuffleboardTab().add("ActualArm Mount Upper Rotation", armMotorLeft.getRotorPosition().getValueAsDouble());
		RobotContainer.getShuffleboardTab().add("Target Arm Mount Lower Rotation", targetAngleLower);
		RobotContainer.getShuffleboardTab().add("Target Arm Mount Upper Rotation", targetAngle);
		

	}

	public void periodic(){
		if(armMotorRight.hasResetOccurred()||armMotorLeft.hasResetOccurred()){
			armMotorRight.optimizeBusUtilization();
			armMotorLeft.optimizeBusUtilization();
			
			armMotorLeft.getRotorPosition().setUpdateFrequency(20);
		}
	}

	public void setTargetRotation(double targetAngle)
	{
		targetAngle = Math.max(
				Math.min(targetAngle, 0),
				armRightMinAngle);

		double gravityFeedForward = Math
				.cos(ArmConstants.ARM_LOWER_MIN_ANGLE_RADIANS + Math.toRadians(targetAngle * 360))
				* ArmConstants.ARM_LOWER_KG;

		armMotorLeft.setControl(motorControlRequest.withPosition(targetAngle)
				.withFeedForward(gravityFeedForward));

	}

	public boolean isAtTarget()
	{
		if (Math.abs(armMotorLeft.getRotorPosition().getValueAsDouble() - (targetAngleLower)) < ArmConstants.ARM_DEADBAND)
		{
			return true;
		}
		return false;
	}

}
