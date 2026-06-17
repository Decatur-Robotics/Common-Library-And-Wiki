package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.AutoLog;

import frc.robot.subsystems.superstructure.elevator.ElevatorConstants;

public interface ClimberIO {

    

    @AutoLog
    class ClimberIOInputs {//apparently the 6 values are intial values for some logging data
        public ClimberIOData data = new ClimberIOData(false, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    record ClimberIOData(
        Boolean climberMotorConnected,
        Double position,
        Double voltage,
        Double velocity,
        Double supplyCurrent,
        Double torqueAmps
    ){


    }

    default void updateInputs(ClimberIOInputs inputs) {

    }

    default void setVoltage(double voltage) {

    }

    default void stop(){};

    default void runPosition(double position, double feedforward) {
    }

    default void setPID(ClimberConstants constants){

    }

    
}
