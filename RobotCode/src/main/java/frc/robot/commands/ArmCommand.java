package frc.robot.commands;

import org.w3c.dom.views.DocumentView;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants;
import frc.robot.subsystems.ArmSubsytem;

public class ArmCommand extends Command
{
	public ArmSubsytem armLeft;


	public ArmCommand()
	{
		this.armLeft = armLeft;


		addRequirements(armLeft);
	
	}
	
	public void initialize(ArmSubsytem arm, double targetAngleLower, double targetAngleUpper){
		armLeft.setTargetRotation(targetAngleLower);
	}
}
