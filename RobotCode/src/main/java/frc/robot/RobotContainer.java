package frc.robot;


import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;

// import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
// import edu.wpi.first.wpilibj.shuffleboard.SuppliedValueWidget;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.Commands.ShooterCommand;

import frc.robot.Subsystems.ShooterSubsystem;

public class RobotContainer
{
	public static Joystick primaryController;
	public static Joystick secondaryController;
	public static ShooterSubsystem shooting;
	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab = Shuffleboard.getTab("Tab 1");

	private SendableChooser<Command> autoChooser = new SendableChooser<>();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;
		shooting = new ShooterSubsystem();

		addAutonomousOptions();
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
		secondaryController = new Joystick(1);
		JoystickButton rightTrigger = new JoystickButton(secondaryController,
				LogitechControllerButtons.triggerRight);

		rightTrigger.whileTrue(new ShooterCommand(shooting));
	}

	// Add autonomous options to the SendableChooser
	private void addAutonomousOptions()
	{
		
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




	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	