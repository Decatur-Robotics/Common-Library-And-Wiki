import frc.lib.core.TeamSubsystemBase;
import frc.robot.constants.Ports;
import frc.lib.core.motors.TeamSparkMAX;

public class IntakeSubsystem extends TeamSubsystemBase
{
    private final TeamSparkMAX intakeMotorRight, intakeMotorLeft, intakeMotorCenter;
    private final double MOTOR_SPEED;

    public IntakeSubsystem(int forwardChannel, int reverseChannel)
    {
        intakeMotorRight = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_RIGHT);
        intakeMotorLeft = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_LEFT);
        intakeMotorCenter = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR_CENTER);
        MOTOR_SPEED = 0.5;

    }

    public void raiseIntake(){
        
    }

    public void lowerIntake(){
        
    }

    public void toggleIntakeOn()
    {
        intakeMotorCenter.set(motorSpeed);
    }

    public void stopIntake()
    {
        intakeMotorCenter.set(0);
    }

}