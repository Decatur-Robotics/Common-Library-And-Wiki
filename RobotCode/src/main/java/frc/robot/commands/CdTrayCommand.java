package frc.robot.commands;

import java.time.LocalTime;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CdTraySubsystem;

public class CdTrayCommand extends Command
{
	final private CdTraySubsystem CdTray;

	Value cdMode;
	LocalTime startTime;
	long timeToWait = 100 * 1000000;

	public boolean closed = false;

	public CdTrayCommand(Value clawMode, CdTraySubsystem CdTray, boolean closed)
	
		System.out.println("Constructing CdTrayCommand...");
		this.CdTray = CdTray;
		this.cdMode = cdMode;
		this.closed = true;
		addRequirements(CdTray);
	}

	public void initialize()
	{
		System.out.println("Initializing CdTrayCommand...");
		CdTray.setSolenoid(cdMode);
		startTime = LocalTime.now();
	}

	public void execute()
	{

		CdTray.closed = true;

		if (CdTray.cdArmLeft.get() == Value.kOff && startTime == null)
		{
			CdTray.setSolenoid(cdMode);
			startTime = LocalTime.now();
		}
	}

	public boolean isFinished()
	{
		return startTime != null && LocalTime.now().minusNanos(timeToWait).compareTo(startTime) > 0;
	}

	public void end()
	{
		System.out.println("Ending CdTrayCommand...");
		CdTray.cdArmLeft.set(Value.kOff);
	}

}
