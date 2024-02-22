package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.ILogSource;
import frc.lib.modules.leds.Color;

import java.util.logging.Level;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LedSubsystem extends SubsystemBase implements ILogSource
{

	private AddressableLED led;
	private AddressableLEDBuffer buffer;

	// Should be set when setAllPixels is called.
	public Color lastColor = new Color(255, 255, 255);
	public double progress = 1.0;

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
		setAllPixels(color, true);
	}

	public void setAllPixels(Color color, boolean reset)
	{
		for (int i = 0; i < length; i++)
			buffer.setRGB(i, color.r, color.g, color.b);
		// This is better than using the setPixel method in the same class because it's not setting
		// the data every time.
		this.updateData();
		if (reset)
		{
			this.lastColor = color;
			this.progress = 1.0;
		}
		log(Level.FINEST,
				"Set color to " + (color.hsv ? "hsv value " : "rgb value ")
						+ (color.hsv ? 
							color.h + ", " + color.s + ", " + color.v : 
							color.r + ", " + color.g + ", " + color.b	));
	}
	
	/**
	 * 
	 * @return Returns a blend of two given colors.
	 * @param progress The progress made of transitioning between the two colors. Ranges from 0.0-1.0.
	 *  */
	public static Color calcBlending(Color color1, Color color2, double progress)
	{
		progress = Math.min(1, Math.max(0, progress));
		int red =   (int) (color1.r * (1 - progress) + color2.r * progress);
		int green = (int) (color1.g * (1 - progress) + color2.g * progress);
		int blue =  (int) (color1.b * (1 - progress) + color2.b * progress);
		return new Color(red, green, blue);
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

		log(Level.FINEST, "Updated the LED Data.");
	}


	/**
	 *
	 * @return The amonut of LEDs, or "pixels" on the LED strip
	 */
	public int getLength()
	{
		return length;
	}

}
