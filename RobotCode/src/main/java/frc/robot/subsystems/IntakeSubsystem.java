package frc.robot.subsystems;

import frc.robot.constants.Ports;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class IntakeSubsystem extends SubsystemBase
{
    private final TeamSparkMAX intakeMotorRight, intakeMotorLeft, intakeMotorCenter;
    private final double MOTOR_SPEED = 0.5;
    private boolean isLowered = false;

    public IntakeSubsystem(int forwardChannel, int reverseChannel)
    {
        intakeMotorRight = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_RIGHT);
        intakeMotorLeft = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_LEFT);
        intakeMotorCenter = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_CENTER);

        intakeMotorLeft.follow(intakeMotorRight);
        intakeMotorLeft.setInverted(true);

        // intakeMotorLeft.enableSoftLimit (kForward);
        // intakeMotorRight.enableSoftLimit (kReverse);

    }

    public void raiseIntake()
    {
        // if (isLowered)

    }

    public void lowerIntake()
    {

    }

    public void toggleIntakeOn()
    {
        intakeMotorCenter.set(MOTOR_SPEED);
    }

    public void stopIntake()
    {
        intakeMotorCenter.set(0);
    }

}
