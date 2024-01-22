import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class IntakeLiftCommand extends Command {

	private IntakeSubsystem intake;
    private JoystickButton IntakeOnButton;
	
	public IntakeLiftCommand (IntakeSubsystem intake , JoystickButton IntakeOnButton) {

		this.intake = intake;
		this.IntakeOnButton = IntakeOnButton;
	}

	public void execute() {
		
	}
}
