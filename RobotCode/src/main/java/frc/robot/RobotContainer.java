package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer
{

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab = Shuffleboard.getTab("Tab 1");

	private final SendableChooser<Command> autoChooser = new SendableChooser<>();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

		addAutonomousOptions();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{

	}

	private void configureSecondaryBindings()
	{

	}

	// Add autonomous options to the SendableChooser
	public void addAutonomousOptions()
	{

	}

	public Command getAutonomousCommand()
	{
		return autoChooser.getSelected();
	}

	public static ShuffleboardTab getShuffleboard()
	{
		return instance.ShuffleboardTab;
	}

}
