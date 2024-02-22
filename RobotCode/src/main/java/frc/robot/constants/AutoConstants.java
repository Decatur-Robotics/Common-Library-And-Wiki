package frc.robot.constants;

import frc.robot.commands.RotateShooterMountToPositionCommand;

public final class AutoConstants
{

    /**
     * The shooter mount rotations (in degrees) to be registered as named commands for PathPlanner.
     * Passed to {@link RotateShooterMountToPositionCommand}.
     */
    public static final double[] AutoShooterMountRotations =
    {
            20, 40, 60
    };

}
