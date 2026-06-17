package frc.robot.subsystems.superstructure.wrist;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class WristIOSim implements WristIO {
  private final DCMotorSim sim;
  private final DCMotor gearbox;
  private double appliedVoltage = 0.0;
    //fix this stuff
  public WristIOSim(DCMotor motorModel, double reduction, double moi) {

    gearbox = motorModel;
    sim =
        new DCMotorSim(LinearSystemId.createDCMotorSystem(motorModel, moi, reduction), motorModel);
  }
  @Override
  public void updateInputs(WristIOInputs inputs) {
    if (DriverStation.isDisabled()) {
      setVolts(0.0);
    }

    sim.update(0/*find a loopperiod constant number to put here eventually*/);
    inputs.data =
        new WristIOData(
            sim.getAngularPositionRad(),
            sim.getAngularVelocityRadPerSec(),
            appliedVoltage,
            gearbox.getCurrent(sim.getAngularVelocityRadPerSec(), appliedVoltage),
            sim.getCurrentDrawAmps(),
            false);
  }
    @Override
    public void setVolts(double volts) {
        appliedVoltage = MathUtil.clamp(volts, -12.0, 12.0);
        sim.setInputVoltage(appliedVoltage);
    }
    @Override
    public void setCurrent(double amps) {
      setVolts(gearbox.getVoltage(gearbox.getTorque(amps), sim.getAngularVelocityRadPerSec()));
    }

    @Override
    public void stop() {
        setVolts(0.0);
    }
}
