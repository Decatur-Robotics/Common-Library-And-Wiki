package frc.lib.modules.swervedrive.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;

public class RotateToAngleCommand extends Command
{

    private final SwerveDriveSubsystem SwerveDriveSubsystem;

    /** In degrees */
    private DoubleSupplier getAngle;
    private double origDistance;

    private static final double ANGLE_DEADBAND = 2;

    private static final Translation2d Zero = new Translation2d(0, 0);

    /**
     * @param getAngle in degrees
     */
    public RotateToAngleCommand(SwerveDriveSubsystem SwerveDriveSubsystem, DoubleSupplier getAngle)
    {
        this.SwerveDriveSubsystem = SwerveDriveSubsystem;
        this.getAngle = getAngle;

        origDistance = getAngle.getAsDouble()
                - SwerveDriveSubsystem.getPose().getRotation().getDegrees();

        addRequirements(SwerveDriveSubsystem);
    }

    /**
     * @param angle in degrees
     */
    public RotateToAngleCommand(SwerveDriveSubsystem SwerveDriveSubsystem, double angle)
    {
        this(SwerveDriveSubsystem, () -> angle);
    }

    @Override
    public void execute()
    {
        double targetRotation = getAngle.getAsDouble(), distance = targetRotation
                - SwerveDriveSubsystem.getPose().getRotation().getDegrees();

        // Formula pulled from the shooter mount ramping
        double rotation = origDistance * Math.sin(distance * Math.PI / origDistance);
        SwerveDriveSubsystem.drive(Zero, rotation);
    }

    @Override
    public boolean isFinished()
    {
        return Math.abs(getAngle.getAsDouble()
                - SwerveDriveSubsystem.getPose().getRotation().getDegrees()) < ANGLE_DEADBAND;
    }

}
