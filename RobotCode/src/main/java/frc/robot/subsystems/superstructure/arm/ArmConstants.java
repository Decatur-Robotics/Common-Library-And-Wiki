package frc.robot.subsystems.superstructure.arm;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;

public class ArmConstants {
    public static final double kP = 8.0;
    public static final double kI = 0.0;
    public static final double kD= 0.0;
    public static final double kS = 0.081;
    public static final double kV = 7.8;
    public static final double kA = 0.0;
    public static final double kG = 0.518;
 
    public static final double STOWED_POSITION = 0;

    // Intaking positions
    public static final double CORAL_GROUND_INTAKING_POSITION = -0.103; // -0.096
    public static final double CORAL_HUMAN_PLAYER_INTAKING_POSITION = 0.231;
    public static final double ALGAE_GROUND_INTAKING_POSITION = 0;
    public static final double ALGAE_LOW_REEF_INTAKING_POSITION = 0.08;
    public static final double ALGAE_HIGH_REEF_INTAKING_POSITION = ALGAE_LOW_REEF_INTAKING_POSITION;

    // Scoring positions
    public static final double L1_SCORING_POSITION = 0.11;
    public static final double L2_STAGING_POSITION = STOWED_POSITION;
    public static final double L2_SCORING_POSITION = 0.14;
    public static final double L3_STAGING_POSITION = L2_STAGING_POSITION;
    public static final double L3_SCORING_POSITION = L2_SCORING_POSITION;
    public static final double L4_STAGING_POSITION = STOWED_POSITION;
    public static final double L4_SCORING_POSITION = 0.12;
    public static final double PROCESSOR_POSITION = 0.15;
    public static final double NET_POSITION = 0.15;

    public static final CANcoderConfiguration ENCODER_CONFIG = new CANcoderConfiguration()
    .withMagnetSensor(new MagnetSensorConfigs().withMagnetOffset(0.008));
}
