package frc.robot.subsystems;

import frc.robot.constants.Ports;

import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class IntakeSubsystem extends SubsystemBase
{
	private final TeamSparkMAX intakeMotorRight, intakeMotorLeft, intakeMotorCenter;
	private final double MOTOR_SPEED = 0.5;
	private boolean isLowered = false;

	// Put as a constant, I don't think it is a value that has to be passed, since it's something
	// that would hardly change
	// The values are just provvisory and invented
	private final float rotationLimitDown = 0.5f;
	private final float rotationLimitUp = 0.1f;

	// forwardChannel and reverseChannel are not used in the constructor, so maybe specify how they
	// would be needed
	// public IntakeSubsystem(int forwardChannel, int reverseChannel)
	public IntakeSubsystem()
	{
		intakeMotorRight = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_RIGHT);
		intakeMotorLeft = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_LEFT);
		intakeMotorCenter = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_CENTER);
		/*
		 * this.rotationLimitDown = rotationLimitDown; this.rotationLimitUp = rotationLimitUp;
		 */

		intakeMotorLeft.follow(intakeMotorRight);
		intakeMotorLeft.setInverted(true);
		intakeMotorRight.setInverted(false);

		intakeMotorLeft.setSoftLimit(SoftLimitDirection.kForward, rotationLimitDown);
		intakeMotorRight.setSoftLimit(SoftLimitDirection.kForward, rotationLimitDown);

		intakeMotorLeft.setSoftLimit(SoftLimitDirection.kReverse, rotationLimitUp);
		intakeMotorRight.setSoftLimit(SoftLimitDirection.kReverse, rotationLimitUp);
	}

	public void raiseOrLowerIntake()
	{
		if (isLowered)
		{
			intakeMotorRight.set(MOTOR_SPEED * -1);
			isLowered = false;
		}
		else
		{
			intakeMotorRight.set(MOTOR_SPEED);
			isLowered = true;
		}
	}

	public void toggleIntakeOn()
	{
		intakeMotorCenter.set(MOTOR_SPEED);
	}

	public void stopIntake()
	{
		intakeMotorCenter.set(0);
		intakeMotorLeft.set(0);
		intakeMotorRight.set(0);
	}

}
