package frc.lib.core;

import frc.robot.Robot;

public interface IModeChangeListener
{

	public default void defaultMethod()
	{
		Robot.addSubsystem(this);
	}

	public void disabledInit();

	public void teleopInit();

	public void autonomousInit();

	public void testInit();

}
