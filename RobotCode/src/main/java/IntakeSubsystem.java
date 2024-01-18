import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.lib.core.TeamSubsystemBase;
import frc.robot.constants.Ports;
import frc.lib.core.motors.TeamSparkMAX;

public class IntakeSubsystem extends TeamSubsystemBase
{
    private DoubleSolenoid m_doubleSolenoid;
    private TeamSparkMAX intakeMotor;
    private double motorSpeed;

    public IntakeSubsystem(int forwardChannel, int reverseChannel)
    {
        m_doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 0);
        intakeMotor = new TeamSparkMAX("Intake Motor", Ports.INTAKE_MOTOR);
        motorSpeed = 0.5;
        m_doubleSolenoid.set(DoubleSolenoid.Value.kReverse);

    }

    public String toggleIsntake()
    {
        m_doubleSolenoid.toggle();

        if (m_doubleSolenoid.get() == DoubleSolenoid.Value.kForward)
        {
            return "forward";
        }
        if (m_doubleSolenoid.get() == DoubleSolenoid.Value.kReverse)
        {
            intakeMotor.set(0, "intake retracted");
            return "reverse";
        }
        return "off";
    }

    public void spinIntake()
    {
        intakeMotor.set(motorSpeed);
    }

    public void stopIntake()
    {
        intakeMotor.set(0);
        m_doubleSolenoid.set(DoubleSolenoid.Value.kOff);
    }

}
