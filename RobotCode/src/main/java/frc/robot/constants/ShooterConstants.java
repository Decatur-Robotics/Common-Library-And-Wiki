package frc.robot.constants;

/**
 * 
 */
public class ShooterConstants
{

	public static final double SHOOTER_KP = 0;
	public static final double SHOOTER_KI = 0;
	public static final double SHOOTER_KD = 0;
	public static final double SHOOTER_KS = 0;
	public static final double SHOOTER_KV = 0.01;
	public static final double SHOOTER_KA = 0;

	public static final double SHOOTER_CRUISE_VELOCITY = 100;
	public static final double SHOOTER_ACCELERATION = 200;

	public static final double SHOOTER_VELOCITY_TOLERANCE = 20;

	// Velocity in RPM
	/**
	 * Maximum safe velocity in RPM
	 */
	public static final double SHOOTER_MAX_VELOCITY = 100;
	/**
	 * Velocity for shooting at speaker in RPM
	 */
	public static final double SHOOTER_SPEAKER_VELOCITY = 80;
	/**
	 * Velocity for shooting at amp in RPM
	 */
	public static final double SHOOTER_AMP_VELOCITY = 50;
	/**
	 * Velocity when not in use in RPM
	 */
	public static final double SHOOTER_REST_VELOCITY = 0;

	/** Number of milliseconds to wait when shooting in autonomous */
	public static final long SHOOT_TIME = 800;

}
