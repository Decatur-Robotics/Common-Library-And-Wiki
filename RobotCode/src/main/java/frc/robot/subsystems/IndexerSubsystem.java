package frc.robot.subsystems;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.lib.core.motors.TeamSparkBase;
import frc.robot.constants.Constants;
import frc.robot.constants.IndexerConstants;
import frc.robot.constants.Ports;

public class IndexerSubsystem extends SubsystemBase
{

	private double desiredIndexerVelocity;

	private SparkPIDController indexerPid;
	private TeamSparkBase indexerMotorRight, indexerMotorLeft;

	// private DigitalInput beamBreak;

	public IndexerSubsystem()
	{
		desiredIndexerVelocity = IndexerConstants.INDEXER_REST_VELOCITY;

		// beamBreak = new DigitalInput(Ports.BEAM_BREAK);
		indexerMotorRight = new TeamSparkBase("Left Shooter Motor Sub", Ports.INDEXER_MOTOR_RIGHT);
		indexerMotorLeft = new TeamSparkBase("Right Shooter Motor Sub", Ports.INDEXER_MOTOR_LEFT);

		indexerMotorRight.setInverted(true);
		indexerMotorLeft.follow(indexerMotorRight, true);

		indexerMotorRight.enableVoltageCompensation(Constants.MAX_VOLTAGE);
		indexerMotorLeft.enableVoltageCompensation(Constants.MAX_VOLTAGE);
		indexerMotorRight.setIdleMode(IdleMode.kBrake);
		indexerMotorLeft.setIdleMode(IdleMode.kBrake);
		indexerMotorRight.setSmartCurrentLimit(Constants.NEO_MAX_CURRENT);
		indexerMotorLeft.setSmartCurrentLimit(Constants.NEO_MAX_CURRENT);

		indexerPid = indexerMotorRight.getPIDController();

		indexerPid.setP(IndexerConstants.INDEXER_KP);
		indexerPid.setI(IndexerConstants.INDEXER_KI);
		indexerPid.setD(IndexerConstants.INDEXER_KD);
		indexerPid.setFF(IndexerConstants.INDEXER_KF);

		indexerMotorRight.setAllCanPeriodicFramePeriods(10000);
		indexerMotorLeft.setAllCanPeriodicFramePeriods(10000);
		indexerMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
		indexerMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
		indexerMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);

		RobotContainer.getShuffleboardTab().addDouble("Actual Indexer Velocity",
				() -> indexerMotorRight.getEncoder().getVelocity());
		RobotContainer.getShuffleboardTab().addDouble("Desired Indexer Velocity",
				() -> desiredIndexerVelocity);
	}

	public void setIndexerMotorVelocity(double desiredIndexerVelocity, String reason)
	{
		this.desiredIndexerVelocity = Math.max(
				Math.min(IndexerConstants.INDEXER_MAX_VELOCITY, desiredIndexerVelocity),
				-IndexerConstants.INDEXER_MAX_VELOCITY);

		indexerPid.setReference(this.desiredIndexerVelocity, ControlType.kVelocity);
	}

	public boolean hasNote()
	{
		// return beamBreak.get();

		return false; // For testing without beam break, temporary
	}

}
