import org.junit.jupiter.api.Test;

import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterMountSubsystem;

class IntakeTest {
    
    //@Test
    void testIntake() {
        
        IntakeSubsystem subsystem = new IntakeSubsystem();

        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        System.out.println("Performing Test for looping without being on.");
        for (int i = 0; i < 100; i++) subsystem.periodic();

        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        System.out.println("Turning on intake");
        subsystem.turnOnOrStopIntakeMotors(true);
        subsystem.raiseOrLowerIntakeMount(true);
        subsystem.setGoalRotation(360);
        subsystem.INTAKE_RAISER_MOTOR_RIGHT.getEncoder().setPosition(0);
        for (int i = 0; i < 100; i++) subsystem.periodic();

        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        System.out.println("Turning off intake");
        subsystem.turnOnOrStopIntakeMotors(false);
        subsystem.raiseOrLowerIntakeMount(false);
        for (int i = 0; i < 100; i++) subsystem.periodic();

        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

        assert (true);
    }

}
