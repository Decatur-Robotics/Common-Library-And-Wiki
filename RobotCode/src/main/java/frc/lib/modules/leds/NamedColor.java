package frc.lib.modules.leds;

public enum NamedColor
{
	RED,
	ORANGE,
	YELLOW,
	GREEN,
	BLUE;

	public Color get() 
	{
		return switch(this) 
		{
			case RED -> new Color(255, 0, 0);
			case ORANGE -> new Color(255, 100, 0);
			case YELLOW -> new Color(255, 255, 0);
			case GREEN -> new Color(0, 255, 0);
			case BLUE -> new Color(0, 0, 255);
		};
	}
}
