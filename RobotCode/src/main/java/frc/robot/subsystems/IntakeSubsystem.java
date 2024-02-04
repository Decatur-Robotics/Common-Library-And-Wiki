package frc.robot.subsystems;

import frc.robot.constants.Ports;

// import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class IntakeSubsystem extends SubsystemBase
{
    private final TeamSparkMAX ToggleIntakeMotorRight, ToggleIntakeMotorLeft, ToggleIntakeOnMotor;
    private final double MOTOR_SPEED = 0.5;
    private boolean isLowered = false;

    /*  Put as a constant, I don't think it is a value that has to be passed, since it's something
     * that would hardly change
     * The values are just provvisory and invented*/
    private final float ROTATION_LIMIT_DOWN = 0.5f, ROTATION_LIMIT_UP = 0.1f;

    public IntakeSubsystem()
    {
        ToggleIntakeMotorRight = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_RIGHT);
        ToggleIntakeMotorLeft = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_LEFT);
        ToggleIntakeOnMotor = new TeamSparkMAX("Intake Motor", Ports.INTAKE_ON_MOTOR);

        ToggleIntakeMotorLeft.follow(ToggleIntakeMotorRight);
        ToggleIntakeMotorLeft.setInverted(true);

        ToggleIntakeMotorLeft.setSoftLimit(SoftLimitDirection.kForward, ROTATION_LIMIT_DOWN);
        ToggleIntakeMotorRight.setSoftLimit(SoftLimitDirection.kForward, ROTATION_LIMIT_DOWN);

        ToggleIntakeMotorLeft.setSoftLimit(SoftLimitDirection.kReverse, ROTATION_LIMIT_UP);
        ToggleIntakeMotorRight.setSoftLimit(SoftLimitDirection.kReverse, ROTATION_LIMIT_UP);
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
