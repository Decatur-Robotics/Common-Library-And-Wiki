package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.leds.commands.FlashLightsOffCommand;
import frc.robot.leds.subsystems.LedSubsystem;

public class RobotContainer
{

	private final static ShuffleboardTab shuffleboard = Shuffleboard.getTab("Tab 1");

	public static JoystickButton aButton;
	public static JoystickButton bButton;
	public static JoystickButton cButton;
	// the above button does not exist and charles pulled it out of his ass
	LedSubsystem ledSubsystem = new LedSubsystem(100);
	// we have no idea how many pixels are actually gonna be used and so we just put 100

	private final SendableChooser<Command> autoChooser = new SendableChooser<>();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		addAutonomousOptions();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	public void configurePrimaryBindings()
	{
		Joystick primaryController = new Joystick(0);
		JoystickButton primaryTrigger;
		aButton = new JoystickButton(primaryController, LogitechControllerButtons.a);
		bButton = new JoystickButton(primaryController, LogitechControllerButtons.b);
		// cButton = new JoystickButton(primaryController, LogitechControllerButtons.c);

		aButton.whileFalse(new FlashLightsOffCommand(ledSubsystem));
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
