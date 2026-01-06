package frc.robot.subsystems.superstructure.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
    @AutoLog
    class IntakeIOInputs {
        public IntakeIOData intakeData = new IntakeIOData(false,false,0.0,0.0,0.0,0.0);
    }

    record IntakeIOData(
        Boolean rightMotorConnected,
        Boolean leftMotorConnected,
        Double rightMotorVoltage,
        Double rightMotorVelocity,
        Double leftMotorVoltage,
        Double leftMotorVelocity
    ){}

    default void updateInputs(IntakeIOInputs inputs) {
    }

    default void setVoltage(double voltage) {
    
    }
    default void runOpenLoop(double output){
        
    }
    default void stop(){}
    
    default void setPID(IntakeConstants constants){
    }
    
}
