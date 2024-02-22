import org.junit.jupiter.api.Test;

import frc.robot.subsystems.ShooterMountSubsystem;

class ShooterMountTest {
    
    //@Test
    void testShooterMount() {
        
        ShooterMountSubsystem subsystem = new ShooterMountSubsystem();

        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("180 degrees = " + ShooterMountSubsystem.degreesToTicks(180) + " ticks");
        System.out.println("360 degrees = " + ShooterMountSubsystem.degreesToTicks(360) + " ticks");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        subsystem.setTargetRotation(180);
        System.out.println("Target Rotation: " + subsystem.targetRotation);
        System.out.println("Within aim tolerance: " + subsystem.withinAimTolerance());
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        assert (ShooterMountSubsystem.degreesToTicks(360) == 4096.0);
    }

}
