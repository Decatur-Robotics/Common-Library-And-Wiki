import org.junit.jupiter.api.Test;

import frc.robot.subsystems.ShooterSubsystem;

class ShooterTest {
    

    // @Test
    void testShooter() {

        ShooterSubsystem subsystem = new ShooterSubsystem();

        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("Shooter Motor Velocity Error: " + subsystem.getShooterMotorVelocityError());
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");        
        System.out.println("Running ShooterSubsystem#periodic x50");
        for (int i = 0; i < 50; i++) {
            subsystem.periodic();
            System.out.println("Main: " + subsystem.shooterMotorMain.get() + " | Sub: " + subsystem.shooterMotorSub.get());
        }
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

    }
}
