package frc.robot.constants;

public class ShooterMountConstants
{

	public static final double TICKS_IN_ONE_DEGREE = 4096 / 360; // Update to reflect gear ratios

	public static final double SHOOTER_MOUNT_OFFSET_DEGREES = 10; // Update to reflect lowest
																	// shooter angle

	public static final double SHOOTER_MOUNT_TO_SPEAKER = 1.98 + 0.05 - 0.29; // Speaker height plus
																				// note height minus
																				// shooter mount
																				// height (meters)

	public static final double SHOOTER_MOUNT_KP = 0.1;
	public static final double SHOOTER_MOUNT_KI = 0;
	public static final double SHOOTER_MOUNT_KD = 0.1;
	public static final double SHOOTER_MOUNT_KF = 0.1;
	public static final double SHOOTER_MOUNT_CRUISE_VELOCITY = 2000;
	public static final double SHOOTER_MOUNT_ACCELERATION = 1000;

}
