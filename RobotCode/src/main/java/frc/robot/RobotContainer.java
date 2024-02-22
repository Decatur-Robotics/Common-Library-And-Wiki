package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.modules.swervedrive.SwerveConstants;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.lib.modules.swervedrive.Commands.ZeroGyroCommand;
import frc.lib.core.LogitechControllerButtons;
import frc.robot.commands.AimShooterCommand;
// import frc.robot.commands.ClimberOverrideCommand;
// import frc.robot.commands.ClimberSpeedCommand;
// import frc.robot.commands.ClimberToPositionCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RotateShooterMountToPositionCommand;
import frc.robot.commands.ShooterOverrideCommand;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.constants.ShooterMountConstants;
// import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/** The container for the robot. Contains subsystems, OI devices, and commands. */
public class RobotContainer
{

	private static RobotContainer instance;

	private final ShuffleboardTab ShuffleboardTab;

	private final SwerveDriveSubsystem SwerveDrive;
	// private final ClimberSubsystem ClimberSubsystem;
	private final ShooterSubsystem ShooterSubsystem;
	private final ShooterMountSubsystem ShooterMountSubsystem;
	private final VisionSubsystem VisionSubsystem;
	private final IndexerSubsystem IndexerSubsystem;
	private final IntakeSubsystem IntakeSubsystem;

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	public RobotContainer()
	{
		instance = this;

		ShuffleboardTab = Shuffleboard.getTab("Tab 1");

		// Instantiate subsystems
		SwerveDrive = new SwerveDriveSubsystem();
		// ClimberSubsystem = new ClimberSubsystem();
		ShooterSubsystem = new ShooterSubsystem();
		ShooterMountSubsystem = new ShooterMountSubsystem();
		VisionSubsystem = new VisionSubsystem(SwerveDrive, ShooterMountSubsystem);
		IndexerSubsystem = new IndexerSubsystem();
		IntakeSubsystem = new IntakeSubsystem();

		Autonomous.init(this);

		// Configure the button bindings
		configurePrimaryBindings();
		configureSecondaryBindings();
	}

	private void configurePrimaryBindings()
	{
		final Joystick PrimaryController = new Joystick(0);

		final JoystickButton LeftTrigger = new JoystickButton(PrimaryController, LogitechControllerButtons.triggerLeft);
		final JoystickButton RightTrigger = new JoystickButton(PrimaryController, LogitechControllerButtons.triggerRight);
		final JoystickButton YButton = new JoystickButton(PrimaryController, LogitechControllerButtons.y);

		SwerveDrive.setDefaultCommand(SwerveDrive.getDefaultCommand(PrimaryController));

		LeftTrigger.whileTrue(SwerveDrive.getTeleopAimToPositionAllianceRelativeCommand(PrimaryController, SwerveConstants.AMP_ROTATION));
		RightTrigger.whileTrue(SwerveDrive.getTeleopAimCommand(PrimaryController, VisionSubsystem,
				IndexerSubsystem));
		YButton.onTrue(new ZeroGyroCommand(SwerveDrive));
	}

	private void configureSecondaryBindings()
	{
		final Joystick SecondaryController = new Joystick(1);

		final JoystickButton LeftTrigger = new JoystickButton(SecondaryController, LogitechControllerButtons.triggerLeft);
		final JoystickButton RightTrigger = new JoystickButton(SecondaryController, LogitechControllerButtons.triggerRight);
		final JoystickButton LeftBumper = new JoystickButton(SecondaryController, LogitechControllerButtons.bumperLeft);
		final JoystickButton AButton = new JoystickButton(SecondaryController, LogitechControllerButtons.a);
		final JoystickButton XButton = new JoystickButton(SecondaryController, LogitechControllerButtons.x);
		final JoystickButton YButton = new JoystickButton(SecondaryController, LogitechControllerButtons.y);
		final JoystickButton UpButton = new JoystickButton(SecondaryController, LogitechControllerButtons.up);
		final JoystickButton DownButton = new JoystickButton(SecondaryController, LogitechControllerButtons.down);

		// ClimberSubsystem.setDefaultCommand(new ClimberSpeedCommand(ClimberSubsystem, () -> SecondaryController.getY(), () -> SecondaryController.getThrottle()));
		LeftTrigger.whileTrue(new ShooterOverrideCommand(ShooterSubsystem, IndexerSubsystem, ShooterConstants.SHOOTER_SPEAKER_VELOCITY));
		RightTrigger.whileTrue(new RotateShooterMountToPositionCommand(ShooterMountSubsystem, ShooterMountConstants.SHOOTER_MOUNT_SPEAKER_ANGLE_FIXED));
		// LeftBumper.whileTrue(new ClimberOverrideCommand(ClimberSubsystem));
		AButton.whileTrue(new RotateShooterMountToPositionCommand(ShooterMountSubsystem, ShooterMountConstants.SHOOTER_MOUNT_AMP_ANGLE));
		XButton.whileTrue(new IntakeCommand(IntakeSubsystem, IndexerSubsystem, ShooterMountSubsystem));
		YButton.whileTrue(new AimShooterCommand(ShooterSubsystem, ShooterMountSubsystem, VisionSubsystem, SwerveDrive));
		// UpButton.onTrue(new ClimberToPositionCommand(ClimberSubsystem, ClimberConstants.MAX_EXTENSION));
		// DownButton.onTrue(new ClimberToPositionCommand(ClimberSubsystem, ClimberConstants.MIN_EXTENSION));
	}

	public static ShuffleboardTab getShuffleboardTab()
	{
		return instance.ShuffleboardTab;
	}

	public SwerveDriveSubsystem getSwerveDrive()
	{
		return SwerveDrive;
	}

	public ShooterSubsystem getShooter()
	{
		return ShooterSubsystem;
	}

	public ShooterMountSubsystem getShooterMount()
	{
		return ShooterMountSubsystem;
	}

	public VisionSubsystem getVision()
	{
		return VisionSubsystem;
	}

	public IndexerSubsystem getIndexer()
	{
		return IndexerSubsystem;
	}

	public IntakeSubsystem getIntake()
	{
		return IntakeSubsystem;
	}

}