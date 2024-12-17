package frc.lib.examples.tankdrives.phoenix;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {

    private TalonFX motorRightFront, motorRightBack, motorLeftFront, motorLeftBack;
    
    private MotionMagicVelocityDutyCycle leftControlRequest, rightControlRequest;
    
    public TankDriveSubsystem() {
        motorRightFront = new TalonFX(0);
        motorRightBack = new TalonFX(0);
        motorLeftFront = new TalonFX(0);
        motorLeftBack = new TalonFX(0);

        TalonFXConfiguration motorConfig = new TalonFXConfiguration();

        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        motorConfig.CurrentLimits.StatorCurrentLimit = TankDriveConstants.STATOR_CURRENT_LIMIT;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

        motorConfig.Slot0.kS = TankDriveConstants.kS;
        motorConfig.Slot0.kV = TankDriveConstants.kV;
        motorConfig.Slot0.kA = TankDriveConstants.kA;
        motorConfig.Slot0.kP = TankDriveConstants.kP;
        motorConfig.Slot0.kI = TankDriveConstants.kI;
        motorConfig.Slot0.kD = TankDriveConstants.kD;

        motorConfig.MotionMagic.MotionMagicCruiseVelocity = TankDriveConstants.CRUISE_VELOCITY;
        motorConfig.MotionMagic.MotionMagicAcceleration = TankDriveConstants.ACCELERATION;

        motorRightFront.getConfigurator().apply(motorConfig);
        motorRightBack.getConfigurator().apply(motorConfig);
        motorLeftFront.getConfigurator().apply(motorConfig);
        motorLeftBack.getConfigurator().apply(motorConfig);

        motorLeftFront.setInverted(true);

        motorRightBack.setControl(new Follower(motorRightFront.getDeviceID(), false));
        motorLeftBack.setControl(new Follower(motorLeftFront.getDeviceID(), false));

        leftControlRequest = new MotionMagicVelocityDutyCycle(0);
        rightControlRequest = new MotionMagicVelocityDutyCycle(0);
    }

    public void setChassisSpeeds(double leftSpeed, double rightSpeed) {
        motorLeftFront.setControl(leftControlRequest.withVelocity(leftSpeed));
        motorRightFront.setControl(rightControlRequest.withVelocity(rightSpeed));
    }

}
