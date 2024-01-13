package frc.lib.core.motors;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.BaseTalonConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.lib.core.PidParameters;
import frc.lib.core.util.TeamUtils;

/**
 * A wrapper class for Motors that helps to consistently and easily perform the following functions:
 * -Keep current and max speeds -Get and Reset encoder values -Lots and lots of SmartDashboard
 * information
 */
public class TeamTalonFX extends WPI_TalonFX
{

  public static double telemetryUpdateInterval_secs = 0.0;

  private double lastTelemetryUpdate = 0;

  private final String smartDashboardPrefix;

  private double maxSpeed = Double.MAX_VALUE;

  private PidParameters[] pidProfiles;

  public static boolean isPidControlMode(ControlMode mode)
  {
    switch (mode)
    {
    case Current:
      return false;
    case Disabled:
      return false;
    case Follower:
      return false;
    default:
      return true;
    }
  }

  public TeamTalonFX(String smartDashboardPrefix, int deviceNumber)
  {
    super(deviceNumber);
    this.smartDashboardPrefix = smartDashboardPrefix;

    // assuming quadencoder
    this.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    enableVoltageCompensation(true);
    
    pidProfiles = new PidParameters[4];
  }

  public void periodic()
  {
    double now = TeamUtils.getCurrentTime();

    if ((now - getLastTelemetryUpdate()) < telemetryUpdateInterval_secs)
    {
      return;
    }

    setLastTelemetryUpdate(now);

    double currentSpeed = getSelectedSensorVelocity(0);

    if (getMaxSpeed() == Double.MAX_VALUE || currentSpeed > getMaxSpeed())
      setMaxSpeed(currentSpeed);
  }

  public long getCurrentEncoderValue()
  {
    // This should be configurable
    return (long) getSensorCollection().getIntegratedSensorPosition();
  }

  public boolean isRunningPidControlMode()
  {
    return isPidControlMode(getControlMode());
  }

  public void set(double power, String reason)
  {
    super.set(power);
    // Logs.info("Set power to " + power + " REASON: " + reason);
  }

  public void resetEncoder()
  {
    getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public double getLastTelemetryUpdate()
  {
    return lastTelemetryUpdate;
  }

  public void setLastTelemetryUpdate(double val)
  {
    lastTelemetryUpdate = val;
  }

  public String getSmartDashboardPrefix()
  {
    return smartDashboardPrefix;
  }

  public int getNumEStops()
  {
    return numEStops;
  }

  public void setNumEStops(int val)
  {
    numEStops = val;
  }
  
  public double getMaxSpeed()
  {
    return maxSpeed;
  }

  public void setMaxSpeed(double val)
  {
    maxSpeed = val;
  }

  public PidParameters[] getPidProfiles()
  {
    return pidProfiles;
  }

  // Public wrapper for protected method (which aren't allowed in interfaces)
  // Use this for configurations which can be shared between SRX and FX
  // Otherwise down cast and use configAllSettings(TalonFXConfiguration allConfigs)
  // if using config settings only available for TalonFX
  public ErrorCode configBaseAllSettings(BaseTalonConfiguration allConfigs)
  {
    return configAllSettings(allConfigs);
  }

  public double getVelocityError()
  {
    if (getControlMode() != ControlMode.Velocity)
    {
      return 0;
    }
    double currentSpeed = getSelectedSensorVelocity(0);
    return (getClosedLoopTarget(0) - currentSpeed);
  }

  public void configureWithPidParameters(PidParameters pidParameters, int pidSlotIndex)
  {
    getPidProfiles()[pidSlotIndex] = pidParameters;

    config_kF(pidSlotIndex, pidParameters.kF, 30);
    config_kP(pidSlotIndex, pidParameters.kP, 30);
    config_kI(pidSlotIndex, pidParameters.kI, 30);
    config_kD(pidSlotIndex, pidParameters.kD, 30);
    configPeakOutputForward(pidParameters.kPeakOutput, 30);
    configPeakOutputReverse(-pidParameters.kPeakOutput, 30);
    configAllowableClosedloopError(pidSlotIndex, pidParameters.errorTolerance, 30);
  }
}