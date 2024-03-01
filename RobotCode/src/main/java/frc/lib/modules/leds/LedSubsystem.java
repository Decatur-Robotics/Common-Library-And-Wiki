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
    private static final int PORT = 0;
    /** How many pixels are on the strip */
    private int LENGTH;

    /**
     * @param length in pixels
     */
    public LedSubsystem(int length)
    {
        LENGTH = length;

        buffer = new AddressableLEDBuffer(length);

        led = new AddressableLED(PORT);
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
        updateData();
    }

    /**
     * @param from        the color to fade from
     * @param to          the color to fade to
     * @param currentFade a value between 0 and 1. 0 is the from color, 1 is the to color.
     * @return the blended color
     */
    @SuppressWarnings("unused")
    private static Color blendColors(Color from, Color to, double currentFade)
    {
        int r = (int) (from.red * currentFade + to.red * (1 - currentFade));
        int g = (int) (from.green * currentFade + to.green * (1 - currentFade));
        int b = (int) (from.blue * currentFade + to.blue * (1 - currentFade));

        return new Color(r, g, b);
    }

    /** THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY! */
    public void setPixel(int pixelIndex, Color color)
    {
        buffer.setRGB(pixelIndex, (int) (color.red * 255), (int) (color.green * 255),
                (int) (color.blue * 255));
    }

    /** THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY! */
    public void setPixelRgb(int pixelIndex, int r, int g, int b)
    {
        buffer.setRGB(pixelIndex, r, g, b);
    }

    /** THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY! */
    public void setPixelHsv(int pixelIndex, int h, int s, int v)
    {
        buffer.setHSV(pixelIndex, h, s, v);
    }

    /** Updates the LEDs */
    public void updateData()
    {
        led.setData(buffer);
        logFiner("Updated LED Data.");
    }

    /**
     * @return length in pixels
     */
    public int getLength()
    {
        return LENGTH;
    }

}