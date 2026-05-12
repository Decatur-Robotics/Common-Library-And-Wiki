package frc.lib.util;

/** A class for doing countdowns or other forms of waiting */
public class Timer
{

    /** In ms */
    private long startTime, duration;

    /**
     * @param duration in ms. Starts automatically
     */
    public Timer(long duration)
    {
        this.duration = duration;
        reset();
    }

    /**
     * Sets {@link #startTime} to the current time, effectively resetting the timer
     */
    public void reset()
    {
        startTime = System.currentTimeMillis();
    }

    public boolean isDone()
    {
        return System.currentTimeMillis() - startTime >= duration;
    }

}
