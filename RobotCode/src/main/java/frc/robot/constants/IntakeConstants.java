package frc.robot.constants;

public class IntakeConstants {
    /** RPM */
    public static final double INTAKE_REST_VELOCITY = 0;
    /** RPM */
    public static final double INTAKE_DEPLOYED_VELOCITY = 10000;

    public static final double INTAKE_REVERSE_VELOCITY = -5000;

    public static final double INTAKE_RETRACTED_ROTATION = 360;
    /** Position intake must pass to allow spinning */
    public static final double INTAKE_SPIN_ROTATION = 1000;
    public static final double INTAKE_DEPLOYED_ROTATION = 2898;

    public static final double INTAKE_RETRACT_KP = 0.0002;
    public static final double INTAKE_RETRACT_KI = 0;
    public static final double INTAKE_RETRACT_KD = 0.00001;

    public static final double INTAKE_DEPLOYMENT_KP = 0.00008;
    public static final double INTAKE_DEPLOYMENT_KI = 0;
    public static final double INTAKE_DEPLOYMENT_KD = 0.00001;

    public static final double INTAKE_ROLLER_KP = 0;
    public static final double INTAKE_ROLLER_KI = 0;
    public static final double INTAKE_ROLLER_KD = 0;
    public static final double INTAKE_ROLLER_KFF = 0.05; 

	public static final int INTAKE_DEPLOYMENT_SLOT_UP = 0;
	public static final int INTAKE_DEPLOYMENT_SLOT_DOWN = 1;
}
