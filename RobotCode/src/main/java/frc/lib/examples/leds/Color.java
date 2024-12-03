package frc.lib.examples.leds;

import java.util.HashMap;

public class Color {

	public static HashMap<String, Color> colors = new HashMap<>();

	public boolean hsv;

	public int r, g, b;
	public int h, s, v;
	// It is ultimately pointless to make these private.

	public Color(int red, int green, int blue) {
		this(red, green, blue, false);
	}

	public Color(int[] color) {
		this(color[0], color[1], color[2], false);
	}

	public static Color fromHSV(int h, int s, int v) {
		return new Color(h, s, v, true);
	}

	private Color(int red, int green, int blue, boolean hsv) {
		this.hsv = hsv;
		if (!hsv) {
			this.r = red;
			this.g = green;
			this.b = blue;
		} else {
			this.h = red;
			this.s = green;
			this.v = blue;
		}
	}

	public int[] getList() {
		return hsv ? new int[]
				{
						h, s, v
				} : new int[]
				{
						r, g, b
				};
	}

	public int get(int index) {
		return switch (index) {
			case 0 -> hsv ? h : r;
			case 1 -> hsv ? s : g;
			case 2 -> hsv ? v : b;
			default -> 0;
		};
	}

	public Color clone() throws CloneNotSupportedException {
		Color color = (Color) super.clone();
		return this.hsv ? new Color(this.r, this.g, this.b, true)
				: new Color(this.h, this.s, this.b, false);
	}

	public Color fromString(String color) {
		return colors.get(color);
	}

}
