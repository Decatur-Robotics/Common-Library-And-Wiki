package frc.lib.modules.swervedrive.Commands;

import frc.lib.core.ILogSource;
import frc.lib.modules.swervedrive.SwerveConstants;
import frc.lib.modules.swervedrive.SwerveDriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.logging.Level;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class TeleopSwerveCommand extends Command implements ILogSource
{
	private SwerveDriveSubsystem s_Swerve;
	private DoubleSupplier translationSup;
	private DoubleSupplier strafeSup;
	private DoubleSupplier rotationSup;
	private BooleanSupplier slowSpeedSupplier;

	public TeleopSwerveCommand(SwerveDriveSubsystem s_Swerve, DoubleSupplier translationSup,
			DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier slowSpeedSupplier)
	{
		this.s_Swerve = s_Swerve;
		addRequirements(s_Swerve);

		this.translationSup = translationSup;
		this.strafeSup = strafeSup;
		this.rotationSup = rotationSup;

		this.slowSpeedSupplier = slowSpeedSupplier;
	}

	private double getSpeed()
	{
		if (slowSpeedSupplier.getAsBoolean())
			return SwerveConstants.SLOW_SPEED;
		return SwerveConstants.NORMAL_SPEED;
	}

	@Override
	public void execute()
	{
		double speed = getSpeed();

		/* Get Values, Deadband */
		double translationVal = Math.pow(MathUtil.applyDeadband(translationSup.getAsDouble(), SwerveConstants.JOYSTICK_DEADBAND), 3);
		double strafeVal = Math.pow(MathUtil.applyDeadband(strafeSup.getAsDouble(), SwerveConstants.JOYSTICK_DEADBAND), 3);
		double rotationVal = rotationSup.getAsDouble() * speed;

		SmartDashboard.putNumber("Swerve Speed", speed);

		/* Drive */
		s_Swerve.drive(
				new Translation2d(translationVal, strafeVal)
						.times(speed),
				rotationVal, true, // field relative is
									// always on
				false);
	}
}