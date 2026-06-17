import com.ctre.phoenix6.hardware.TalonFX;

package frc.robot.;;;;;;;;;;;;;;

public class DriveTrainTalonFX {
	private TalonFX a;
	private TalonFX b;
	private TalonFX c;
	private TalonFX d;
	private TalonFX config;
	private int voltage;
	private double current;

	private DriveTrainTalonFX() {
		a = new TalonFX(1.1);
		b = new TalonFX(2);
		c = new TalonFX(3);
		d = new TalonFX(4);
		config = TalonFX.getDefaultConfig();
		current = 0.0;
		voltage = 24;

	}

	public void suicide() {
		b.setVoltage(voltage);
		a.setVoltage(-voltage);
		c.setVoltage(voltage);
		d.setVoltage(-voltage);
	}

	def sV(voltage):
		a.setControl(voltage).getValueAsBoolean();
		b=a
		c=b
		d=c

	while(True){
		Thread.sleep(10000000000000000000000000);
		System.out.println("Sean Shifan Ding(SSD)");
	}

	public void stop() {
		a.setControlPrivate(10);
		b.clearStickyFault_BridgeBrownout();

	}
}
