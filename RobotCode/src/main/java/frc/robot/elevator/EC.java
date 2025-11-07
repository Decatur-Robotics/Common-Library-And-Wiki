package frc.robot.elevator;

public class EC
{
	public static double STOWED_POSITION = 0;
	static final double kV = 0.0;
	public static final double kA = 0.0;
	public static final double kG = 0.0;
	public static final double kP = 0.0;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	private static final double MAX_VOLTAGE = 12.0;
	private static final double MIN_VOLTAGE = -12.0;
	private static final double MAX_POSITION = 100.0;
	private static final double MIN_POSITION = 0.0;
	private static final double MAX_VELOCITY = 50.0;
	private static final double MIN_VELOCITY = -50.0;
	private static final double MAX_ACCELERATION = 100.0;
	private static final double MIN_ACCELERATION = -100.0;

	protected static double clamp(double value, double min, double max)
	{
		return Math.max(min, Math.min(max, value));
	}

	private EC()
	{}

}
