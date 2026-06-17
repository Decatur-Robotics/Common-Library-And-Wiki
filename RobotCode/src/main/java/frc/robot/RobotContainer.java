// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LEDs.LED;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.climber.ClimberIO;
import frc.robot.subsystems.climber.ClimberIOTalonFX;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.DriveCommands;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.superstructure.Superstructure;
import frc.robot.subsystems.superstructure.arm.Arm;
import frc.robot.subsystems.superstructure.arm.ArmIOTalonFX;
import frc.robot.subsystems.superstructure.elevator.Elevator;
import frc.robot.subsystems.superstructure.elevator.ElevatorIO;
import frc.robot.subsystems.superstructure.elevator.ElevatorIOTalonFX;
import frc.robot.subsystems.superstructure.intake.Intake;
import frc.robot.subsystems.superstructure.intake.IntakeIOTalonFX;
import frc.robot.subsystems.superstructure.wrist.Wrist;
import frc.robot.subsystems.superstructure.wrist.WristIOTalonFX;
import frc.robot.util.LogitechControllerButtons;

import java.util.function.Supplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

 
public class RobotContainer {

  private static RobotContainer instance;
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Drive swerve = TunerConstants.createDrivetrain();


  private final Climber climber;
  private final Elevator elevator; 
  private final Arm arm;
  private final Intake intake;
  private final Wrist wrist;
  private final LED led;
  private final Drive drive;

  private final Superstructure superstructure;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    instance = this;
  
    climber = new Climber( new ClimberIOTalonFX());
    elevator = new Elevator(new ElevatorIOTalonFX());
    arm = new Arm( new ArmIOTalonFX());
    intake = new Intake(new IntakeIOTalonFX());
    wrist = new Wrist("Wrist", new WristIOTalonFX());
    led = new LED();
    drive = new Drive(new GyroIOPigeon2(), new ModuleIOTalonFX(TunerConstants.FrontLeft), new ModuleIOTalonFX(TunerConstants.FrontRight), new ModuleIOTalonFX(TunerConstants.BackLeft), new ModuleIOTalonFX(TunerConstants.BackRight));
    superstructure = new Superstructure(elevator, arm, intake, wrist, led);
    // Configure the trigger bindings
    configurePrimaryBindings();
    configureSecondaryBindings();
    Supplier<Boolean> isAligned = () -> (Robot.isInSimulation());
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configurePrimaryBindings() {

    Joystick joystick = new Joystick(0);

    POVButton down = new POVButton(joystick, LogitechControllerButtons.down);
    POVButton up = new POVButton(joystick, LogitechControllerButtons.up);
        POVButton left = new POVButton(joystick, LogitechControllerButtons.left);
        POVButton right = new POVButton(joystick, LogitechControllerButtons.right);
        JoystickButton a = new JoystickButton(joystick, LogitechControllerButtons.a); 
        JoystickButton b = new JoystickButton(joystick, LogitechControllerButtons.b); // scoring at pose override
        JoystickButton x = new JoystickButton(joystick, LogitechControllerButtons.x);
        JoystickButton y = new JoystickButton(joystick, LogitechControllerButtons.y);
        JoystickButton triggerLeft = new JoystickButton(joystick, LogitechControllerButtons.triggerLeft);
        JoystickButton triggerRight = new JoystickButton(joystick, LogitechControllerButtons.triggerRight);
        JoystickButton bumperLeft = new JoystickButton(joystick, LogitechControllerButtons.bumperLeft);
        JoystickButton bumperRight = new JoystickButton(joystick, LogitechControllerButtons.bumperRight);




    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    drive.setDefaultCommand(
      DriveCommands.joystickDrive(
        drive,
        ()-> joystick.getY(),
        ()-> joystick.getX(),
        ()-> joystick.getTwist()
    ));

    

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }
  private void configureSecondaryBindings() {

    Joystick joystick = new Joystick(1);

        POVButton down = new POVButton(joystick, LogitechControllerButtons.down);
        POVButton up = new POVButton(joystick, LogitechControllerButtons.up);
        POVButton left = new POVButton(joystick, LogitechControllerButtons.left);
        POVButton right = new POVButton(joystick, LogitechControllerButtons.right);
        JoystickButton a = new JoystickButton(joystick, LogitechControllerButtons.a); 
        JoystickButton b = new JoystickButton(joystick, LogitechControllerButtons.b); // scoring at pose override
        JoystickButton x = new JoystickButton(joystick, LogitechControllerButtons.x);
        JoystickButton y = new JoystickButton(joystick, LogitechControllerButtons.y);
        JoystickButton triggerLeft = new JoystickButton(joystick, LogitechControllerButtons.triggerLeft);
        JoystickButton triggerRight = new JoystickButton(joystick, LogitechControllerButtons.triggerRight);
        JoystickButton bumperLeft = new JoystickButton(joystick, LogitechControllerButtons.bumperLeft);
        JoystickButton bumperRight = new JoystickButton(joystick, LogitechControllerButtons.bumperRight);

        Supplier<Boolean> overrideAtPose = () -> true;
        Supplier<Boolean> overrideNearPose = () -> true;
        Supplier<Boolean> isNearAligned = () -> true;
        Supplier<Boolean> isAligned = ()-> true;

        down.whileTrue(superstructure.scoreCoralL1Command(isNearAligned, isAligned, overrideNearPose, overrideAtPose));
        right.whileTrue(superstructure.scoreCoralL2Command(isNearAligned, isAligned, overrideNearPose, overrideAtPose));
        left.whileTrue(superstructure.scoreCoralL3Command(isNearAligned, isAligned, overrideNearPose, overrideAtPose));
        up.whileTrue(superstructure.scoreCoralL4Command(isNearAligned, isAligned, overrideNearPose, overrideAtPose));

        a.whileTrue(superstructure.intakeCoralGroundCommand());
        b.whileTrue(superstructure.intakeCoralHumanPlayerCommand());
        x.whileTrue(superstructure.dealgifyLowCommand());
        y.whileTrue(superstructure.dealgifyHighCommand());
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }

  public Drive getSwerve() {
    return swerve;
}

  public static RobotContainer getInstance() {
    return instance;
}
}
