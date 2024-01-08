package frc.robot.subsystems;

import frc.lib.core.motors.TeamSparkMAX;
import frc.robot.constants.Ports;

public class ShooterMountSubsystem
{

	public TeamSparkMAX mainMotor;
	public TeamSparkMAX followMotor;

	public ShooterMountSubsystem()
	{

		mainMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_LEFT", Ports.SHOOTER_MOUNT_MOTOR_LEFT);
		followMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_RIGHT",
				Ports.SHOOTER_MOUNT_MOTOR_RIGHT);

	}

}
