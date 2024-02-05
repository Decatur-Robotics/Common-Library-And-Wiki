package frc.lib.modules.swervedrive.Commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import frc.lib.modules.swervedrive.SwerveDriveSubsystem;
import frc.robot.constants.VisionConstants;
import frc.robot.subsystems.VisionSubsystem;

public class AutoAimSwerveCommand extends TeleopSwerveCommand
{

    private final VisionSubsystem Vision;

    public AutoAimSwerveCommand(SwerveDriveSubsystem swerve, VisionSubsystem vision,
            DoubleSupplier translationSup, DoubleSupplier strafeSup,
            BooleanSupplier slowSpeedSupplier, BooleanSupplier fastSpeedSupplier)
    {
        super(swerve, translationSup, strafeSup, vision::getRotationToSpeaker, slowSpeedSupplier,
                fastSpeedSupplier);
        Vision = vision;
    }

    @Override
    public boolean isFinished()
    {
        return Math.abs(Vision.getRotationToSpeaker()) < VisionConstants.CHASSIS_AIM_THRESHOLD;
    }

    @Override
    public void end(boolean interrupted)
    {

    }

}
