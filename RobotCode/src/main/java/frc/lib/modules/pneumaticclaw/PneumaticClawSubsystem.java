package frc.lib.modules.pneumaticclaw;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticClawSubsystem extends SubsystemBase
{

	private Compressor mainCompressor;

	private DoubleSolenoid clawGrabberLeft, clawGrabberRight;

	public PneumaticClawSubsystem(Value startPosition) {
		mainCompressor = new Compressor(PneumaticClawConstants.PNEUMATICS_HUB,
				PneumaticsModuleType.REVPH);
		clawGrabberLeft = new DoubleSolenoid(PneumaticClawConstants.PNEUMATICS_HUB,
				PneumaticsModuleType.REVPH, PneumaticClawConstants.SOLENOID_LEFT_CLOSE,
				PneumaticClawConstants.SOLENOID_LEFT_OPEN);
		clawGrabberRight = new DoubleSolenoid(PneumaticClawConstants.PNEUMATICS_HUB,
				PneumaticsModuleType.REVPH, PneumaticClawConstants.SOLENOID_RIGHT_CLOSE,
				PneumaticClawConstants.SOLENOID_RIGHT_OPEN);

		mainCompressor.enableDigital();

		clawGrabberLeft.set(startPosition);
		clawGrabberRight.set(startPosition);
	}

	// Set solenoids to a desired value
	public void setSolenoid(Value mode) {
		clawGrabberLeft.set(mode);
		clawGrabberRight.set(mode);
	}
}
