package frc.lib.modules.twomotorelevator;

public class TwoMotorElevatorConstants {
	
	// Constants
	public static final double ELEVATOR_JOYSTICK_DEADBAND = 0.05;
	public static final double ELEVATOR_POWER_EXPONENT = 3;

	public static final double ELEVATOR_DEADBAND = 0.6;
    public static final double MAX_ELEVATOR_MOTOR_POWER = 0.3;
	public static final double MAX_ELEVATOR_POWER_CHANGE = 0.275;

	public static final double MINIMUM_ELEVATOR_POSITION = 0;
	public static final double MAXIMUM_ELEVATOR_POSITION = 30;

	public static final double FIRST_TARGET_ELEVATOR_POSITION = 10;
	public static final double SECOND_TARGET_ELEVATOR_POSITION = 20;
	public static final double THIRD_TARGET_ELEVATOR_POSITION = 30;

	// Ports
	public static final int RIGHT_ELEVATOR_MOTOR = 0;
	public static final int LEFT_ELEVATOR_MOTOR = 0;
	public static final int ELEVATOR_LIMIT_SWITCH = 0;
	public static final int ELEVATOR_POTENTIOMETER = 0;

}
