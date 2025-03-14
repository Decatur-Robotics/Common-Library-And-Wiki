package frc.lib.examples.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.constants.Ports;

public class ExampleSubsystem
{
	private TalonFX leftMotor, rightMotor;

	public ExampleSubsystem()
	{
		// These should have the ports defined in the ports file on the actual robot
		leftMotor = new TalonFX(Ports.EXAMPLE_LEFT_MOTOR);
		rightMotor = new TalonFX(Ports.EXAMPLE_RIGHT_MOTOR);

		leftMotor.optimizeBusUtilization();
		rightMotor.optimizeBusUtilization();

		// leftMotor.getConfigurator().apply( Constants.motorConfig);
		// rightMotor.getConfigurator().apply(Constants.motorConfig);

		leftMotor.setControl(new Follower(0, true));

	}
}
