import org.junit.jupiter.api.Test;

import frc.lib.modules.leds.Color;
import frc.lib.modules.leds.NamedColor;
import frc.robot.subsystems.LedSubsystem;

class LedTest {
    
    // @Test
    void testLeds() {

        // LedSubsystem subsystem = new LedSubsystem(1);

        Color color1 = NamedColor.YELLOW;
        Color color2 = NamedColor.BLUE;

        
        
        for (double progress = 0.0; progress < 1.00; progress += 0.02) {
            Color out = LedSubsystem.calcBlending(color1, color2, progress);
            System.out.print(out.r + ", " + out.g + ", " + out.b + "|");
        }

    }

}
