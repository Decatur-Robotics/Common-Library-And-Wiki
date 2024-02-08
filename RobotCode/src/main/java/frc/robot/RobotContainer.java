package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.lib.modules.swervedrive.Commands.TeleopAimSwerveCommand;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.commands.ShooterOverrideCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/** The container for the robot. Contains subsystems, OI devices, and commands. */
public class RobotContainer
{

	private static RobotContainer instance;

	private final IntakeSubsystem intakeSubsytem;

	private final ShuffleboardTab shuffleboardTab;

	private final SendableChooser<Command> autoChooser;

	private final SwerveDriveSubsystem swerveDrive;
	private final ClimberSubsystem climberSubsystem;
	private final ShooterSubsystem shooterSubsystem;

	private final ShooterMountSubsystem shooterMountSubsystem;
	private final VisionSubsystem visionSubsystem;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

		shuffleboardTab = Shuffleboard.getTab("Tab 1");

		autoChooser = AutoBuilder.buildAutoChooser();

		// Instantiate subsystems
		swerveDrive = new SwerveDriveSubsystem();
		climberSubsystem = new ClimberSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		shooterMountSubsystem = new ShooterMountSubsystem();
		intakeSubsytem = new IntakeSubsystem();
		visionSubsystem = new VisionSubsystem();

		Autonomous.init(this);

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{
		final Joystick PrimaryController = new Joystick(0);

		final JoystickButton rightTrigger = new JoystickButton(PrimaryController,
				LogitechControllerButtons.triggerRight);

		rightTrigger.whileTrue(SwerveDrive.getTeleopAimCommand(PrimaryController, VisionSubsystem));
	}

	private void configureSecondaryBindings()
	{
    // Note: Right trigger is being bound twice- once in configurePrimaryBindings, once here! Fix before competition.
		final Joystick secondaryController = new Joystick(1);
		final JoystickButton rightTrigger = new JoystickButton(secondaryController,
				LogitechControllerButtons.triggerRight);

		rightTrigger.whileTrue(new ShooterCommand(shooterSubsystem));
	}

	public static ShuffleboardTab getShuffleboardTab()
	{
		return instance.shuffleboardTab;
	}

	public SwerveDriveSubsystem getSwerveDrive()
	{
		return swerveDrive;
	}

	public ShooterSubsystem getShooter()
	{
		return shooterSubsystem;
	}

	public ShooterMountSubsystem getShooterMount()
	{
		return shooterMountSubsystem;
	}

	public VisionSubsystem getVision()
	{
		return visionSubsystem;
	}

}
