package frc.robot.commands;

import org.w3c.dom.views.DocumentView;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.ArmSubsytem;

public class ArmCommand extends Command
{
	public ArmSubsytem armLeft;
	public ArmSubsytem armRight;

	public ArmCommand()
	{
		this.armLeft = armLeft;
		this.armRight = armRight;

		addRequirements(armLeft);
		addRequirements(armRight);
	}

	
	public void initialize(ArmSubsytem arm, double targetAngleLower, double targetAngleUpper){
		armLeft.setTargetRotation(targetAngleLower);
	}

	
	public void stop(boolean interrupt){
		armLeft.setTargetRotation(0);

	}

	public void intakePosition(){
		armLeft.setTargetRotation(ArmConstants.ARM_LOWER_INTAKE_POSITION);

	}

	public void lowerShelfPosition(){
		armLeft.setTargetRotation(ArmConstants.ARM_LOWER_SHELF_POSITION_LOWER);

	}

	public void middleShelfPosition(){
		armLeft.setTargetRotation(ArmConstants.ARM_LOWER_SHELF_POSITION_MIDDLE);
	
	}

	public void upperShelfPosition(){
		armLeft.setTargetRotation(ArmConstants.ARM_LOWER_SHELF_POSITION_UPPER);
	
	}


}
