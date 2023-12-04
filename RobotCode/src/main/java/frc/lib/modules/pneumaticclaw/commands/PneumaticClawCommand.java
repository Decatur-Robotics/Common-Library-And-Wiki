package frc.lib.modules.pneumaticclaw.commands;

import java.time.LocalTime;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc. lib.modules.pneumaticclaw.PneumaticClawSubsystem;

public class PneumaticClawCommand extends CommandBase {
    
    private PneumaticClawSubsystem clawIntake;

    private Value clawMode;
    private LocalTime startTime;
    private long timeToWait;

    public PneumaticClawCommand(Value clawMode, PneumaticClawSubsystem clawIntake, boolean open) {
        this.clawIntake = clawIntake;
        this.clawMode = clawMode;

		timeToWait = 100000000;
		open = false;

        addRequirements(clawIntake);
    }

	@Override
    public void initialize() {
        clawIntake.setSolenoid(clawMode);
        startTime = LocalTime.now();
    }

	@Override
    public void execute() {
        if (startTime == null) {
            clawIntake.setSolenoid(clawMode);
            startTime = LocalTime.now();
        }
    }

	@Override
    public boolean isFinished() {
        return startTime != null && LocalTime.now().minusNanos(timeToWait).compareTo(startTime) > 0;
    }

    public void end() {
		clawIntake.setSolenoid(Value.kOff);
    }
}

