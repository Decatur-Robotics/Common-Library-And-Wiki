package frc.robot.subsystems;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.RobotContainer;
import frc.robot.constants.Ports;
import frc.robot.constants.ShooterMountConstants;

public class ShooterMountSubsystem extends SubsystemBase
{

	private TeamTalonFX mainMotor, followMotor;

	private double targetRotation; // In degrees

	private boolean autoAim;

	private Pose2d shooterMountPose;

	public ShooterMountSubsystem()
	{
		mainMotor = new TeamTalonFX("SHOOTER_MOUNT_MOTOR_LEFT", Ports.SHOOTER_MOUNT_MOTOR_LEFT);
		followMotor = new TeamTalonFX("SHOOTER_MOUNT_MOTOR_RIGHT", Ports.SHOOTER_MOUNT_MOTOR_RIGHT);

		followMotor.follow(mainMotor);
		followMotor.setInverted(true);

		mainMotor.config_kP(0, ShooterMountConstants.SHOOTER_MOUNT_KP);
		mainMotor.config_kI(0, ShooterMountConstants.SHOOTER_MOUNT_KI);
		mainMotor.config_kD(0, ShooterMountConstants.SHOOTER_MOUNT_KD);
		mainMotor.config_kF(0, ShooterMountConstants.SHOOTER_MOUNT_KF);
		mainMotor.configMotionCruiseVelocity(ShooterMountConstants.SHOOTER_MOUNT_CRUISE_VELOCITY);
		mainMotor.configMotionAcceleration(ShooterMountConstants.SHOOTER_MOUNT_ACCELERATION);
		mainMotor.selectProfileSlot(0, 0);

		targetRotation = 0;

		autoAim = false;
		shooterMountPose = new Pose2d();
	}

	@Override
	public void periodic()
	{
		

		if (autoAim)
		{
			shooterMountPose = /* RobotContainer.getVision().getShooterMountPose() */ new Pose2d();



		}
	}

	public void setTargetRotation(double targetRotation)
	{
		this.targetRotation = targetRotation;
	}

	private static double degreesToTicks(double degrees)
	{
		return degrees * ShooterMountConstants.TICKS_IN_ONE_DEGREE;
	}

	public void setAutoAim(boolean autoAim)
	{
		this.autoAim = autoAim;
	}

}