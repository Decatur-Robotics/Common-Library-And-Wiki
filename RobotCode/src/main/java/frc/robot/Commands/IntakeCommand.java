package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ClawConstants;
import frc.robot.constants.ElevatorConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.subsystems.ArmSubsytem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subystems.ClawSubsystem;

public class IntakeCommand extends Command {

	private IntakeSubsystem intake;
	private ArmSubsytem arm;
	private ElevatorSubsystem elevator;
	private ClawSubsystem claw;
	private boolean limitSwitchPressed;

	public IntakeCommand(IntakeSubsystem intake) {
		this.intake = intake;
		addRequirements(intake);
	}
	
	@Override
	public void initialize() {
		
		intake.setVelocity(IntakeConstants.INTAKING_VELOCITY);
		intake.setRotation(IntakeConstants.INTAKE_OUT_ROTATION);
		elevator.setTargetPosition(ElevatorConstants.INTAKE_HEIGHT);
		claw.setClawSpeed(ClawConstants.CLAW_INTAKE_VELOCITY);
		arm.setTargetRotation(ArmConstants.ARM_INTAKE_POSITION);


	}
	
	@Override
	public void execute() {
		limitSwitchPressed = claw.getLimitSwitch();
		if(limitSwitchPressed){
			claw.setClawSpeed(0);
			intake.setVelocity(0);
			intake.setRotation(IntakeConstants.INTAKE_IN_ROTATION);
		}
	}
	
	@Override
	public boolean isFinished() {
		return limitSwitchPressed;
	}
	
	
}

