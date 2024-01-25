package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.lib.modules.swervedrive.SwerveConstants;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.ShooterInstantCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/** The container for the robot. Contains subsystems, OI devices, and commands. */
public class RobotContainer
{

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab;

	private final SendableChooser<Command> AutoChooser;
	private final ClimberSubsystem ClimberSubsystem;
	private final ShooterSubsystem ShooterSubsystem;

	private final ShooterMountSubsystem ShooterMountSubsystem;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		AutoChooser = AutoBuilder.buildAutoChooser();

		// Instantiate subsystems
		ClimberSubsystem = new ClimberSubsystem();
		ShooterSubsystem = new ShooterSubsystem();
		ShooterMountSubsystem = new ShooterMountSubsystem();

		// Autonomous set up
		addAutonomousOptions();
		registerNamedCommands();
		addAutonomousOptions();

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{
		final Joystick primaryController = new Joystick(0);
	}

	private void configureSecondaryBindings()
	{
		final Joystick secondaryController = new Joystick(1);
		final JoystickButton rightTrigger = new JoystickButton(secondaryController,
				LogitechControllerButtons.triggerRight);

		rightTrigger.whileTrue(new ShooterCommand(ShooterSubsystem));
	}

	/** Registers any commands we want to use in PathPlanner */
	private void registerNamedCommands()
	{
		// Ex: NamedCommands.registerCommand("commandName", command);
		NamedCommands.registerCommand("ShooterInstant",
				new ShooterInstantCommand(ShooterSubsystem));
	}

	/**
	 * Adds autonomous options to the SendableChooser
	 * 
	 * @see #registerNamedCommands()
	 */
	private void addAutonomousOptions()
	{
		ShuffleboardTab.add("Auto Chooser", AutoChooser);
	}

	/**
	 * @return The command that will be run as the autonomous. Will return whatever is selected in
	 *         the autochooser on Shuffleboard
	 * @see #addAutonomousOptions()
	 */
	public Command getAutonomousCommand()
	{
		return AutoChooser.getSelected();
	}

	/**
	 * Returns a command to follow a path from PathPlanner GUI whilst avoiding obstacles
	 * 
	 * @param pathName The filename of the path to follow w/o file extension. Must be in the paths
	 *                 folder. Ex: Example Human Player Pickup
	 * @return A command that will drive the robot along the path
	 */
	private Command followPath(final String pathName)
	{
		final PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);
		return AutoBuilder.pathfindThenFollowPath(path,
				SwerveConstants.AutoConstants.PathConstraints);
	}

	public static ShuffleboardTab getShuffleboardTab()
	{
		return instance.ShuffleboardTab;
	}

}
