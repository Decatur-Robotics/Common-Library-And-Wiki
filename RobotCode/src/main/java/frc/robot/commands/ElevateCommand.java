package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevateCommand extends Command {
	
	private ElevatorSubsystem elevatorSubsystem;
	private int targetPosition;

	public ElevateCommand(ElevatorSubsystem subsystem, int position) {
		
		this.elevatorSubsystem = subsystem;
		this.targetPosition = position;

		this.addRequirements( this.elevatorSubsystem );

	}

	@Override
	public void initialize() {
		elevatorSubsystem.setElevatorPosition( this.targetPosition );
	}

	@Override
	public boolean isFinished() {
		return elevatorSubsystem.isFinished();
	}

}
