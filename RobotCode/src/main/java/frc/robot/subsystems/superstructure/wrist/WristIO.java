package frc.robot.subsystems.superstructure.wrist;

import org.littletonrobotics.junction.AutoLog;

public interface WristIO {

    @AutoLog
    class WristIOInputs {
        public WristIOData data = new WristIOData(0, 0, 0, 0, 0, false);
    }

    record WristIOData(
        double positionRad,
        double velocityRadPerSec,
        double appliedVolts,
        double torqueCurrentAmps,
        double supplyCurrentAmps,
        boolean connected
        ) {}

    default void updateInputs(WristIOInputs inputs){};

    default void setCurrent(double current){};
    
    default void setVolts(double volts){};

    default void stop(){};

    default void runPosition(double positionRad, double feedforwardVolts){};

    default void setBrakeMode(boolean enabled){};

}
