package frc.robot.constants;

public class ShooterMountConstants
{

	public static final double SHOOTER_MOUNT_GEAR_RATIO = (25 / 1) * (68 / 72);

	/** Highest position the shooter mount target position can be set to in rotations */
	public static final double SHOOTER_MOUNT_MAX_ANGLE_OFFSET = 4.1;

	/** Lowest position of the shooter in radians */
	public static final double SHOOTER_MOUNT_MIN_ANGLE_IN_RADIANS = 0.419;

	/** Rotations of motor to full shooter arm rotation in radians */
	public static final double MOTOR_ROTATIONS_IN_SHOOTER_RADIANS = SHOOTER_MOUNT_GEAR_RATIO * 2
			* Math.PI;

	/** Angle in rotations for shooting at amp */
	public static final double SHOOTER_MOUNT_AMP_ANGLE_OFFSET = 1.3;
	/** Angle in rotations for shooting at speaker */
	public static final double SHOOTER_MOUNT_SPEAKER_ANGLE_FIXED_OFFSET = 4.1;
	public static final double SHOOTER_MOUNT_NOTE_CENTER_ANGLE_FIXED_OFFSET = 2.2;

	public static final double SHOOTER_MOUNT_KP = 1;
	public static final double SHOOTER_MOUNT_KI = 0;
	public static final double SHOOTER_MOUNT_KD = 0;
	public static final double SHOOTER_MOUNT_KS = 0;
	public static final double SHOOTER_MOUNT_KV = 0;
	public static final double SHOOTER_MOUNT_KA = 0;
	public static final double SHOOTER_MOUNT_KG = 0;

	/** Rotations per second */
	public static final double SHOOTER_MOUNT_CRUISE_VELOCITY = 40;
	/** Rotations per second per second */
	public static final double SHOOTER_MOUNT_ACCELERATION = 60;

	/** Tolerance for determining mount is within range to shoot in rotations */
	public static final double AIMING_DEADBAND = 0.1;

	/** Distance to speaker in meters */
	public static final double[] SpeakerDistanceTreeMapKeys =
	{
			1, 2, 3
	};
	/** Rotation for aiming in rotations */
	public static final double[] ShooterMountAngleTreeMapValues =
	{
			0, 1, 2
	};
	/** Note velocity in meters per second */
	public static final double[] NoteVelocityEstimateTreeMapValues =
	{
			1, 2, 3
	};

}
