package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase
{
	private double shooterspeed;
	private TeamSparkMAX shooterMotorLeft;
	private TeamSparkMAX shooterMotorRight;
	private String shooterReason;

	public ShooterSubsystem()
	{
		shooterMotorLeft = new TeamSparkMAX("left Motor Shooter", 0);
		shooterMotorRight = new TeamSparkMAX("right Motor Shooter", 0);
		shooterMotorRight.follow(shooterMotorLeft, true);
		shooterMotorLeft.enableVoltageCompensation(12);
		shooterMotorRight.enableVoltageCompensation(12);
		shooterspeed = 0;
		shooterReason = "";
	}

	public void setMotorpower(float power, String reason)
	{
		shooterspeed = power;
		shooterReason = reason;
	}

	public void execute()
	{
		shooterMotorLeft.set(shooterspeed, shooterReason);
	}
}