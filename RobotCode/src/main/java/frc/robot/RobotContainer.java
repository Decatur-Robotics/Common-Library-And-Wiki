package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/** The container for the robot. Contains subsystems, OI devices, and commands. */
public class RobotContainer
{

	private static RobotContainer instance;

	private final IntakeSubsystem intakeSubsytem;

	private final ShuffleboardTab shuffleboardTab;

	private final SendableChooser<Command> autoChooser;

	private final SwerveDriveSubsystem SwerveDrive;
	private final ClimberSubsystem climberSubsystem;
	private final ShooterSubsystem shooterSubsystem;

	private final ShooterMountSubsystem shooterMountSubsystem;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

		shuffleboardTab = Shuffleboard.getTab("Tab 1");

		autoChooser = AutoBuilder.buildAutoChooser();

		// Instantiate subsystems
		SwerveDrive = new SwerveDriveSubsystem();
		climberSubsystem = new ClimberSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		shooterMountSubsystem = new ShooterMountSubsystem();
		intakeSubsytem = new IntakeSubsystem();

		Autonomous.init(this);

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{
		final Joystick primaryController = new Joystick(0);
	}

	private void configureSecondaryBindings()
	{
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
		return SwerveDrive;
	}

	public ShooterSubsystem getShooter()
	{
		return shooterSubsystem;
	}

}
