package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;
import frc.robot.constants.Ports;
import java.util.Scanner;

public class ShooterMountSubsystem extends SubsystemBase
{

	private TeamSparkMAX mainMotor, followMotor;

	private double currentRotation, originalRotation, goalRotation;

	private static final double DEGREES_IN_ONE_TICK = 360 / 42;

	// private static ShooterMountSubsystem instance;

	// public static ShooterMountSubsystem getInstance()
	// {
	// if (instance == null)
	// instance = new ShooterMountSubsystem();
	//
	// return instance;
	// return new ShooterMountSubsystem();
	// }

	public ShooterMountSubsystem()
	{
		mainMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_LEFT", Ports.SHOOTER_MOUNT_MOTOR_LEFT);
		followMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_RIGHT",
				Ports.SHOOTER_MOUNT_MOTOR_RIGHT);
		followMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_RIGHT",
				Ports.SHOOTER_MOUNT_MOTOR_RIGHT);

		followMotor.follow(mainMotor);
		followMotor.setInverted(true);

		mainMotor.getEncoder().setPositionConversionFactor(42);
		mainMotor.getEncoder().setPosition(0);

		mainMotor.set(0);

		currentRotation = 0.0;
		originalRotation = 0.0;
		goalRotation = 0.0;
	}

	public void update()
	{
		double position = goalRotation - currentRotation;
		setMotors(Math.sin((position * Math.PI) / 2),
				"ShooterMountSubsystem#update | Position: " + position);
	}

	public void setGoalRotation(double degrees)
	{
		originalRotation = currentRotation;
		goalRotation = degrees;
		mainMotor.getEncoder().setPosition(degreesToTicks(originalRotation));
	}

	private double degreesToTicks(double degrees)
	{
		return degrees / DEGREES_IN_ONE_TICK;
	}

	private double ticksToDegrees(double ticks)
	{
		return ticks * DEGREES_IN_ONE_TICK;
	}

	public void setMotors(double power, String reason)
	{
		mainMotor.set(Math.max(-1, Math.min(power, 1)), reason);
	}

}
