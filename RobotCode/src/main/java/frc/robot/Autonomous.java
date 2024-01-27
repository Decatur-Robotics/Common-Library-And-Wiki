package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveConstants;
import frc.lib.modules.swervedrive.Commands.DriveDistanceAuto;

public class Autonomous
{

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
        Gui = frc.robot.RobotContainer.getShuffleboardTab();

        // Set up starting position chooser
        SendableChooser<StartingPosition> startingPositionChooser = new SendableChooser<>();
        startingPositionChooser.setDefaultOption("Middle", StartingPosition.Middle);
        startingPositionChooser.addOption("Amp Side", StartingPosition.Amp);
        startingPositionChooser.addOption("Middle", StartingPosition.Middle);
        startingPositionChooser.addOption("Human Player Side", StartingPosition.HumanPlayer);
        Gui.add("Starting Position", startingPositionChooser);
        StartingPositionChooser = startingPositionChooser;

        // Set up auto mode chooser
        SendableChooser<AutoMode> autoModeChooser = new SendableChooser<>();
        autoModeChooser.setDefaultOption("Do Nothing", AutoMode.DoNothing);
        autoModeChooser.addOption("Do Nothing", AutoMode.DoNothing);
        autoModeChooser.addOption("Leave", AutoMode.Leave);
        autoModeChooser.addOption("Multi Note", AutoMode.MultiNote);
        Gui.add("Auto Mode", autoModeChooser);
        AutoModeChooser = autoModeChooser;
    }

    /** Creates an instance and adds auto options to Shuffleboard */
    public static void init(RobotContainer robotContainer)
    {
        if (instance == null)
        {
            new Autonomous(robotContainer);
        }
        else if (instance.RobotContainer != robotContainer)
        {
            throw new IllegalStateException("Cannot reinitialize Autonomous!");
        }
    }

    /**
     * Parses selected options into a single command
     */
    public Command buildAutoCommand()
    {
        final StartingPosition StartingPosition = StartingPositionChooser.getSelected();
        final AutoMode AutoMode = AutoModeChooser.getSelected();

        switch (AutoMode)
        {
        case DoNothing:
            return null;
        case Leave:
            return new DriveDistanceAuto(0, 0, null);
        case MultiNote:
            return null;
        default:
            throw new IllegalStateException("Invalid auto mode: " + AutoMode);
        }
    }

    /** Calls {@link #buildAutoCommand()} */
    public static Command getAutoCommand()
    {
        return instance.buildAutoCommand();
    }

    /** Closes the instance's SendableChoosers to free up resources */
    public static void close()
    {
        instance.StartingPositionChooser.close();
        instance.AutoModeChooser.close();
    }

    /**
     * Returns a command to follow a path from PathPlanner GUI whilst avoiding obstacles
     * 
     * @param pathName The filename of the path to follow w/o file extension. Must be in the paths
     *                 folder. Ex: Example Human Player Pickup
     * @return A command that will drive the robot along the path
     */
    private Command followPath(final String pathName)
    {
        final PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);
        return AutoBuilder.pathfindThenFollowPath(path,
                SwerveConstants.AutoConstants.PathConstraints);
    }

}
