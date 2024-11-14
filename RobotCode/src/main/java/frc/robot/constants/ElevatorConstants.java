package frc.robot.constants;

public class ElevatorConstants {
	
	public static final double[] ELEVATOR_POSITIONS = { // In TalonFX rotations
		
		25*2048,
		50*2048,
		70*2048 // Purely hypothetical values. Not real.

	};

	public static final double ACCEPTABLE_TARGET_DIFFERENCE = 2;

	public static final double ELEVATOR_MOTOR_KP = 0.0;
	public static final double ELEVATOR_MOTOR_KI = 0.0;
	public static final double ELEVATOR_MOTOR_KD = 0.0;

	public static final double ELEVATOR_MOTOR_KS = 0.0;
	public static final double ELEVATOR_MOTOR_KV = 0.0;
	public static final double ELEVATOR_MOTOR_KA = 0.0;

	public static final double ELEVATOR_MOTOR_KG = 0.0;

	public static final double ELEVATOR_ACCELERATION = 0.0;
	public static final double ELEVATOR_CRUISE_VELOCITY = 0.0;


	public static final double MIN_ANGLE = 0.0;
	public static final double MIN_ANGLE_RAD = MIN_ANGLE * Math.PI/180;

	public static final double MOTOR_ROTATIONS_IN_RADIANS = 0.0;




}
