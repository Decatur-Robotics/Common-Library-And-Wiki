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

	}

	// Add autonomous options to the SendableChooser
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

	public Command getAutonomousCommand()
	{
		return autoChooser.getSelected();
	}

	public static ShuffleboardTab getShuffleboard()
	{
		return instance.ShuffleboardTab;
	}

}
