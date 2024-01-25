package frc.lib.modules.leds.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.modules.leds.Color;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LedSubsystem extends SubsystemBase
{

	private AddressableLED led;
	private AddressableLEDBuffer buffer;

	private static final int port = 0; // The PWM port of the LED strip. Setting to 0 for now.
	private int length; // How many "pixels" are on the strip, I think that the one we have on the
						// showbot has 300

	public LedSubsystem(int pixels)
	{

		this.length = pixels;

		buffer = new AddressableLEDBuffer(pixels);

		led = new AddressableLED(port);
		led.setLength(pixels);
		led.setData(buffer);
		led.start();

	}

	public void setAllPixels(Color color)
	{
		for (int i = 0; i < length; i++)
			buffer.setRGB(i, color.r, color.g, color.b);
		// This is better than using the setPixel method in the same class because it's not setting
		// the data every time.
		this.updateData();
	}

	public static int[] calcBlending(Color c1, Color c2, double currentFade)
	{
		int r = (int) (c1.r * currentFade / 1 + c2.r * (1 - currentFade) / currentFade);
		int g = (int) (c1.g * currentFade / 1 + c2.g * (1 - currentFade) / currentFade);
		int b = (int) (c1.b * currentFade / 1 + c2.b * (1 - currentFade) / currentFade);
		return new int[]
		{
				r, g, b
		};
	}

	public void setPixel(int pixelToSet, Color color)
	{
		if (color.hsv)
			buffer.setHSV(pixelToSet, color.h, color.s, color.v);
		else
			buffer.setRGB(pixelToSet, color.r, color.g, color.b);

		// THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY
	}

	public void updateData()
	{
		led.setData(buffer);
		System.out.println("Updated LED Data.");
	}

	public int getLength()
	{
		return length;
	}

    public static Color calcBlending(int i, int j, int k, int l, int m, int n, double progress)
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcBlending'");
    }

}
