package frc.robot.elevator;

public interface ElevatorIO
{

	class ElevatorIOInputs
	{
		public double position = 0.0;
		public double voltage = 0.0;
	}

	record ElevatorIOOutputs(double voltage)
	{}

	default void updateInputs(ElevatorIOInputs inputs)
	{}

	// when the
	default void setVoltage(double voltage)
	{}

	default void stop()
	{}

	default void runPosition(double position, double feedForward)
	{}

	default void setPID()
	{}
}