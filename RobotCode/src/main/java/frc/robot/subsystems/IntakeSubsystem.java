package frc.robot.subsystems;

import frc.robot.RobotContainer;
import frc.robot.constants.Constants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.Ports;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.FaultID;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkLowLevel.PeriodicFrame;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

	private CANSparkMax intakeDeployMotorLeft, intakeDeployMotorRight, intakeRollerMotorTop,
			intakeRollerMotorBottom;
	private double desiredRotation, desiredVelocity;
	private SparkPIDController intakeDeployPidController, intakeRollerPidController;
	private RelativeEncoder intakeDeployEncoderRight;

	public IntakeSubsystem() {
		intakeDeployMotorRight = new CANSparkMax(Ports.INTAKE_DEPLOY_MOTOR_RIGHT, MotorType.kBrushless);
		intakeDeployMotorLeft = new CANSparkMax(Ports.INTAKE_DEPLOY_MOTOR_LEFT, MotorType.kBrushless);
		intakeRollerMotorTop = new CANSparkMax(Ports.INTAKE_ROLLER_MOTOR_TOP, MotorType.kBrushless);
		intakeRollerMotorBottom = new CANSparkMax(Ports.INTAKE_ROLLER_MOTOR_BOTTOM, MotorType.kBrushless);

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
		intakeRollerMotorBottom.follow(intakeRollerMotorTop, true);
		intakeRollerMotorTop.setSmartCurrentLimit(Constants.NEO_550_MAX_CURRENT);
		intakeRollerMotorBottom.setSmartCurrentLimit(Constants.NEO_550_MAX_CURRENT);
		intakeRollerMotorTop.setIdleMode(IdleMode.kBrake);
		intakeRollerMotorBottom.setIdleMode(IdleMode.kBrake);

		// Configure roller PID
		intakeRollerPidController = intakeRollerMotorTop.getPIDController();
		intakeRollerPidController.setP(IntakeConstants.INTAKE_ROLLER_KP, 0);
		intakeRollerPidController.setI(IntakeConstants.INTAKE_ROLLER_KI, 0);
		intakeRollerPidController.setD(IntakeConstants.INTAKE_ROLLER_KD, 0);
		intakeRollerPidController.setFF(IntakeConstants.INTAKE_ROLLER_KFF, 0);

		desiredRotation = IntakeConstants.INTAKE_RETRACTED_ROTATION;
		desiredVelocity = IntakeConstants.INTAKE_REST_VELOCITY;

		RobotContainer.getShuffleboardTab().addDouble("Actual Intake Velocity",
				() -> intakeRollerMotorTop.getEncoder().getVelocity());
		RobotContainer.getShuffleboardTab().addDouble("Desired Intake Velocity",
				() -> desiredVelocity);
		RobotContainer.getShuffleboardTab().addDouble("Actual Intake Rotation",
				() -> intakeDeployEncoderRight.getPosition());
		RobotContainer.getShuffleboardTab().addDouble("Desired Intake Rotation",
				() -> desiredRotation);
	}

	@Override
	public void periodic() {
		if (intakeDeployMotorLeft.getStickyFault(FaultID.kHasReset)
				|| intakeDeployMotorRight.getStickyFault(FaultID.kHasReset)
				|| intakeRollerMotorBottom.getStickyFault(FaultID.kHasReset)
				|| intakeRollerMotorTop.getStickyFault(FaultID.kHasReset)) {
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10000);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 10000);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 10000);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 10000);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 10000);
			intakeRollerMotorTop.setPeriodicFramePeriod(PeriodicFrame.kStatus7, 10000);

			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10000);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 10000);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 10000);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 10000);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 10000);
			intakeRollerMotorBottom.setPeriodicFramePeriod(PeriodicFrame.kStatus7, 10000);

			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10000);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 10000);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 10000);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 10000);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 10000);
			intakeDeployMotorLeft.setPeriodicFramePeriod(PeriodicFrame.kStatus7, 10000);

			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus0, 20);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus1, 20);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus2, 10000);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus3, 10000);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus4, 10000);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus5, 10000);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus6, 10000);
			intakeDeployMotorRight.setPeriodicFramePeriod(PeriodicFrame.kStatus7, 10000);
		}
	}

	/** @param desiredRotation Ticks */
	public void setDesiredRotation(double desiredRotation) {
		this.desiredRotation = desiredRotation;
		intakeDeployPidController.setReference(desiredRotation, ControlType.kPosition, 0);
	}

	/** @param desiredVelocity Ticks per second */
	public void setDesiredVelocity(double desiredVelocity) {
		this.desiredVelocity = desiredVelocity;
		intakeRollerPidController.setReference(desiredVelocity, ControlType.kVelocity, 0);
	}

}
