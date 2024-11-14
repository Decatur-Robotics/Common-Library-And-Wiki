package frc.robot.constants;

public enum ElevatorPosition {
	UPPER,
	MIDDLE,
	LOWER;

	public double getRotation() {
		return switch(this) { // Hypothetical values
			case UPPER ->  25.0;
			case MIDDLE -> 50.0;
			case LOWER ->  75.0;
		};
	}
}
