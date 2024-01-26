package frc.robot.Commands;

import java.time.LocalTime;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.CdTraySubsystem;

public class CdTrayCommand extends Command
{
	private final CdTraySubsystem CdTray;

	private Value cdMode;
	private LocalTime startTime;
	private long timeToWait = 100 * 1000000;
	// milliseconds * 1000000

	private boolean closed;

	public CdTrayCommand(CdTraySubsystem CdTray)
	{
		System.out.println("Constructing CdTrayCommand...");
		this.CdTray = CdTray;
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

		if (CdTray.getCdArmLeft().get() == Value.kOff && startTime == null)
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
		CdTray.getCdArmLeft().set(Value.kOff);
	}

}
