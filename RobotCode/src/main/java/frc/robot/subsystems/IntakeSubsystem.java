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

        ToggleIntakeMotorLeft.follow(ToggleIntakeMotorRight);
        ToggleIntakeMotorLeft.setInverted(true);
        ToggleIntakeMotorRight.setInverted(false);

        // limits motor movement in a specified direction based on a specified amount of rotations

        ToggleIntakeMotorLeft.setSoftLimit(SoftLimitDirection.kForward, rotationLimitDown);
        ToggleIntakeMotorRight.setSoftLimit(SoftLimitDirection.kForward, rotationLimitDown);
        ToggleIntakeMotorLeft.setSoftLimit(SoftLimitDirection.kReverse, rotationLimitUp);
        ToggleIntakeMotorRight.setSoftLimit(SoftLimitDirection.kReverse, rotationLimitUp);

        ToggleIntakeMotorLeft.setIdleMode(IdleMode.kBrake);
        ToggleIntakeMotorRight.setIdleMode(IdleMode.kBrake);
        ToggleIntakeOnMotor.setIdleMode(IdleMode.kBrake);

    }

    public boolean isStopped()
    {
        if (ToggleIntakeMotorLeft.get() != 0 && ToggleIntakeMotorRight.get() != 0)
            return false;
        return true;
    }

    public void raiseOrLowerIntake()
    {
        if (isLowered)
        {
            ToggleIntakeMotorRight.set(MOTOR_SPEED * -1);
            isLowered = false;
        }
        else
        {
            ToggleIntakeMotorRight.set(MOTOR_SPEED);
            isLowered = true;
        }
    }

    public void toggleIntakeOn()
    {
        ToggleIntakeOnMotor.set(MOTOR_SPEED);
    }

    public void stopIntake()
    {
        ToggleIntakeOnMotor.set(0);
        ToggleIntakeMotorLeft.set(0);
        ToggleIntakeMotorRight.set(0);
    }

}
