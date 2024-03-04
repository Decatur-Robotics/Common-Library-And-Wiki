package frc.robot.subsystems;

import frc.robot.RobotContainer;
import frc.robot.constants.Constants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.Ports;

import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkBase;

public class IntakeSubsystem extends SubsystemBase
{

    private TeamSparkBase intakeDeployMotorLeft, intakeDeployMotorRight, intakeRollerMotorTop;
    // intakeRollerMotorBottom;
    private double desiredRotation, desiredVelocity;
    private SparkPIDController intakeDeployPidController, intakeRollerPidController;

    public IntakeSubsystem()
    {
        intakeDeployMotorRight = new TeamSparkBase("Intake Deploy Motor Right",
                Ports.INTAKE_DEPLOY_MOTOR_RIGHT);
        intakeDeployMotorLeft = new TeamSparkBase("Intake Deploy Motor Left",
                Ports.INTAKE_DEPLOY_MOTOR_LEFT);
        intakeRollerMotorTop = new TeamSparkBase("Intake Roller Motor Top",
                Ports.INTAKE_ROLLER_MOTOR_TOP);
        // intakeRollerMotorBottom = new TeamSparkBase("Intake Roller Motor Bottom",
        // Ports.INTAKE_ROLLER_MOTOR_BOTTOM);

        intakeDeployMotorRight.setAllCanPeriodicFramePeriods(10000);
        intakeDeployMotorLeft.setAllCanPeriodicFramePeriods(10000);
        intakeRollerMotorTop.setAllCanPeriodicFramePeriods(10000);
        // intakeRollerMotorBottom.setAllCanPeriodicFramePeriods(10000);
        intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
        // intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
        intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
        intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);

        // Configure deployment motors
        intakeDeployMotorLeft.follow(intakeDeployMotorRight, true);
        intakeDeployMotorRight.setSmartCurrentLimit(Constants.NEO_550_MAX_CURRENT);
        intakeDeployMotorLeft.setSmartCurrentLimit(Constants.NEO_550_MAX_CURRENT);
        intakeDeployMotorRight.setIdleMode(IdleMode.kBrake);
        intakeDeployMotorLeft.setIdleMode(IdleMode.kBrake);

        // Configure deployment PID
        intakeDeployPidController = intakeDeployMotorRight.getPIDController();
        intakeDeployPidController.setP(IntakeConstants.INTAKE_DEPLOYMENT_KP, 0);
        intakeDeployPidController.setI(IntakeConstants.INTAKE_DEPLOYMENT_KI, 0);
        intakeDeployPidController.setD(IntakeConstants.INTAKE_DEPLOYMENT_KD, 0);
        intakeDeployPidController.setFF(IntakeConstants.INTAKE_DEPLOYMENT_KFF, 0);

        // Configure roller motors
        intakeRollerMotorTop.setInverted(true);
        // intakeRollerMotorBottom.follow(intakeRollerMotorTop, true);
        intakeRollerMotorTop.setSmartCurrentLimit(Constants.NEO_550_MAX_CURRENT);
        // intakeRollerMotorBottom.setSmartCurrentLimit(Constants.NEO_550_MAX_CURRENT);
        intakeRollerMotorTop.setIdleMode(IdleMode.kBrake);
        // intakeRollerMotorBottom.setIdleMode(IdleMode.kBrake);

        // Configure roller PID
        intakeRollerPidController = intakeRollerMotorTop.getPIDController();
        intakeRollerPidController.setP(IntakeConstants.INTAKE_ROLLER_KP, 0);
        intakeRollerPidController.setI(IntakeConstants.INTAKE_ROLLER_KI, 0);
        intakeRollerPidController.setD(IntakeConstants.INTAKE_ROLLER_KD, 0);
        intakeRollerPidController.setFF(IntakeConstants.INTAKE_ROLLER_KFF, 0);

        desiredRotation = IntakeConstants.INTAKE_RETRACTED_ROTATION;
        desiredVelocity = IntakeConstants.INTAKE_REST_VELOCITY;

        RobotContainer.getShuffleboardTab().addDouble("Actual Intake Velocity",
                () -> intakeRollerMotorTop.getVelocity());
        RobotContainer.getShuffleboardTab().addDouble("Desired Intake Velocity",
                () -> desiredVelocity);
        RobotContainer.getShuffleboardTab().addDouble("Actual Intake Rotation",
                () -> intakeDeployMotorRight.getCurrentEncoderValue());
        RobotContainer.getShuffleboardTab().addDouble("Desired Intake Rotation",
                () -> desiredRotation);
    }

    /** @param desiredRotation Ticks */
    public void setDesiredRotation(double desiredRotation)
    {
        this.desiredRotation = desiredRotation;
        intakeDeployPidController.setReference(desiredRotation, ControlType.kPosition, 0);
    }

    /** @param desiredVelocity Ticks per second */
    public void setDesiredVelocity(double desiredVelocity)
    {
        this.desiredVelocity = desiredVelocity;
        intakeRollerPidController.setReference(desiredVelocity, ControlType.kVelocity, 0);
    }

}
