package frc.lib.core;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

/*
 * Extend this class instead of SubsystemBase. This class receives method calls when the robot
 * changes state
 */
public class TeamSubsystemBase extends SubsystemBase
{

	public TeamSubsystemBase()
	{
		super();
		Robot.addSubsystem(this);
	}

	public void disabledInit()
	{}

	public void teleopInit()
	{}

	public void autonomousInit()
	{}

	public void testInit()
	{}
}