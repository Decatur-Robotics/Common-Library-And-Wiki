package frc.robot.constants;

public class IntakeConstants {
    /** RPM */
    public static final double INTAKE_REST_VELOCITY = 0;
    /** RPM */
    public static final double INTAKE_DEPLOYED_VELOCITY = 10000;

    public static final double INTAKE_REVERSE_VELOCITY = -5000;

    public static final double INTAKE_RETRACTED_ROTATION = 0.1;
    /** Position intake must pass to allow spinning */
    public static final double INTAKE_SPIN_ROTATION = 0.3;
    public static final double INTAKE_DEPLOYED_ROTATION = 3.28;

    public static final double INTAKE_DEPLOYMENT_UP_KP = 0.5; // 0.5;
    public static final double INTAKE_DEPLOYMENT_UP_KI = 0;
    public static final double INTAKE_DEPLOYMENT_UP_KD = 0;
    public static final double INTAKE_DEPLOYMENT_UP_KFF = 0;

    public static final double INTAKE_DEPLOYMENT_DOWN_KP = 0.01;
    public static final double INTAKE_DEPLOYMENT_DOWN_KI = 0;
    public static final double INTAKE_DEPLOYMENT_DOWN_KD = 0;
    public static final double INTAKE_DEPLOYMENT_DOWN_KFF = 0;

    public static final double INTAKE_ROLLER_KP = 0;
    public static final double INTAKE_ROLLER_KI = 0;
    public static final double INTAKE_ROLLER_KD = 0;
    public static final double INTAKE_ROLLER_KFF = 0; // 0.05; 

	public static final int INTAKE_DEPLOYMENT_SLOT_UP = 0;
	public static final int INTAKE_DEPLOYMENT_SLOT_DOWN = 1;
}
