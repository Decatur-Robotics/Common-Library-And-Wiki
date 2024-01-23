package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeOnCommand extends Command
{
    private IntakeSubsystem intake;

    public IntakeOnCommand(IntakeSubsystem intake)
    {
        this.intake = intake;
    }

    @Override
    public void execute()
    {
        intake.toggleIntakeOn();
    }

    @Override
    public void end(boolean stop)
    {
        intake.stopIntake();
    }
}
