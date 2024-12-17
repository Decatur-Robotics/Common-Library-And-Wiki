package frc.lib.examples.tankdrives.revlib;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {
    
    private CANSparkMax motorRightFront, motorRightBack, motorLeftFront, motorLeftBack;
    private SparkPIDController rightPIDController, leftPIDController;

    public TankDriveSubsystem() {
        motorRightFront = new CANSparkMax(0, MotorType.kBrushless);
        motorRightBack = new CANSparkMax(0, MotorType.kBrushless);
        motorLeftFront = new CANSparkMax(0, MotorType.kBrushless);
        motorLeftBack = new CANSparkMax(0, MotorType.kBrushless);

        motorRightFront.setIdleMode(IdleMode.kBrake);
        motorRightBack.setIdleMode(IdleMode.kBrake);
        motorLeftFront.setIdleMode(IdleMode.kBrake);
        motorLeftBack.setIdleMode(IdleMode.kBrake);

        motorRightFront.setSmartCurrentLimit(TankDriveConstants.STATOR_CURRENT_LIMIT);
        motorRightBack.setSmartCurrentLimit(TankDriveConstants.STATOR_CURRENT_LIMIT);
        motorLeftFront.setSmartCurrentLimit(TankDriveConstants.STATOR_CURRENT_LIMIT);
        motorLeftBack.setSmartCurrentLimit(TankDriveConstants.STATOR_CURRENT_LIMIT);

        motorLeftFront.setInverted(true);

        rightPIDController = motorRightFront.getPIDController();
        leftPIDController = motorLeftFront.getPIDController();

        rightPIDController.setP(TankDriveConstants.kP);
        rightPIDController.setI(TankDriveConstants.kI);
        rightPIDController.setD(TankDriveConstants.kD);
        rightPIDController.setFF(TankDriveConstants.kFF);

        rightPIDController.setSmartMotionMaxVelocity(TankDriveConstants.CRUISE_VELOCITY, 0);
        rightPIDController.setSmartMotionMaxAccel(TankDriveConstants.ACCELERATION, 0);
        
        leftPIDController.setP(TankDriveConstants.kP);
        leftPIDController.setI(TankDriveConstants.kI);
        leftPIDController.setD(TankDriveConstants.kD);
        leftPIDController.setFF(TankDriveConstants.kFF);

        leftPIDController.setSmartMotionMaxVelocity(TankDriveConstants.CRUISE_VELOCITY, 0);
        leftPIDController.setSmartMotionMaxAccel(TankDriveConstants.ACCELERATION, 0);

        motorRightBack.follow(motorRightFront);
        motorLeftBack.follow(motorLeftFront);
    }

    public void setChassisSpeeds(double leftSpeed, double rightSpeed) {
        rightPIDController.setReference(rightSpeed, ControlType.kSmartVelocity);
        leftPIDController.setReference(leftSpeed, ControlType.kSmartVelocity);
    }

}
