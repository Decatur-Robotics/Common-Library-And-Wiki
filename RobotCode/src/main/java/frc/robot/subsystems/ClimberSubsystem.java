package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Ports;

public class ClimberSubsystem extends SubsystemBase
{

	private TeamTalonFX extendMotorLeft, extendMotorRight;
	private double targetPositionLeft;
	private double targetPositionRight;
	private ProfiledPIDController pidController;
	private boolean override;
	private double leftPower, rightPower;

	public ClimberSubsystem()
	{
		// sets extension of left and right motors to given extension length
		extendMotorLeft = new TeamTalonFX("Subsystems.Climber.ExtendRight",
				Ports.CLIMBER_EXTEND_RIGHT_MOTOR);
		extendMotorRight = new TeamTalonFX("Subsystems.Climber.ExtendLeft",
				Ports.CLIMBER_EXTEND_LEFT_MOTOR);
		extendMotorLeft.setNeutralMode(NeutralMode.Brake);
		extendMotorRight.setNeutralMode(NeutralMode.Brake);
		extendMotorLeft.setInverted(true);
		targetPositionLeft = ClimberConstants.MIN_EXTENSION;
		pidController = new ProfiledPIDController(ClimberConstants.CLIMBER_KP,
				ClimberConstants.CLIMBER_KI, ClimberConstants.CLIMBER_KD,
				new TrapezoidProfile.Constraints(ClimberConstants.CLIMBER_VELOCITY,
						ClimberConstants.CLIMBER_ACCELERATION));
		override = false;
	}

	public void periodic()
	{
		if (!override)
		{
			extendMotorLeft.set(pidController.calculate(extendMotorLeft.getCurrentEncoderValue(), targetPositionLeft));
			extendMotorRight.set(pidController.calculate(extendMotorRight.getCurrentEncoderValue(), targetPositionRight));
		} 
		else 
		{
			extendMotorLeft.set(leftPower * ClimberConstants.MAX_OVERRIDE_SPEED);
			extendMotorRight.set(rightPower * ClimberConstants.MAX_OVERRIDE_SPEED);
			targetPositionLeft = extendMotorLeft.getCurrentEncoderValue();
			targetPositionRight = extendMotorRight.getCurrentEncoderValue();
		}
	}

	public void setPowers(double leftPower, double rightPower, String reason)
	{
		this.leftPower = leftPower;
		this.rightPower = rightPower;


	}

	public void setPosition(double position)
	{
		targetPositionLeft = position;
		targetPositionRight = position;
	}

	// checks if the power level is too high or low for both motors.
	public boolean motorPowerCheck(double power)
	{
		return (extendMotorLeft.getCurrentEncoderValue() > ClimberConstants.MAX_EXTENSION
				&& power >= 0)
				|| (extendMotorLeft.getCurrentEncoderValue() < ClimberConstants.MIN_EXTENSION
						&& power <= 0);
	}

	public void setOverride(boolean override)
	{
		this.override = override;
	}

}