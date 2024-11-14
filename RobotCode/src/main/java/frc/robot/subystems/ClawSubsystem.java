package frc.robot.subystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.lib.core.motors.TeamSparkMAX;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.Ports;

public class ClawSubsystem
{
	private SparkPIDController clawPidController;
	private CANSparkMax clawMotorLeft, clawMotorRight;

	public ClawSubsystem()
	{
		CANSparkMax clawMotorLeft = new CANSparkMax(Ports.clawMotorLeft, MotorType.kBrushless);
		CANSparkMax clawMotorRight = new CANSparkMax(Ports.clawMotorRight, MotorType.kBrushless);
		clawMotorLeft.follow(clawMotorRight);

		clawMotorLeft.setInverted(true);

		clawPidController = clawMotorRight.getPIDController();
		clawPidController.setP(ClawConstants.CLAW_MOTOR_KP);
		clawPidController.setI(ClawConstants.CLAW_MOTOR_KI);
		clawPidController.setD(ClawConstants.CLAW_MOTOR_KD);

	}
}