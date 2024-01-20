package frc.robot;


import edu.wpi.first.wpilibj.Joystick;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

import frc.lib.modules.swervedrive.SwerveConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.Commands.ShooterCommand;

import frc.robot.Subsystems.ShooterSubsystem;

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
	private Joystick primaryController, secondaryController;
	private final ShooterSubsystem ShooterSubsystem;
	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab;

	private SendableChooser<Command> autoChooser;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;
		ShooterSubsystem = new ShooterSubsystem();

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Autonomous set up
		addAutonomousOptions();
		registerNamedCommands();
		addAutonomousOptions();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	/** Registers any commands we want to use in PathPlanner */
	private void registerNamedCommands()
	{
		// Ex: NamedCommands.registerCommand("commandName", command);
	}

	private void configurePrimaryBindings()
	{

	}

	private void configureSecondaryBindings()
	{
		secondaryController = new Joystick(1);
		JoystickButton rightTrigger = new JoystickButton(secondaryController,
				LogitechControllerButtons.triggerRight);

		rightTrigger.whileTrue(new ShooterCommand(ShooterSubsystem));
	}

	/** Adds autonomous options to the SendableChooser */
	private void addAutonomousOptions()
	{
		autoChooser = AutoBuilder.buildAutoChooser();
		ShuffleboardTab.add("Auto Chooser", autoChooser);
		
	}

	/**
	 * Returns a command to follow a path from PathPlanner GUI whilst avoiding obstacles
	 * 
	 * @param pathName The filename of the path to follow w/o file extension. Must be in the paths
	 *                 folder. Ex: Example Human Player Pickup
	 * @return A command that will drive the robot along the path
	 */
	private Command followPath(String pathName)
	{
		PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);
		return AutoBuilder.pathfindThenFollowPath(path,
				SwerveConstants.AutoConstants.PathConstraints);
	}

	/**
	 * @return The command that will be run as the autonomous. Will return whatever is selected in
	 *         the autochooser on Shuffleboard
	 */
	public Command getAutonomousCommand()
	{
		return autoChooser.getSelected();
	}

	public static ShuffleboardTab getShuffleboardTab()
	{
		return instance.ShuffleboardTab;
	}

}




	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	