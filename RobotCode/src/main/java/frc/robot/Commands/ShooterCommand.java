package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems.ShooterSubsystem;

public class ShooterCommand extends CommandBase
{
	// makes the subsystem object
	private final ShooterSubsystem Shooter;

	public ShooterCommand(ShooterSubsystem shoot)
	{
		
		Shooter = shoot;
		addRequirements(Shooter);
	}
	// execute command
	public void execute()
	{
		
		// spins up the motor
		Shooter.setShooterMotorPower(1.0, "joystick said to shoot");
		// if statement  to see if motor is spun up
		if (Shooter.getShooterMotorPower() >= 0.95)
		{
			Shooter.setFeedMotorPower(1.0, "motor is spun");
		}
		else
		{
			Shooter.setFeedMotorPower(0, "motor is not spun");
		}
	}
	// the end command
	public void end(boolean interrupted)
	{
		Shooter.setShooterMotorPower(0.25, "command is over");
		Shooter.setFeedMotorPower(0, "command is over");
	}
}
