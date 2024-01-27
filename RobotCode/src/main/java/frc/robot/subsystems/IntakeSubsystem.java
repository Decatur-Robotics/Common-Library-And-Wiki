package frc.robot.subsystems;

import frc.robot.constants.Ports;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class IntakeSubsystem extends SubsystemBase
{
    private final TeamSparkMAX ToggleIntakeMotorRight, ToggleIntakeMotorLeft, ToggleIntakeOnMotor;
    private final double MOTOR_SPEED = 0.5;
    private boolean isLowered = false;
    private float rotationLimitDown, rotationLimitUp;

    public IntakeSubsystem(float rotationLimitDown, float rotationLimitUp)
    {
        ToggleIntakeMotorRight = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_RIGHT);
        ToggleIntakeMotorLeft = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_LEFT);
        ToggleIntakeOnMotor = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_CENTER);

        this.rotationLimitDown = rotationLimitDown;
        this.rotationLimitUp = rotationLimitUp;

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
