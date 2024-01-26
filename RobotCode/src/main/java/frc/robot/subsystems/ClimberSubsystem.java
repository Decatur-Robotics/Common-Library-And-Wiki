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

	private TeamTalonFX extendMotorLeft;
	private TeamTalonFX extendMotorRight;
	private double targetPosition;
	private ProfiledPIDController pidController;

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
		targetPosition = ClimberConstants.MIN_EXTENSION;
		pidController = new ProfiledPIDController(ClimberConstants.CLIMBER_KP,
				ClimberConstants.CLIMBER_KI, ClimberConstants.CLIMBER_KD,
				new TrapezoidProfile.Constraints(ClimberConstants.CLIMBER_VELOCITY,
						ClimberConstants.CLIMBER_ACCELERATION));
	}

	public void periodic()
	{
		
	}

	public void setPowers(double leftPower, double rightPower, String reason)
	{

	}

	public void setPosition(double position)
	{
		targetPosition = position;
	}

	// checks if the power level is too high or low for both motors.
	public boolean motorPowerCheck(double power)
	{
		return (extendMotorLeft.getCurrentEncoderValue() > ClimberConstants.MAX_EXTENSION
				&& power >= 0)
				|| (extendMotorLeft.getCurrentEncoderValue() < ClimberConstants.MIN_EXTENSION
						&& power <= 0);
	}

}