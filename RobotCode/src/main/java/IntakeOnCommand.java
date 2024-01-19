import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class IntakeOnCommand extends Command
{
    private IntakeSubsystem intake;
    private JoystickButton IntakeOnButton;

    public IntakeOnCommand(IntakeSubsystem intake,
            JoystickButton IntakeOnButton)
    {
        this.intake = intake;
        this.spinIntakeButton = spinIntakeButton;
    }

    @Override
    public void execute()
    {
        if (spinIntakeButton.getAsBoolean())
            intake.toggleIntakeOn();
    }

    @Override
    public void end(boolean stop)
    {
        intake.stopIntake();
    }
}
