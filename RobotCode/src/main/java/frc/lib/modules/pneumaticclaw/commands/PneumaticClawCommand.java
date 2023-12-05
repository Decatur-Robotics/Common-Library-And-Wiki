package frc.lib.modules.pneumaticclaw.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc. lib.modules.pneumaticclaw.PneumaticClawSubsystem;

public class PneumaticClawCommand extends CommandBase {
    
    private PneumaticClawSubsystem clawIntake;

    private Value clawMode;

    public PneumaticClawCommand(Value clawMode, PneumaticClawSubsystem clawIntake) {
        this.clawIntake = clawIntake;
        this.clawMode = clawMode;

        addRequirements(clawIntake);
    }

	@Override
    public void initialize() {
		// Set claw to desired mode immediately
        clawIntake.setSolenoid(clawMode);
    }

	@Override
    public boolean isFinished() {
		// Immediately end command
        return true;
    }
}

