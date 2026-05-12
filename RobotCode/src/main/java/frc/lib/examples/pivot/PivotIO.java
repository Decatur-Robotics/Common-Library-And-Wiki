package frc.lib.examples.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {

    @AutoLog
    class PivotIOInputs{
        public PivotIOData pivotData = new PivotIOData(false, false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }   public record PivotIOData(
        boolean mainMotorConnected,
        boolean followerMotorConnected,
        double mainMotorVoltage,
        double followerMotorVoltage,
        double mainMotorPosition,
        double followerMotorPosition,
        double encoderPosition,
        double mainMotorVelocity,
        double followerMotorVelocity,
        double mainMotorAcceleration,
        double followerMotorAcceleration,
        double mainMotorTemperature,
        double followerMotorTemperature
    ) {
    }

    default void updateInputs(PivotIOInputs inputs){}

    default void setVoltage(double voltage){}

    default void setPosition(double position){}

    default void periodic(){}

    default void coast(){}
} 