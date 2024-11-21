package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command
{
	private IntakeSubsystem intake;
	public IntakeCommand(IntakeSubsystem intake){
		this.intake = intake;
		addRequirements(intake);
	}
	@Override
	public void initialize(){
		intake.setVelocity(IntakeConstants.INTAKING_VELOCITY);
		intake.setRotation(IntakeConstants.INTAKE_OUT_ROTATION);
	}
	
	public void end(){
		intake.setRotation(IntakeConstants.INTAKE_OUT_ROTATION);
		intake.setVelocity(IntakeConstants.REST_VELOCITY);
	}
}

