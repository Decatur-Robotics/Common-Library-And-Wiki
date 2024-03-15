package frc.lib.modules.pneumaticclaw;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.Untested;

@Untested
public class PneumaticClawSubsystem extends SubsystemBase {

	private final DoubleSolenoid ClawGrabberLeft, ClawGrabberRight;

	public PneumaticClawSubsystem(Value startPosition) {
		ClawGrabberLeft = new DoubleSolenoid(PneumaticClawConstants.PNEUMATICS_HUB,
				PneumaticsModuleType.REVPH, PneumaticClawConstants.SOLENOID_LEFT_CLOSE,
				PneumaticClawConstants.SOLENOID_LEFT_OPEN);
		ClawGrabberRight = new DoubleSolenoid(PneumaticClawConstants.PNEUMATICS_HUB,
				PneumaticsModuleType.REVPH, PneumaticClawConstants.SOLENOID_RIGHT_CLOSE,
				PneumaticClawConstants.SOLENOID_RIGHT_OPEN);

		// This is try-with-resources, it will automatically close the compressor
		Compressor mainCompressor = new Compressor(PneumaticClawConstants.PNEUMATICS_HUB,
				PneumaticsModuleType.REVPH);
		mainCompressor.enableDigital();

		ClawGrabberLeft.set(startPosition);
		ClawGrabberRight.set(startPosition);
	}

	/**
	 * Set solenoids to a desired value
	 */
	public void setSolenoid(Value mode) {
		ClawGrabberLeft.set(mode);
		ClawGrabberRight.set(mode);
	}

}
