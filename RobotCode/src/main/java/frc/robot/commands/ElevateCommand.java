package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ElevatorPosition;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevateCommand extends Command {
	
	private ElevatorSubsystem elevatorSubsystem;
	private double targetPosition;

	public ElevateCommand(ElevatorSubsystem subsystem, double position) {
		
		this.elevatorSubsystem = subsystem;
		this.targetPosition = position;

		this.addRequirements( this.elevatorSubsystem );

	}

	@Override
	public void initialize() {
		elevatorSubsystem.setTargetPosition( this.targetPosition );
	}

}
