package frc.lib.core.motors;

import com.ctre.phoenix6.StatusSignal;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ControlModeValue;

import frc.lib.core.util.TeamUtils;

/**
 * Used for everything that isn't a NEO. A wrapper class for motors that helps to consistently and
 * easily perform the following functions: 1. Keep current and max speeds. 2. Get and reset encoder
 * values. 3. Lots and lots of SmartDashboard information
 */
public class TeamTalonFX extends TalonFX
{

  private final VoltageOut voltageRequest = new VoltageOut(0);

  public static double telemetryUpdateInterval_secs = 0.0;

  private double lastTelemetryUpdate = 0;

  private final String smartDashboardPrefix;

  private double maxSpeed = Double.MAX_VALUE;

  private double encoderOffset;

  /** I would love to understand how to make this work. Avoid using right now */
  @Deprecated
  public static boolean isPidControlMode(final ControlModeValue mode)
  {
    switch (mode)
    {
    // case Current:
    case DisabledOutput:
    case Follower:
      return false;
    default:
      return true;
    }
  }

  public TeamTalonFX(final String smartDashboardPrefix, final int deviceNumber)
  {
    super(deviceNumber);
    this.smartDashboardPrefix = smartDashboardPrefix;

    // assuming quadencoder

    setControl(voltageRequest.withOutput(12));

  }

  public void periodic()
  {
    final double now = TeamUtils.getCurrentTime();
    // Renato told me to leave this alone, though we may wanna change it later.

    if ((now - getLastTelemetryUpdate()) < telemetryUpdateInterval_secs)
    {
      return;
    }

    setLastTelemetryUpdate(now);

    final StatusSignal<Double> currentSpeed = getVelocity();

    if (getMaxSpeed() == Double.MAX_VALUE || currentSpeed.getValueAsDouble() > getMaxSpeed())
      setMaxSpeed(currentSpeed.getValueAsDouble());
  }

  public double getCurrentEncoderValue()
  {
    // This should be configurable
    return getRotorPosition().getValueAsDouble() + encoderOffset;
  }

  public boolean isRunningPidControlMode()
  {
    return isPidControlMode(getControlMode().getValue());
  }

  /**
   * @param power  Between -1 and 1
   * @param reason Unused for now
   * @see #set(double)
   */
  public void set(final double power, final String reason)
  {
    super.set(power);
    // Logs.info("Set power to " + power + " REASON: " + reason);
  }

  public void resetEncoder()
  {
    encoderOffset = -1 * getRotorPosition().getValueAsDouble();

  }

  public double getLastTelemetryUpdate()
  {
    return lastTelemetryUpdate;
  }

  public void setLastTelemetryUpdate(final double val)
  {
    lastTelemetryUpdate = val;
  }

  public String getSmartDashboardPrefix()
  {
    return smartDashboardPrefix;
  }

  public double getMaxSpeed()
  {
    return maxSpeed;
  }

  public void setMaxSpeed(final double val)
  {
    maxSpeed = val;
  }

  /** please make this work. I don't know how */
  public double getVelocityError()
  {
    if (getControlMode().getValue() != ControlModeValue.VelocityDutyCycle)
    {
      return 0;
    }

    final double currentSpeed = getVelocity().getValueAsDouble();
    return (getRotorPosition().getValueAsDouble() - currentSpeed);
  }

}