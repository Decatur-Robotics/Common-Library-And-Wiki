package frc.robot.constants;

public class IntakeConstants {
    /** RPM */
    public static final double INTAKE_REST_VELOCITY = 0;
    /** RPM */
    public static final double INTAKE_DEPLOYED_VELOCITY = 10000;

    public static final double INTAKE_REVERSE_VELOCITY = -5000;

    public static final double INTAKE_RETRACTED_ROTATION = 180;
    /** Position intake must pass to allow spinning */
    public static final double INTAKE_SPIN_ROTATION = 1000;
    public static final double INTAKE_DEPLOYED_ROTATION = 3000;

    public static final double INTAKE_RETRACT_KP = 0.0003;
    public static final double INTAKE_RETRACT_KI = 0;
    public static final double INTAKE_RETRACT_KD = 0.00003;
    public static final double INTAKE_RETRACT_KV = 0.00015;
    public static final double INTAKE_RETRACT_KG = -0.106;
    public static final double INTAKE_RETRACT_CRUISE_VELOCITY = 4000;
    public static final double INTAKE_RETRACT_MAX_ACCELERATION = 6000;

    public static final double INTAKE_ROLLER_KP = 0;
    public static final double INTAKE_ROLLER_KI = 0;
    public static final double INTAKE_ROLLER_KD = 0;
    public static final double INTAKE_ROLLER_KFF = 0.05; 

	public static final int INTAKE_DEPLOYMENT_SLOT_UP = 0;
	public static final int INTAKE_DEPLOYMENT_SLOT_DOWN = 1;
}
