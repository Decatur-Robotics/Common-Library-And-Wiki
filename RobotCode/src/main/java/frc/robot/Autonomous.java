package frc.robot;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.core.ILogSource;
import frc.lib.modules.swervedrive.SwerveConstants;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.lib.modules.swervedrive.Commands.AutoAimSwerveCommand;
import frc.lib.modules.swervedrive.Commands.DriveDistanceAuto;
import frc.robot.commands.AimShooterCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

/**
 * <p>
 * A singleton class for handling autonomous. Puts dropdowns on Shuffleboard and then reads from
 * them to dynamically generate an autonomous. Uses
 * <a href="https://github.com/mjansen4857/pathplanner">PathPlanner</a> to follow paths.
 * </p>
 * <p>
 * <b>Usage:</b> Call {@link #init(RobotContainer)} in RobotContainer's constructor. Then, to
 * actually get the autonomous command, call {@link #getAutoCommand()}.
 * </p>
 * <p>
 * <b>Autonomous Options:</b> The options are hardcoded; they are built off the enums at the top of
 * the class and then manually read into dropdowns on Shuffleboard. I would like to at some point
 * improve this system to be more easily configureable.
 * </p>
 */
public class Autonomous implements ILogSource
{

    // Options for configuring autonomous

    private enum StartingPosition
    {
        Amp, Middle, HumanPlayer
    }

    private enum AutoMode
    {
        DoNothing, Leave, MultiNote
    }

    private static Autonomous instance;

    private final RobotContainer RobotContainer;

    private final ShuffleboardTab Gui;
    private final SendableChooser<StartingPosition> StartingPositionChooser;
    private final SendableChooser<AutoMode> AutoModeChooser;

    private Autonomous(RobotContainer robotContainer)
    {
        instance = this;
        RobotContainer = robotContainer;

        logFine("Initializing GUI...");
        Gui = frc.robot.RobotContainer.getShuffleboardTab();

        // Set up starting position chooser
        final SendableChooser<StartingPosition> StartingPositionChooser = new SendableChooser<>();
        StartingPositionChooser.setDefaultOption("Middle", StartingPosition.Middle);
        StartingPositionChooser.addOption("Amp Side", StartingPosition.Amp);
        StartingPositionChooser.addOption("Middle", StartingPosition.Middle);
        StartingPositionChooser.addOption("Human Player Side", StartingPosition.HumanPlayer);
        Gui.add("Starting Position", StartingPositionChooser);
        this.StartingPositionChooser = StartingPositionChooser;

        // Set up auto mode chooser
        final SendableChooser<AutoMode> AutoModeChooser = new SendableChooser<>();
        AutoModeChooser.setDefaultOption("Do Nothing", AutoMode.DoNothing);
        AutoModeChooser.addOption("Do Nothing", AutoMode.DoNothing);
        AutoModeChooser.addOption("Leave", AutoMode.Leave);
        AutoModeChooser.addOption("Multi Note", AutoMode.MultiNote);
        Gui.add("Auto Mode", AutoModeChooser);
        this.AutoModeChooser = AutoModeChooser;

        logFine("GUI initialized!");
    }

    /**
     * Creates an instance and adds auto options to Shuffleboard. Must be called before anything can
     * be done using Autonomous. Creates a new instance if one does not exist, otherwise logs an
     * exception.
     */
    public static void init(RobotContainer robotContainer)
    {
        if (instance == null)
            new Autonomous(robotContainer);
        else
            instance.logException("Attempted to reinitialize Autonomous! This should not happen!");
    }

    /**
     * Parses selected options into a single command. {@link #init(RobotContainer)} must be called
     * first.
     */
    private Optional<Command> buildAutoCommand()
    {
        logInfo("Building auto command...");

        final StartingPosition StartingPosition = StartingPositionChooser.getSelected();
        logFine("Read starting position: " + StartingPosition);
        final AutoMode AutoMode = AutoModeChooser.getSelected();
        logFine("Read auto mode: " + AutoMode);

        final SwerveDriveSubsystem SwerveDrive = RobotContainer.getSwerveDrive();
        final ShooterMountSubsystem ShooterMount = RobotContainer.getShooterMount();
        final ShooterSubsystem Shooter = RobotContainer.getShooter();
        final VisionSubsystem Vision = RobotContainer.getVision();
        final IndexerSubsystem Indexer = RobotContainer.getIndexer();
        final IntakeSubsystem Intake = RobotContainer.getIntake();

        logFine("Initializing command groups...");

        // Most of our auto will go in AutoMain
        final SequentialCommandGroup AutoMain = new SequentialCommandGroup(
                new AimShooterCommand(Shooter, ShooterMount, Vision, SwerveDrive));

        // Initialize commands
        final AimShooterCommand AimShooterCommand = new AimShooterCommand(Shooter, ShooterMount,
                Vision, SwerveDrive);
        final AutoAimSwerveCommand AutoAimSwerveCommand = new AutoAimSwerveCommand(SwerveDrive,
                Vision, Indexer);
        final IntakeCommand IntakeCommand = new IntakeCommand(Intake, Indexer, ShooterMount);

        // Aim shooter mount and chassis in parallel, then shoot once both are aimed
        // We still need to add the check that shooter mount is aimed to AutoAimSwerveCommand
        final ParallelRaceGroup ShootGroup = new ParallelRaceGroup(AutoAimSwerveCommand,
                AimShooterCommand);

        logFine("Command groups initialized! Adding commands based on AutoMode...");
        switch (AutoMode)
        {
        case DoNothing:
            logFiner("Doing nothing...");
            break;

        case Leave:
            logFiner("Adding leave command...");
            AutoMain.addCommands(ShootGroup, new DriveDistanceAuto(AutoConstants.LEAVE_DISTANCE,
                    SwerveConstants.AutoConstants.MAX_SPEED, SwerveDrive));
            break;

        case MultiNote:
            logFiner("Adding multi-note command based on StartingPosition...");
            String[] pathSequence = null;

            switch (StartingPosition)
            {
            case Amp:
            case Middle:
                pathSequence = new String[]
                {
                        StartingPosition == Autonomous.StartingPosition.Amp
                                ? "Top Start to Top Note"
                                : "Middle Start to Top Note",
                        "Top to Middle Note", "Middle to Bottom Note",
                };
                break;
            case HumanPlayer:
                pathSequence = new String[]
                {
                        "Bottom Start to Bottom Note", "Bottom to Middle Note",
                        "Middle to Top Note",
                };
                break;
            }

            if (pathSequence == null)
                break;

            logFiner("Adding path sequence: " + String.join(", ", pathSequence));
            for (String pathName : pathSequence)
            {
                ParallelRaceGroup commandsWhileFollowingPath = new ParallelRaceGroup(IntakeCommand,
                        followPath(pathName));

                AutoMain.addCommands(ShootGroup, commandsWhileFollowingPath);
            }

            break;
        }

        logInfo("Auto command built!");
        return Optional.ofNullable(AutoMain);
    }

    /**
     * Calls {@link #buildAutoCommand()}. {@link #init(RobotContainer)} must be called first!
     */
    public static Optional<Command> getAutoCommand()
    {
        return instance.buildAutoCommand();
    }

    /**
     * Closes the instance's SendableChoosers ({@link #AutoModeChooser} &
     * {@link #StartingPositionChooser}) to free up resources
     */
    public static void close()
    {
        instance.logFine("Closing Autonomous GUI...");
        instance.StartingPositionChooser.close();
        instance.AutoModeChooser.close();
    }

    /**
     * Returns a command to follow a path from PathPlanner GUI whilst avoiding obstacles
     * 
     * @param PathName The filename of the path to follow w/o file extension. Must be in the paths
     *                 folder. Ex: Example Human Player Pickup
     * @return A command that will drive the robot along the path
     */
    private Command followPath(final String PathName)
    {
        final PathPlannerPath path = PathPlannerPath.fromPathFile(PathName);
        return AutoBuilder.pathfindThenFollowPath(path,
                SwerveConstants.AutoConstants.PathConstraints);
    }

}