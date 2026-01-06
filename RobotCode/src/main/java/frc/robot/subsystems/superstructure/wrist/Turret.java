package frc.robot.subsystems.superstructure.Turret;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Turret extends SubsystemBase{
    private final String inputsName;
    private final TurretIO io;
    private double volts = 0.0;
    protected final TurretIOInputsAutoLogged inputs = new TurretIOInputsAutoLogged();
    private final TorqueCurrentFOC TorqueCurrentOut = new TorqueCurrentFOC(TurretConstants.PERPENDICULAR_CURRENT);
    private final VoltageOut VoltageOut = new VoltageOut(0.0);
    private final NeutralOut neutralOut = new NeutralOut();
    private TurretIOTalonFX TurretMotor;
    private Debouncer slamDebouncer;
    private double filteredVelocity;
    private Boolean isSlammed;

    
    private boolean brakeModeEnabled = true;
    public Turret(String inputsName, TurretIO io) {

        this.inputsName = inputsName;
        this.io = io;
        slamDebouncer = new Debouncer(TurretConstants.SLAM_DEBOUNCE_TIME);

        isSlammed = false;

    }
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(inputsName, inputs);


        io.setVolts(volts);
        isSlammed = slamDebouncer.calculate(Math.abs(filteredVelocity) < TurretConstants.MAX_SLAMMED_VELOCITY);

        //LoggedTracer.record(name);
        // this doesn't work mayhbe important idk 
        Logger.recordOutput(inputsName + "/BrakeModeEnabled", brakeModeEnabled);
    }

    public void setBrakeMode(boolean enabled) {
        if (brakeModeEnabled == enabled) return;
        brakeModeEnabled = enabled;
        io.setBrakeMode(enabled);
        

    }

    public double getTorqueCurrent() {
        return inputs.data.torqueCurrentAmps();
    }

    public double getVolts() {
        return inputs.data.appliedVolts();
    }
    public double getPosition() {
        return inputs.data.positionRad();
    }
    
    
    public void setCurrent(double current){
        TurretMotor.TurretMotor.setControl(TorqueCurrentOut.withOutput(current));
    }
    
    public void setVolts(double volts){
        TurretMotor.TurretMotor.setControl(VoltageOut.withOutput(volts));
    }

    public double getCurrent() {
        return inputs.data.torqueCurrentAmps();
    }
    
    public void stop(TurretIOTalonFX TurretMotor) {
        TurretMotor.TurretMotor.setControl(neutralOut);
    }
    
    public boolean isSlammed() {
        return isSlammed;
    }

    
    
}
