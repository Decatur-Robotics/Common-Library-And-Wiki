package frc.lib.modules.leds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.ILogSource;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class LedSubsystem extends SubsystemBase implements ILogSource
{

    private AddressableLED led;
    private AddressableLEDBuffer buffer;

    /** The PWM port of the LED strip. */
    private static final int port = 0;
    /** How many "pixels" are on the strip, I think that the one we have on the showbot has 300 */
    private int LENGTH;

    /**
     * @param length in pixels
     */
    public LedSubsystem(int length)
    {
        this.LENGTH = length;

        buffer = new AddressableLEDBuffer(length);

        led = new AddressableLED(port);
        led.setLength(length);
        updateData();
        led.start();
    }

    /** Does update buffer. */
    public void setAllPixels(Color color)
    {
        setAllPixels((int) (color.red * 255), (int) (color.green * 255), (int) (color.blue * 255));
    }

    /** Does update buffer. */
    public void setAllPixels(int r, int g, int b)
    {
        for (int i = 0; i < LENGTH; i++)
            buffer.setRGB(i, r, g, b);
        // This is better than using the setPixel method in the same class because it's not setting
        // the data every time.
        this.updateData();
    }

    @SuppressWarnings("unused")
    private static Color calcBlending(Color color1, Color color2, double currentFade)
    {
        int[] rgb = calcBlending((int) (color1.red * 255), (int) (color1.green * 255),
                (int) (color1.blue * 255), (int) (color2.red * 255), (int) (color2.green * 255),
                (int) (color2.blue * 255), currentFade);

        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    @SuppressWarnings("unused")
    private static int[] calcBlending(int r1, int g1, int b1, int r2, int g2, int b2,
            double currentFade)
    {
        int r = (int) (r1 * currentFade / 1 + r2 * (1 - currentFade) / currentFade);
        int g = (int) (g1 * currentFade / 1 + g2 * (1 - currentFade) / currentFade);
        int b = (int) (b1 * currentFade / 1 + b2 * (1 - currentFade) / currentFade);

        return new int[]
        {
                r, g, b
        };
    }

    /** THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY! */
    public void setPixel(int pixelToSet, Color color)
    {
        buffer.setRGB(pixelToSet, (int) (color.red * 255), (int) (color.green * 255),
                (int) (color.blue * 255));
    }

    /** THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY! */
    public void setPixelRGB(int pixelToSet, int r, int g, int b)
    {
        buffer.setRGB(pixelToSet, r, g, b);
    }

    /** THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY! */
    public void setPixelHSV(int pixelToSet, int h, int s, int v)
    {
        buffer.setHSV(pixelToSet, h, s, v);
    }

    public void updateData()
    {
        led.setData(buffer);
        logFiner("Updated LED Data.");
    }

    public int getLENGTH()
    {
        return LENGTH;
    }

}