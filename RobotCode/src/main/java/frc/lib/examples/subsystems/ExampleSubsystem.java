package frc.lib.examples.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.constants.Ports;

public class ExampleSubsystem
{
	private MotionMagicVoltage positionRequest;
	private TalonFX leftMotor, rightMotor;

	public ExampleSubsystem()
	{
		leftMotor = new TalonFX(Ports.EXAMPLE_LEFT_MOTOR);
		rightMotor = new TalonFX(Ports.EXAMPLE_RIGHT_MOTOR);

		leftMotor.optimizeBusUtilization();
		rightMotor.optimizeBusUtilization();

		// leftMotor.getConfigurator().apply( Constants.motorConfig);
		// rightMotor.getConfigurator().apply(Constants.motorConfig);

		leftMotor.setControl(new Follower(rightMotor.getDeviceID(), true));

		positionRequest = new MotionMagicVoltage(0).withEnableFOC(false);
		rightMotor.setControl(positionRequest);

		configureShuffleboard();

	}

	public void periodic()
	{
		if (rightMotor.hasResetOccurred() || leftMotor.hasResetOccurred())
		{
			rightMotor.optimizeBusUtilization();
			leftMotor.optimizeBusUtilization();
			rightMotor.getPosition().setUpdateFrequency(20);
		}
	}

	private void configureShuffleboard()
	{
		ShuffleboardTab tab = Shuffleboard.getTab("Example Subsystem");
		tab.addDouble("Example Position", () -> getPosition());
	}

	public double getPosition()
	{
		return rightMotor.getPosition().getValueAsDouble();
	}

	public void setPosition(double position)
	{
		rightMotor.setControl(positionRequest.withPosition(position));
	}

	public Command setPositionCommand(double position)
	{
		return Commands.runOnce(() -> setPosition(position));
	}

}
