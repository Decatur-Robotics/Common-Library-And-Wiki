import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class IntakeCommand extends Command
{
    private IntakeSubsystem intake;
    private JoystickButton toggleIntakeButton;
    private JoystickButton spinIntakeButton;

    public IntakeCommand(IntakeSubsystem intake, JoystickButton toggleIntakeButton,
            JoystickButton spinIntakeButton)
    {
        this.intake = intake;
        this.toggleIntakeButton = toggleIntakeButton;
        this.spinIntakeButton = spinIntakeButton;
    }

    @Override
    public void execute()
    {
        if (toggleIntakeButton.getAsBoolean())
            intake.toggleIntake();
        if (spinIntakeButton.getAsBoolean())
            intake.spinIntake();
    }

    @Override
    public void end(boolean stop)
    {
        intake.stopIntake();
    }
}
