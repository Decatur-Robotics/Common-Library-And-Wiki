package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;
import frc.robot.constants.Ports;

public class ShooterMountSubsystem extends SubsystemBase {

	public TeamSparkMAX mainMotor;
	public TeamSparkMAX followMotor;


	public double currentRotation = 0.0;

	private double originalRotation = 0.0;
	private double goalRotation = 0.0;

   private static ShooterMountSubsystem instance;
	public static ShooterMountSubsystem getInstance() {
		if (instance == null)
			instance = new ShooterMountSubsystem();
		return instance;
	}

	private ShooterMountSubsystem() {
		mainMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_LEFT", Ports.SHOOTER_MOUNT_MOTOR_LEFT);
		followMotor = new TeamSparkMAX("SHOOTER_MOUNT_MOTOR_RIGHT", Ports.SHOOTER_MOUNT_MOTOR_RIGHT);
	
		followMotor.follow(mainMotor);
		followMotor.setInverted(true);

		mainMotor.enableVoltageCompensation(12);
		followMotor.enableVoltageCompensation(12);
		
		//The setPositionConversonFactor is set to 42 because the NEO motors have 42 ticks per rotation 
		mainMotor.getEncoder().setPositionConversionFactor(42);
		mainMotor.getEncoder().setPosition(0);

		followMotor.getEncoder().setPositionConversionFactor(42);
		followMotor.getEncoder().setPosition(0);

		mainMotor.set(0);
	}

	@Override
	public void periodic() {
		double position = Math.max(-1, Math.min(1, (goalRotation - currentRotation)/45));
		setMotors(position, "ShooterMountSubsystem#update | Position: " + position + " | Difference: " + (goalRotation - currentRotation));
	}

	public void setGoalRotation(double degrees) {
		originalRotation = currentRotation;
		goalRotation = degrees;
		mainMotor.getEncoder().setPosition(degreesToTicks(originalRotation));
	}


	private static final double CONVERSION_NUMBER = 360/42;
	private double degreesToTicks(double degrees) {
		return degrees / CONVERSION_NUMBER;
	}

	private double ticksToDegrees(double ticks) {
		return ticks * CONVERSION_NUMBER;
	}


	public void setMotors(double power, String reason) {
		mainMotor.set(Math.max(-1, Math.min(power, 1)), reason);
		System.out.println(reason);
	}




}
