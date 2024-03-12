package frc.robot.constants;

public class ShooterMountConstants
{

	public static final double SHOOTER_MOUNT_GEAR_RATIO = (25 / 1) * (68 / 72);

	/** Lowest position the shooter mount target position can be set to in rotations */
	public static final double SHOOTER_MOUNT_MIN_ANGLE = 0.33; // 0.33;

	/** Lowest position of the shooter in radians */
	public static final double SHOOTER_MOUNT_MIN_ANGLE_IN_RADIANS = 0.419;

	/** Rotations of motor to full shooter arm rotation in radians */
	public static final double MOTOR_ROTATIONS_IN_SHOOTER_RADIANS = SHOOTER_MOUNT_GEAR_RATIO * 2 * Math.PI;

	/** Speaker height plus note height minus shooter mount height (meters) */
	public static final double SHOOTER_MOUNT_TO_SPEAKER = 1.98 + 0.05 - 0.29;

	/** Angle in rotations for shooting at amp */
	public static final double SHOOTER_MOUNT_AMP_ANGLE = 2;
	/** Angle in rotations for shooting at speaker */
	public static final double SHOOTER_MOUNT_SPEAKER_ANGLE_FIXED = 3.5; // 3.8;

	public static final double SHOOTER_MOUNT_KP = 0.7; // 0.1;
	public static final double SHOOTER_MOUNT_KI = 0;
	public static final double SHOOTER_MOUNT_KD = 0;
	public static final double SHOOTER_MOUNT_KS = 0;
	public static final double SHOOTER_MOUNT_KV = 0;
	public static final double SHOOTER_MOUNT_KA = 0;
	public static final double SHOOTER_MOUNT_KG = 0;

	/** Rotations per second */
	public static final double SHOOTER_MOUNT_CRUISE_VELOCITY = 10;
	/** Rotations per second per second */
	public static final double SHOOTER_MOUNT_ACCELERATION = 10;

	/** Tolerance for determining mount is within range to shoot in rotations */
	public static final double AIMING_DEADBAND = 100;

	/** Distance to speaker in meters */
	public static final double[] SpeakerDistanceTreeMapKeys =
	{
			1, 2, 3
	};
	/** Rotation for aiming in rotations */
	public static final double[] ShooterMountAngleTreeMapValues =
	{
			3, 3, 3
	};
	/** Note velocity in meters per second */
	public static final double[] NoteVelocityEstimateTreeMapValues =
	{
			1, 2, 3
	};

}
