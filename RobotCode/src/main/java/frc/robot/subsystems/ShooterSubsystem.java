package frc.robot.subsystems;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import frc.robot.RobotContainer;
import frc.robot.constants.Constants;
import frc.robot.constants.Ports;
import frc.robot.constants.ShooterConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkBase;

public class ShooterSubsystem extends SubsystemBase
{

	private double desiredShooterVelocity;

	private SparkPIDController shooterPid;
	private TeamSparkBase shooterMotorMain, shooterMotorSub;

	public ShooterSubsystem()
	{
		// Sets default shooter motor power
		desiredShooterVelocity = ShooterConstants.SHOOTER_REST_VELOCITY;

		// Initializes motor object
		shooterMotorMain = new TeamSparkBase("Left Shooter Motor Main", Ports.SHOOTER_MOTOR_RIGHT);
		shooterMotorSub = new TeamSparkBase("Right Shooter Motor Main", Ports.SHOOTER_MOTOR_LEFT);

		shooterMotorSub.follow(shooterMotorMain, true);
		shooterMotorMain.setIdleMode(IdleMode.kBrake);
		shooterMotorSub.setIdleMode(IdleMode.kBrake);
		shooterMotorMain.setSmartCurrentLimit(Constants.MAX_CURRENT);
		shooterMotorSub.setSmartCurrentLimit(Constants.MAX_CURRENT);

		shooterPid = shooterMotorMain.getPidController();

		shooterPid.setP(ShooterConstants.SHOOTER_KP);
		shooterPid.setI(ShooterConstants.SHOOTER_KI);
		shooterPid.setD(ShooterConstants.SHOOTER_KD);
		shooterPid.setFF(ShooterConstants.SHOOTER_KF);

		RobotContainer.getShuffleboardTab().addDouble("Actual Shooter Velocity",
				() -> shooterMotorMain.getVelocity());
		RobotContainer.getShuffleboardTab().addDouble("Desired Shooter Velocity",
				() -> desiredShooterVelocity);
	}

	public double getShooterMotorVelocityError()
	{
		return shooterMotorMain.getVelocityError();
	}

	/**
	 * This is clamping the shooter motor power
	 */
	public void setShooterMotorVelocity(double desiredShooterVelocity, String reason)
	{
		this.desiredShooterVelocity = Math.max(
				Math.min(ShooterConstants.SHOOTER_MAX_VELOCITY, desiredShooterVelocity),
				-ShooterConstants.SHOOTER_MAX_VELOCITY);
	}

	public boolean isUpToSpeed()
	{
		return Math.abs(shooterMotorMain.getEncoder().getVelocity()
				- desiredShooterVelocity) < ShooterConstants.SHOOTER_VELOCITY_TOLERANCE;
	}

	/**
	 * Continuously updates shooter speed based on the commands above.
	 */
	@Override
	public void periodic()
	{
		shooterPid.setReference(desiredShooterVelocity, ControlType.kVelocity);
	}

}
