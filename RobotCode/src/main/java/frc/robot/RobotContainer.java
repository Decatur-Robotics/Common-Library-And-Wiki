package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveConstants;

public class RobotContainer
{

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab;

	private SendableChooser<Command> autoChooser;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Autonomous set up
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
