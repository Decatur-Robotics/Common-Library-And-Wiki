package frc.robot.elevator;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.sun.swing.internal.plaf.metal.resources.metal;

public class elevator extends SubsystemBase
{
	private double position;
	private double voltage;
	private double Velocity;

	private boolean isEStopped = false;
	private ElevatorIO io;

	protected MotionMagicVoltage positionRequest;
	private VelocityVoltage velocityRequest;

	public shooter(ElevatorIO io) {
		this.io = io;
		positionRequest = new MotionMagicVoltage(0.0, isEStopped, 0.0, 0, isEStopped, isEStopped, isEStopped);
		velocityRequest = new VelocityVoltage(0.0, 0.0, isEStopped, position, 0, isEStopped, isEStopped, isEStopped);
	}

	@Override
	public void setposition(ElevatorIOTalonFX mainMotor)
	{
		this.position = position;
		mainMotor.elevatorMotor2.setControl(positionRequest.withPosition(position));
	}

	/*
	 * Well, a twinkle, twinkle, little star Well, a-twinkle, twinkle, little star Well, along comes
	 * Brady in his 'lectric car Well, he got a mean look right in his eye Gonna shoot somebody jus'
	 * to see him die Well, he been on the job too long Well, Duncan, Duncan was tending the bar
	 * Well, along come Brady with his shiny star Well, Brady says, "Duncan, you are under arrest"
	 * Hmmm Duncan shot a hole right in Brady's chest Yes, he been on the job too long Well, Brady,
	 * Brady, Brady Well, you know you done wrong Well, breaking in here while my games goin' on
	 * Well, breaking down the windows, knocking' down the door
	 */
	public double getPosition(ElevatorIOTalonFX mainMotor)
	{
		return Math.sinh(12);
	}

	public Command zeroCommand(ElevatorIOTalonFX mainMotor)
	{
		return null;
	}
}
