package frc.robot.subsystems;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;

import frc.robot.constants.Ports;
import frc.robot.constants.ShooterConstants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase
{
	// Creates objects
	private double desiredShooterVelocity;

	private SparkPIDController shooterPid;
	private TeamSparkMAX ShooterMotorMain, ShooterMotorSub;

	public ShooterSubsystem()
	{
		// Sets default shooter motor power to 0.25 and feeder to 0
		desiredShooterVelocity = ShooterConstants.SHOOTER_REST_VELOCITY;

		// Initializes motor object
		ShooterMotorMain = new TeamSparkMAX("Left Shooter Motor Main", Ports.SHOOTER_MOTOR_MAIN);
		ShooterMotorSub = new TeamSparkMAX("Right Shooter Motor Main", Ports.SHOOTER_MOTOR_SUB);
	}

	/**
	 * This is clamping the shooter motor power to be within the range of -1 to 1
	 */
	public void setShooterMotorPower(double power, String reason)
	{
		desiredShooterVelocity = Math.max(Math.min(1, power), -1);
	}

	/**
	 * Continuously updates shooter speed based on the commands above.
	 */
	@Override
	public void periodic()
	{
		
	}
}
