package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ClimberSubsystem;

public class ClimberToPositionCommand extends Command
{

    public ClimberToPositionCommand(ClimberSubsystem climber, double targetPosition)
    {
        addRequirements(climber);

		climber.setPosition(targetPosition);
    }
}
