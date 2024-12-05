package frc.robot.subystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.FaultID;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;
import frc.robot.RobotContainer;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.Ports;

public class ClawSubsystem extends SubsystemBase
{
	private SparkPIDController clawPidController;
	private CANSparkMax clawMotorLeft, clawMotorRight;
	private double desiredSpeed;
	private DigitalInput limitSwitch;

	public ClawSubsystem()
	{
		CANSparkMax clawMotorLeft = new CANSparkMax(Ports.clawMotorLeft, MotorType.kBrushless);
		CANSparkMax clawMotorRight = new CANSparkMax(Ports.clawMotorRight, MotorType.kBrushless);
		clawMotorLeft.follow(clawMotorRight);
		clawMotorLeft.setInverted(true);

		clawMotorRight.setSmartCurrentLimit(ClawConstants.CLAW_MOTOR_CURRENT_LIMIT);
		clawMotorLeft.setSmartCurrentLimit(ClawConstants.CLAW_MOTOR_CURRENT_LIMIT);

		clawPidController = clawMotorRight.getPIDController();
		clawPidController.setP(ClawConstants.CLAW_MOTOR_KP);
		clawPidController.setI(ClawConstants.CLAW_MOTOR_KI);
		clawPidController.setD(ClawConstants.CLAW_MOTOR_KD);

	}

	public void periodic()
	{
		// sticky faults are set to true when a motor has a fault.
		if (clawMotorLeft.getStickyFault(FaultID.kHasReset)
				|| clawMotorRight.getStickyFault(FaultID.kHasReset))
		{
			
			clawMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			clawMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);

			clawMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			clawMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);

			clawMotorLeft.setIdleMode(CANSparkMax.IdleMode.kBrake);
			clawMotorRight.setIdleMode(CANSparkMax.IdleMode.kBrake);

			RobotContainer.getShuffleboardTab().addDouble("Desired Claw Velocity",
					() -> desiredSpeed);
			RobotContainer.getShuffleboardTab().addDouble("Actual Claw Motor Velocity",
					() -> clawMotorRight.getEncoder().getVelocity());
		}
	}

	public void setClawSpeed(double desiredSpeed)
	{
		this.desiredSpeed = desiredSpeed;
		clawPidController.setReference(desiredSpeed, ControlType.kVelocity);
	}

	public boolean getLimitSwitch()
	{
		if (limitSwitch.get())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}