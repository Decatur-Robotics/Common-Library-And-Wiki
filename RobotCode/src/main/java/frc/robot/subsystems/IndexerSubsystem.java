package frc.robot.subsystems;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;

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
	private TeamSparkBase indexerMotorMain, indexerMotorSub;

	// private DigitalInput beamBreak;

	public IndexerSubsystem()
	{
		desiredIndexerVelocity = IndexerConstants.INDEXER_REST_VELOCITY;

		// beamBreak = new DigitalInput(Ports.BEAM_BREAK);

		indexerMotorMain = new TeamSparkBase("Left Shooter Motor Sub", Ports.INDEXER_MOTOR_RIGHT);
		indexerMotorSub = new TeamSparkBase("Right Shooter Motor Sub", Ports.INDEXER_MOTOR_LEFT);

		indexerMotorSub.follow(indexerMotorMain, true);

		indexerMotorMain.enableVoltageCompensation(Constants.MAX_VOLTAGE);
		indexerMotorSub.enableVoltageCompensation(Constants.MAX_VOLTAGE);
		indexerMotorMain.setIdleMode(IdleMode.kBrake);
		indexerMotorSub.setIdleMode(IdleMode.kBrake);
		indexerMotorMain.setSmartCurrentLimit(Constants.MAX_CURRENT);
		indexerMotorSub.setSmartCurrentLimit(Constants.MAX_CURRENT);

		indexerPid = indexerMotorMain.getPidController();

		indexerPid.setP(IndexerConstants.INDEXER_KP);
		indexerPid.setI(IndexerConstants.INDEXER_KI);
		indexerPid.setD(IndexerConstants.INDEXER_KD);
		indexerPid.setFF(IndexerConstants.INDEXER_KF);

		RobotContainer.getShuffleboardTab().addDouble("Actual Indexer Velocity",
				() -> indexerMotorMain.getVelocity());
		RobotContainer.getShuffleboardTab().addDouble("Desired Indexer Velocity",
				() -> desiredIndexerVelocity);
	}

	public void setIndexerMotorVelocity(double desiredIndexerVelocity, String reason)
	{
		this.desiredIndexerVelocity = Math.max(
				Math.min(IndexerConstants.INDEXER_MAX_VELOCITY, desiredIndexerVelocity),
				-IndexerConstants.INDEXER_MAX_VELOCITY);
	}

	@Override
	public void periodic()
	{
		indexerPid.setReference(desiredIndexerVelocity, ControlType.kVelocity);
	}

	public boolean hasNote()
	{
		// return beamBreak.get();

		return false; // For testing without beam break, temporary
	}

}
