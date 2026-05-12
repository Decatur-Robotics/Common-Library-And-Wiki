package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * The container for the robot. Contains subsystems, OI devices, and commands.
 */
public class RobotContainer {

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Instantiate subsystems

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings() {

	}

	private void configureSecondaryBindings() {
	}

	@SuppressWarnings("unused")
	public static ShuffleboardTab getShuffleboardTab() {
		return instance.ShuffleboardTab;
	}

}
