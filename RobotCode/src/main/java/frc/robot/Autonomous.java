package frc.robot;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.core.ILogSource;
import frc.lib.modules.swervedrive.SwerveConstants;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.RotateShooterMountToPositionCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.ShooterConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * <p>
 * A singleton class for handling autonomous. Puts dropdowns on Shuffleboard and then reads from
 * them to dynamically generate an autonomous. Uses
 * <a href="https://github.com/mjansen4857/pathplanner">PathPlanner</a> to follow paths and run
 * commands.
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
public abstract class Autonomous implements ILogSource
{

    private final RobotContainer RobotContainer;

    public Autonomous(final RobotContainer RobotContainer)
    {
        this.RobotContainer = RobotContainer;
        registerNamedCommands();
    }

    /** Registers commands for building autos through PathPlanner. */
    private void registerNamedCommands()
    {
        logFine("Registering named commands...");

        // Get subsystems
        final ShooterMountSubsystem ShooterMount = RobotContainer.getShooterMount();
        final IndexerSubsystem Indexer = RobotContainer.getIndexer();
        final IntakeSubsystem Intake = RobotContainer.getIntake();

        // Initialize commands
        final IntakeCommand IntakeCommand = new IntakeCommand(Intake, Indexer, ShooterMount);
        NamedCommands.registerCommand("Intake", IntakeCommand);

        final ShootCommand ShootCommand = new ShootCommand(Indexer);
        NamedCommands.registerCommand("Shoot", ShootCommand);


        // Populate rotation commands
        for (double rot : AutoConstants.AutoShooterMountRotations)
        {
            RotateShooterMountToPositionCommand rotateCommand = new RotateShooterMountToPositionCommand(
                    ShooterMount, rot);
            NamedCommands.registerCommand("Aim to " + rot + " deg", rotateCommand);
            NamedCommands.registerCommand("Shoot then Aim to " + rot + " deg",
                    new SequentialCommandGroup(ShootCommand, rotateCommand));

            
        }
        registerAutosAsNamedCommands();
    }

    private void registerAutosAsNamedCommands(){
        for(String auto : AutoBuilder.getAllAutoNames())
            NamedCommands.registerCommand("Auto " + auto, getPathPlannerAuto(auto));

    }

    /**
     * Returns a command to follow a path from PathPlanner GUI whilst avoiding obstacles
     * 
     * @param PathName The filename of the path to follow w/o file extension. Must be in the paths
     *                 folder. Ex: Example Human Player Pickup
     * @return A command that will drive the robot along the path
     */
    protected static Command followPath(final String PathName)
    {
        final PathPlannerPath path = PathPlannerPath.fromPathFile(PathName);
        return AutoBuilder.pathfindThenFollowPath(path,
                SwerveConstants.AutoConstants.PathConstraints);
    }

    protected static Command getPathPlannerAuto(final String PathName)
    {
        return new PathPlannerAuto(PathName);

    }

    public abstract Optional<Command> buildAutoCommand();

    protected RobotContainer getRobotContainer(){
        return RobotContainer;
    }

}