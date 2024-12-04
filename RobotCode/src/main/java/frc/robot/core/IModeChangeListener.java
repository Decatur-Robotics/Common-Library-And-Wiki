package frc.robot.core;

import frc.robot.Robot;

public interface IModeChangeListener
{

	public default void defaultMethod()
	{
		Robot.addSubsystem(this);
	}

	public default void disabledInit(){
		
	}

	public default void teleopInit(){
	
	}

	public default void autonomousInit(){
		
	}

	public default void testInit(){
		
	}

}
