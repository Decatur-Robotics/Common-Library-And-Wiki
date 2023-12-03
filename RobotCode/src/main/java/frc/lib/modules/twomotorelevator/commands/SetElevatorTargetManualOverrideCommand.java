package frc.lib.modules.twomotorelevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.modules.twomotorelevator.TwoMotorElevatorSubsystem;

public class SetElevatorTargetManualOverrideCommand extends CommandBase {

    public TwoMotorElevatorSubsystem elevator;
    public boolean override;

    public SetElevatorTargetManualOverrideCommand(boolean override, TwoMotorElevatorSubsystem elevator) {
        this.override = override;
        this.elevator = elevator;

        addRequirements(elevator);
    }

    public void initialize() {
        elevator.targetOverridden = override;

        if(!override) {
            elevator.resetTarget();
            elevator.setPower(0);
        }
    }

    public boolean isFinished() {
        return true;
    }
    
}
