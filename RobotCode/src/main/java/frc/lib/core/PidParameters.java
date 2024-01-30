package frc.lib.core;

import java.util.Objects;

import javax.annotation.processing.Generated;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.core.motors.*;
import frc.lib.core.util.TeamUtils;

public class PidParameters
{
	private final static double TELEMETRY_UPDATE_INTERVAL_SECS = 0.5;

	private double lastTelemetryUpdate;

	// The k prefix is Hungarian Notation and doesn't match our code standards, but I don't feel
	// like fixing it rn -Renato (Jan 2024)
	private double kP, kI, kD, kF;

	private final double KI_ZONE;

	private double kPeakOutput;

	private double maxAcc;

	private double maxVel;
	private int errorTolerance;

	/** For TalonSRX */
	public PidParameters(final double kP, final double kI, final double kD, final double kF,
			final double kIZone, final double kPeakOutput, final int errorTolerance)
	{
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.KI_ZONE = kIZone;
		this.kPeakOutput = kPeakOutput;
		this.errorTolerance = errorTolerance;
	}

	/** For Spark MAX */
	public PidParameters(final double kP, final double kI, final double kD, final double kF,
			final double kIZone, final double kPeakOutput, final double maxVel, final double maxAcc,
			final int errorTolerance)
	{
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.KI_ZONE = kIZone;
		this.kPeakOutput = kPeakOutput;
		this.errorTolerance = errorTolerance;
		this.maxVel = maxVel;
		this.maxAcc = maxAcc;
	}

	@Override
	public PidParameters clone()
	{
		return new PidParameters(kP, kI, kD, kF, KI_ZONE, kPeakOutput, errorTolerance);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj == null)
			return false;
		if (!(obj instanceof PidParameters))
			return false;

		final PidParameters otherPP = (PidParameters) obj;

		return otherPP.kP == kP && otherPP.kI == kI && otherPP.kD == kD && otherPP.kF == kF
				&& otherPP.KI_ZONE == KI_ZONE && otherPP.kPeakOutput == kPeakOutput
				&& otherPP.errorTolerance == errorTolerance;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(kP, kI, kD, kF, KI_ZONE, kPeakOutput, errorTolerance);
	}

	/** Use these parameters on a motor */
	public void configureMotorWithPidParameters(final IMotor Motor, final int PidSlotIndex)
	{
		Motor.configureWithPidParameters(this, PidSlotIndex);
	}

	public void periodic(final String prefix, final IMotor motor, final int pidSlotIndex)
	{
		final double NOW = TeamUtils.getCurrentTime();

		if ((NOW - lastTelemetryUpdate) < TELEMETRY_UPDATE_INTERVAL_SECS)
		{
			return;
		}

		lastTelemetryUpdate = NOW;

		// We update the motor immediately when the the motor is in a PID-controlled mode
		final boolean UPDATE_MOTOR = motor.isRunningPidControlMode();

		final double new_kF = SmartDashboard.getNumber(prefix + ".PID.kF", kF);
		if (new_kF != kF)
		{
			kF = new_kF;
			if (UPDATE_MOTOR)
				motor.configF(kF, pidSlotIndex);
		}
		SmartDashboard.putNumber(prefix + ".PID.kF", kF);

		final double NEW_KP = SmartDashboard.getNumber(prefix + ".PID.kP", kP);
		if (NEW_KP != kP)
		{
			kP = NEW_KP;
			if (UPDATE_MOTOR)
				motor.configP(kP, pidSlotIndex);
		}
		SmartDashboard.putNumber(prefix + ".PID.kP", kP);

		final double NEW_KI = SmartDashboard.getNumber(prefix + ".PID.kI", kI);
		if (NEW_KI != kI)
		{
			kI = NEW_KI;
			if (UPDATE_MOTOR)
				motor.configI(kI, pidSlotIndex);
		}
		SmartDashboard.putNumber(prefix + ".PID.kI", kI);

		final double NEW_KD = SmartDashboard.getNumber(prefix + ".PID.kD", kD);
		if (NEW_KD != kD)
		{
			kD = NEW_KD;
			if (UPDATE_MOTOR)
				motor.configD(kD, pidSlotIndex);
		}
		SmartDashboard.putNumber(prefix + ".PID.kD", kD);

		final double NEW_PEAK_OUTPUT = SmartDashboard.getNumber(prefix + ".kPeakOutput",
				kPeakOutput);
		if (NEW_PEAK_OUTPUT != kPeakOutput)
		{
			kPeakOutput = NEW_PEAK_OUTPUT;
			if (UPDATE_MOTOR)
				motor.configPeakOutput(kPeakOutput);
		}
		SmartDashboard.putNumber(prefix + ".kPeakOutput", kPeakOutput);

		final int NEW_ERROR_TOLERANCE = (int) SmartDashboard.getNumber(prefix + ".errorTolerance",
				errorTolerance);
		if (NEW_ERROR_TOLERANCE != errorTolerance)
		{
			errorTolerance = NEW_ERROR_TOLERANCE;
			if (UPDATE_MOTOR)
				motor.setClosedLoopErrorLimit(pidSlotIndex, errorTolerance);
		}
		SmartDashboard.putNumber(prefix + ".errorTolerance", errorTolerance);

		motor.onPidPeriodic(pidSlotIndex);
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getKP()
	{
		return kP;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setKP(final double kP)
	{
		this.kP = kP;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getKI()
	{
		return kI;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setKI(final double kI)
	{
		this.kI = kI;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getKD()
	{
		return kD;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setKD(final double kD)
	{
		this.kD = kD;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getKF()
	{
		return kF;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setKF(final double kF)
	{
		this.kF = kF;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public int getErrorTolerance()
	{
		return errorTolerance;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setErrorTolerance(final int errorTolerance)
	{
		this.errorTolerance = errorTolerance;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getKPeakOutput()
	{
		return kPeakOutput;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setKPeakOutput(final double kPeakOutput)
	{
		this.kPeakOutput = kPeakOutput;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getMaxAcc()
	{
		return maxAcc;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setMaxAcc(final double maxAcc)
	{
		this.maxAcc = maxAcc;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public double getMaxVel()
	{
		return maxVel;
	}

	@Generated("Language Support for Java(TM) by Red Hat")
	public void setMaxVel(final double maxVel)
	{
		this.maxVel = maxVel;
	}
}