// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AgitateCommand;
import frc.robot.commands.AltLightsCommand;
import frc.robot.commands.LightsCommand;
import frc.robot.commands.MoveCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.AgitateSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer
{

	private final LEDSubsystem ledSubsystem = new LEDSubsystem(300);
	
	private JoystickButton aButton;
	private JoystickButton bButton;

	public RobotContainer() {
		configureBindings();
	}

	private void configureBindings() {

		secondaryController = new Joystick(1);
		aButton = new JoystickButton(secondaryController, LogitechControllerButtons.a);
		bButton = new JoystickButton(secondaryController, LogitechControllerButtons.b);
		
		aButton.whileTrue(new LightsCommand(ledSubsystem));
		
		bButton.whileTrue(new AltLightsCommand(ledSubsystem));
		bButton.whileFalse(new AltLightsOffCommand(ledSubsystem));
	}
}
