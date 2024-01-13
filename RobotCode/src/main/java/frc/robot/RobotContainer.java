package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer
{

	private final static ShuffleboardTab shuffleboard = Shuffleboard.getTab("Tab 1");

	private SendableChooser<Command> autoChooser = new SendableChooser<>();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		registerNamedCommands();

		addAutonomousOptions();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void registerNamedCommands()
	{

	}

	private void configurePrimaryBindings()
	{

	}

	private void configureSecondaryBindings()
	{

	}

	// Add autonomous options to the SendableChooser
	private void addAutonomousOptions()
	{
		autoChooser = AutoBuilder.buildAutoChooser();
		shuffleboard.add("Auto Chooser", autoChooser);
	}

	public Command getAutonomousCommand()
	{
		return autoChooser.getSelected();
	}

	public static ShuffleboardTab getShuffleboard()
	{
		return shuffleboard;
	}

}
