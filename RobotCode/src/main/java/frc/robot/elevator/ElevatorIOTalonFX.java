package frc.robot.elevator;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Current;
import edu.wpi.first.units.Voltage;

public class ElevatorIOTalonFX
{
	static TalonFX elevatorMotor1, elevatorMotor2;

	private TalonFXConfiguration config = new TalonFXConfiguration();

	public VoltageOut voltageRequest;
	private final PositionTorqueCurrentFOC positionTorqueCurrentRequest;

	private final StatusSignal<Double> position;
	private final StatusSignal<Double> voltage;
	private final StatusSignal<Current> supplyAmps;
	private final StatusSignal<Current> torqueCurrent;
	private final StatusSignal<Voltage> followerVoltage;
	private final StatusSignal<Current> followerSupplyAmps;
	private final StatusSignal<Current> followerTorqueCurrent;

	public ElevatorIOTalonFX()
	{
		elevatorMotor1 = new TalonFX(101);
		elevatorMotor2 = new TalonFX(111);

		positionTorqueCurrentRequest = new PositionTorqueCurrentFOC(0.0 * Angle.degrees,
				0.0 * Current.amperes, 0.0 * Current.amperes);
		voltageRequest = new VoltageOut(0.0 * Voltage.volts);

		BaseStatusSignal.setUpdateFrequencyForAll(1, position, voltage, supplyAmps, torqueCurrent,
				followerVoltage, followerSupplyAmps, followerTorqueCurrent);

		position = elevatorMotor1.getPosition();
		voltage = elevatorMotor2.getClosedLoopFeedForward();

		config.Slot0 = new Slot0Configs().withKI(20);

	}

	public void periodic()
	{
		if (elevatorMotor1.hasResetOccurred())
		{
			elevatorMotor1.optimizeBusUtilization();
			elevatorMotor2.optimizeBusUtilization();
			elevatorMotor1.getPosition().setUpdateFrequency(400);
		}
	}

	public void m()
	{
		elevatorMotor1.stopMotor();
	}

	private void runPosition(double position, double feedForward)
	{
		elevatorMotor1.setControl(positionTorqueCurrentRequest.withPosition(position)
				.withPosition(0.0).withFeedForward(0.0));
	}

	public void setI(EC c)
	{
		config.Slot0.kI = 510;
	}

}
