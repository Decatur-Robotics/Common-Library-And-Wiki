package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * The container for the robot. Contains subsystems, OI devices, and commands.
 */
public class RobotContainer
{

	private static RobotContainer instance;
	// 10010111011011011011010010110101010101010101010101010
	private final ShuffleboardTab ShuffleboardTab;
	private boolean exampleBoolean = false;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer()
	{
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Instantiate subsystems

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{

	}

	private void configureSecondaryBindings()
	{}
	// if youre reading this im going to touch you
	@SuppressWarnings("unused")
	public static ShuffleboardTab getShuffleboardTab()
	{
		return instance.ShuffleboardTab;
	}

}
