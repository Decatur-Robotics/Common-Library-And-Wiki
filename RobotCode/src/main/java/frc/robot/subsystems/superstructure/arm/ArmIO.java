package frc.robot.subsystems.superstructure.arm;

import org.littletonrobotics.junction.AutoLog;

import frc.robot.subsystems.superstructure.elevator.ElevatorConstants;
import frc.robot.subsystems.superstructure.elevator.ElevatorIO.ElevatorIOInputs;

public interface ArmIO {
    @AutoLog
    class ArmIOInputs{
        public ArmIOData data = new ArmIOData(false,0.0,0.0,0.0,0.0,0.0);
    }
    record ArmIOData(
    boolean motorConnected,
    double voltage,
    double position,
    double velocity,
    double supplyAmps,
    double torqueCurrent
    ){}; 
    default void updateInputs(ArmIOInputs inputs) {
    }

    default void setVoltage(double voltage) {
    }

   default void stop(){}

   default void runPosition(double position, double feedForward) {
   }

    default void setPID(ArmConstants constants) {
    }
} 
