package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.modules.leds.Color;
import frc.robot.constants.LedConstants;
import frc.robot.constants.Ports;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LedSubsystem extends SubsystemBase {

	private AddressableLED led;
	private AddressableLEDBuffer buffer;

	// Should be set when setAllPixels is called.
	public Color lastColor = new Color(255, 255, 255);
	public double progress = 1.0;

	private int length; // How many "pixels" are on the strip, I think that the one we have on the
						// showbot has 300

	private int numFlashes;
	private int timesFlashed;
	private boolean on;
	private Color offColor;
	private int periodsPassed;

	public LedSubsystem() {
		this.length = LedConstants.LENGTH;

		buffer = new AddressableLEDBuffer(length);

		led = new AddressableLED(Ports.ADDRESSABLE_LED);
		led.setLength(length);
		led.setData(buffer);
		led.start();

		numFlashes = 0;
		timesFlashed = 0;
		periodsPassed = 0;

		on = true;
		offColor = new Color(0, 0, 0);
	}

	public void setAllPixels(Color color) {
		setAllPixels(color, true);
	}

	public void setAllPixels(Color color, boolean reset) {
		for (int i = 0; i < length; i++)
			buffer.setRGB(i, color.r, color.g, color.b);
		// This is better than using the setPixel method in the same class because it's
		// not setting
		// the data every time.
		this.updateData();
		if (reset) {
			this.lastColor = color;
			this.progress = 1.0;
		}
	}

	/** Flashes all pixels the default number of times */
	public void flashAllPixels(Color color) {
		flashAllPixels(color, LedConstants.DEFAULT_FLASHES);
	}

	public void flashAllPixels(Color color, int numFlashes) {
		setAllPixels(color, true);

		this.numFlashes = numFlashes;
		this.timesFlashed = 0;
	}

	@Override
	public void periodic() {
		periodsPassed++;

		if (periodsPassed == 5 || timesFlashed < numFlashes) {
			if (on) {
				setAllPixels(offColor, false);
				on = false;
			} else {
				setAllPixels(lastColor, false);
				on = true;
				timesFlashed++;
			}
			this.updateData();
		}

		if (periodsPassed > 5) {
			periodsPassed = 0;
		}
	}

	public static Color calcBlending(Color c1, Color c2, double currentFade) {
		int r = (int) (c1.r * currentFade / 1 + c2.r * (1 - currentFade) / currentFade);
		int g = (int) (c1.g * currentFade / 1 + c2.g * (1 - currentFade) / currentFade);
		int b = (int) (c1.b * currentFade / 1 + c2.b * (1 - currentFade) / currentFade);
		return new Color(r, g, b);
	}

	public void setPixel(int pixelToSet, Color color) {
		if (color.hsv)
			buffer.setHSV(pixelToSet, color.h, color.s, color.v);
		else
			buffer.setRGB(pixelToSet, color.r, color.g, color.b);

		// THIS METHOD DOES NOT UPDATE THE BUFFER'S DATA! THAT NEEDS TO BE DONE MANUALLY
	}

	public void updateData() {
		led.setData(buffer);
		// System.out.println("Updated LED Data.");
	}

	public int getLength() {
		return length;
	}

	public static Color calcBlending(int i, int j, int k, int l, int m, int n, double progress) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcBlending'");
	}

}