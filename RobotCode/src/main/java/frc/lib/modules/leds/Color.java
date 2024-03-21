package frc.lib.modules.leds;

public class Color
{

	// Preset colors
	public static final Color Red = new Color(255, 0, 0);
	public static final Color Green = new Color(0, 255, 0);
	public static final Color Blue = new Color(0, 0, 200);
	public static final Color Yellow = new Color(255, 127, 0);
	public static final Color Cyan = new Color(0, 255, 255);
	public static final Color Magenta = new Color(255, 0, 255);
	public static final Color White = new Color(255, 255, 255);
	public static final Color Black = new Color(0, 0, 0);

	public boolean hsv;

	public int r, g, b;
	public int h, s, v;
	// It is ultimately pointless to make these private.

	public Color(int red, int green, int blue)
	{
		this(red, green, blue, false);
	}

	public Color(int[] color)
	{
		this(color[0], color[1], color[2], false);
	}

	public static Color fromHSV(int h, int s, int v)
	{
		return new Color(h, s, v, true);
	}

	private Color(int red, int green, int blue, boolean hsv)
	{
		this.hsv = hsv;
		if (!hsv)
		{
			this.r = red;
			this.g = green;
			this.b = blue;
		}
		else
		{
			this.h = red;
			this.s = green;
			this.v = blue;
		}
	}

	public int[] getList()
	{
		return hsv ? new int[]
		{
				h, s, v
		} : new int[]
		{
				r, g, b
		};
	}

	public int get(int index)
	{
		switch (index)
		{
		case 0:
			return hsv ? h : r;
		case 1:
			return hsv ? s : g;
		case 2:
			return hsv ? v : b;
		}
		return 0; // If index > 2, return 0
	}

	public Color clone()
	{
		return this.hsv ? new Color(this.r, this.g, this.b, true)
				: new Color(this.h, this.s, this.b, false);
	}

}
