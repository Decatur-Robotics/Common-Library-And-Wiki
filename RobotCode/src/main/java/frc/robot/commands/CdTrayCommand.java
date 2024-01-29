package frc.robot.commands;

import java.time.LocalTime;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CdTraySubsystem;

public class CdTrayCommand extends Command
{
	private final CdTraySubsystem CdTray;

	private Value cdMode;
	private LocalTime startTime;
	private long timeToWait = 100 * 1000000;
	// milliseconds * 1000000

	private boolean closed;

	public CdTrayCommand(CdTraySubsystem CdTray, CdTrayCommand cdMode)
	{
		System.out.println("Constructing CdTrayCommand...");
		this.CdTray = CdTray;
		this.closed = true;
		this.cdMode = cdMode;
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

		if (CdTray.getCdArmLeft().get() == Value.kReverse && startTime == null)
		{
			CdTray.setSolenoid(cdMode);
			startTime = LocalTime.now();
		}
	}

}
