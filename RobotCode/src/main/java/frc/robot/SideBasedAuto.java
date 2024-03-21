package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.core.Autonomous;

public class SideBasedAuto extends Autonomous
{

    private enum Side
    {
        Amp("Amp Side"), Center("Center"), Source("Source Side");

        private final String autoName;

        private Side(String autoName)
        {
            this.autoName = autoName;
        }
    }

    private enum AutoMode
    {
        DriveBackwards("Drive Backwards"), OneNote("1 Note"), TwoNote("2 Note"),
        ThreeNote("3 Note"), FourNote("4 Note");

        private final String autoName;

        private AutoMode(String autoName)
        {
            this.autoName = autoName;
        }
    }

    private final SendableChooser<Side> SideChooser;
    private final SendableChooser<AutoMode> AutoModeChooser;

    public SideBasedAuto(frc.robot.RobotContainer RobotContainer)
    {
        super(RobotContainer);

        SideChooser = new SendableChooser<>();
        SideChooser.setDefaultOption(Side.Amp.autoName, Side.Amp);
        SideChooser.addOption(Side.Center.autoName, Side.Center);
        SideChooser.addOption(Side.Source.autoName, Side.Source);

        AutoModeChooser = new SendableChooser<>();
        AutoModeChooser.setDefaultOption(AutoMode.DriveBackwards.autoName, AutoMode.DriveBackwards);
        AutoModeChooser.addOption(AutoMode.OneNote.autoName, AutoMode.OneNote);
        AutoModeChooser.addOption(AutoMode.TwoNote.autoName, AutoMode.TwoNote);
        AutoModeChooser.addOption(AutoMode.ThreeNote.autoName, AutoMode.ThreeNote);
        AutoModeChooser.addOption(AutoMode.FourNote.autoName, AutoMode.FourNote);

        RobotContainer.getShuffleboardTab().add(SideChooser);
        RobotContainer.getShuffleboardTab().add(AutoModeChooser);
    }

    @Override
    public Optional<Command> buildAutoCommand()
    {
        final Side side = SideChooser.getSelected();
        final AutoMode autoMode = AutoModeChooser.getSelected();

        String autoName = autoMode.autoName + " " + side.autoName;

        return Optional.of(getPathPlannerAuto(autoName));
    }

}
