package frc.robot.constants;

public class IntakeConstants
{
    public static final double DEGREES_IN_ONE_TICK = 360 / 42;

    public static final double INTAKE_REST_VELOCITY = 0;
    public static final double INTAKE_INTAKING_VELOCITY = 1;

    public static final double INTAKE_RETRACTED_ROTATION = 0;
    public static final double INTAKE_DEPLOYED_ROTATION = 10;

    public static final double INTAKE_DEPLOYMENT_KP = 0.1;
    public static final double INTAKE_DEPLOYMENT_KI = 0;
    public static final double INTAKE_DEPLOYMENT_KD = 0.1;
    public static final double INTAKE_DEPLOYMENT_KFF = 0;
    
    public static final double INTAKE_ROLLER_KP = 0.001;
    public static final double INTAKE_ROLLER_KI = 0;
    public static final double INTAKE_ROLLER_KD = 0;
    public static final double INTAKE_ROLLER_KFF = 0.01;
}
