package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.RotateShooterMountCommand;
import frc.robot.subsystems.ShooterMountSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer
{

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab = Shuffleboard.getTab("Tab 1");

	private SendableChooser<Command> autoChooser = new SendableChooser<>();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

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
		Joystick secondaryController;
		JoystickButton secondaryTrigger;
	}

	// Add autonomous options to the SendableChooser
	private void addAutonomousOptions()
	{
		autoChooser = AutoBuilder.buildAutoChooser();
		ShuffleboardTab.add("Auto Chooser", autoChooser);
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
