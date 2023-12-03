package frc.lib.modules.twomotorelevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.twomotorelevator.TwoMotorElevatorSubsystem;

public class SetElevatorTargetManualOverrideCommand extends CommandBase {

    private TwoMotorElevatorSubsystem elevator;
    private boolean override;

    public SetElevatorTargetManualOverrideCommand(boolean override, TwoMotorElevatorSubsystem elevator) {
        this.override = override;
        this.elevator = elevator;

        addRequirements(elevator);
    }

    public void initialize() {
        elevator.setTargetOverridden(override);

        if(!override) {
            elevator.resetTarget();
            elevator.setPower(0);
        }
    }

    public boolean isFinished() {
        return true;
    }
    
}
