package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command
{
	// Initializes the subsystem object
	private ShooterSubsystem shooter;

	public ShooterCommand(ShooterSubsystem shooter)
	{

		this.shooter = shooter;
		addRequirements(shooter);
	}

	public void execute()
	{

		// Spins up the motor
		shooter.setShooterMotorPower(1.0, "joystick said to shoot");
		// If-statement to see if motor is spun up
		if (shooter.getShooterMotorPower() >= 0.95)
		{
			shooter.setFeedMotorPower(1.0, "motor is spun");
		}
		else
		{
			shooter.setFeedMotorPower(0, "motor is not spun");
		}
	}

	public void end(boolean interrupted)
	{
		shooter.setShooterMotorPower(0.25, "command is over");
		shooter.setFeedMotorPower(0, "command is over");
	}
}
