package frc.robot.subsystems.LEDs;

import frc.robot.util.TeamColor;

public class LEDConstants {
      /** The length of the LED strip */
    public static final int LENGTH = 79; // 39 on one strip 40 on the other

    /** The color of a pixel when it is off */
    public static final TeamColor OFF_COLOR = new TeamColor(0, 0, 0);

    // Preset Colors
    public static final TeamColor RED = new TeamColor(255, 0, 0);
	public static final TeamColor GREEN = new TeamColor(0, 255, 0);
	public static final TeamColor BLUE = new TeamColor(0, 0, 200);
	public static final TeamColor YELLOW = new TeamColor(255, 127, 0);
	public static final TeamColor CYAN = new TeamColor(0, 255, 255);
	public static final TeamColor MAGENTA = new TeamColor(255, 0, 255);
	public static final TeamColor WHITE = new TeamColor(255, 255, 255);
	public static final TeamColor BLACK = new TeamColor(0, 0, 0);
}
