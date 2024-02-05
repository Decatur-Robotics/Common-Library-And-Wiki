package frc.robot.subsystems;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;
import frc.robot.constants.Constants;
import frc.robot.constants.FeederConstants;
import frc.robot.constants.Ports;

public class FeederSubsystem extends SubsystemBase
{

    private double desiredFeederVelocity;

    private SparkPIDController feederPid;
    private TeamSparkMAX feederMotorMain, feederMotorSub;

    public FeederSubsystem()
    {
        desiredFeederVelocity = FeederConstants.FEEDER_REST_VELOCITY;

        feederMotorMain = new TeamSparkMAX("Left Shooter Motor Sub", Ports.FEEDER_MOTOR_MAIN);
        feederMotorSub = new TeamSparkMAX("Right Shooter Motor Sub", Ports.FEEDER_MOTOR_SUB);

        feederMotorSub.follow(feederMotorMain, true);

        feederMotorMain.enableVoltageCompensation(Constants.MAX_VOLTAGE);
        feederMotorSub.enableVoltageCompensation(Constants.MAX_VOLTAGE);
        feederMotorMain.setIdleMode(IdleMode.kBrake);
        feederMotorSub.setIdleMode(IdleMode.kBrake);
        feederMotorMain.setSmartCurrentLimit(Constants.MAX_CURRENT);
        feederMotorSub.setSmartCurrentLimit(Constants.MAX_CURRENT);

        feederPid = feederMotorMain.getPidController();

        feederPid.setP(FeederConstants.FEEDER_KP);
        feederPid.setI(FeederConstants.FEEDER_KI);
        feederPid.setD(FeederConstants.FEEDER_KD);
        feederPid.setFF(FeederConstants.FEEDER_KF);
        feederPid.setOutputRange(-1, 1);
    }

    public void setFeederMotorPower(double desiredFeederVelocity)
    {
        this.desiredFeederVelocity = desiredFeederVelocity;
    }

    public void periodic()
    {

    }

}
