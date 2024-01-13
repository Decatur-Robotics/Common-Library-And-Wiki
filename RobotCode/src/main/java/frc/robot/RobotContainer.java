package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
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

	private final static ShuffleboardTab shuffleboard = Shuffleboard.getTab("Tab 1");

	private final SendableChooser<Command> autoChooser = new SendableChooser<>();

	private Joystick secondaryController;
	private JoystickButton secondaryTrigger;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		addAutonomousOptions();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{
		// Testing for Shooter Mount code. Remove before merging into dev.
		secondaryController = new Joystick(0);
		secondaryTrigger = new JoystickButton(secondaryController, 2);
		
		secondaryTrigger.whileTrue(new RotateShooterMountCommand(ShooterMountSubsystem.getInstance()));
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
		return shuffleboard;
	}

}
