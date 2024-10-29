package frc.robot.commands;

import org.w3c.dom.views.DocumentView;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.ArmSubsytem;

public class ArmCommand extends Command
{
	public ArmSubsytem armLower;
	public ArmSubsytem armUpper;

	public ArmCommand()
	{
		this.armLower = armLower;
		this.armUpper = armUpper;

		addRequirements(armLower);
		addRequirements(armUpper);
	}

	
	public void initialize(ArmSubsytem arm, double targetAngleLower, double targetAngleUpper){
		armLower.setLowerTargetRotation(targetAngleLower);
		armUpper.setUpperTargetRotation(targetAngleUpper);
	}

	
	public void stop(boolean interrupt){
		armLower.setLowerTargetRotation(0);
		armUpper.setUpperTargetRotation(0);
	}

	public void intakePosition(){
		armLower.setLowerTargetRotation(ArmConstants.ARM_LOWER_INTAKE_POSITION);
		armUpper.setUpperTargetRotation(ArmConstants.ARM_UPPER_INTAKE_POSITION);
	}

	public void lowerShelfPosition(){
		armLower.setLowerTargetRotation(ArmConstants.ARM_LOWER_SHELF_POSITION_LOWER);
		armUpper.setUpperTargetRotation(ArmConstants.ARM_UPPER_SHELF_POSITION_LOWER);
	}

	public void middleShelfPosition(){
		armLower.setLowerTargetRotation(ArmConstants.ARM_LOWER_SHELF_POSITION_MIDDLE);
		armUpper.setUpperTargetRotation(ArmConstants.ARM_UPPER_SHELF_POSITION_MIDDLE);
	}

	public void upperShelfPosition(){
		armLower.setLowerTargetRotation(ArmConstants.ARM_LOWER_SHELF_POSITION_UPPER);
		armUpper.setUpperTargetRotation(ArmConstants.ARM_UPPER_SHELF_POSITION_UPPER);
	}


}
