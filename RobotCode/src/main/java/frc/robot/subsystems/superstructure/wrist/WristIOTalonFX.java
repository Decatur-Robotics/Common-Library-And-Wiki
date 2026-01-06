package frc.robot.subsystems.superstructure.wrist;

import static frc.robot.util.PhoenixUtil.tryUntilOk;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public class WristIOTalonFX implements WristIO{
    public TalonFX wristMotor;


    private StatusSignal<Angle> position;
    private StatusSignal<AngularVelocity> velocity;
    private StatusSignal<Voltage> appliedVolts;
    private StatusSignal<Current> supplyCurrent;
    private StatusSignal<Current> torqueCurrent;
    private final PositionTorqueCurrentFOC positionTorqueCurrentRequest =
    new PositionTorqueCurrentFOC(0.0).withUpdateFreqHz(0.0);
            
        //add port later
    public void WristIoTalonFx(){

        wristMotor = new TalonFX(0);

        position = wristMotor.getPosition();
        velocity = wristMotor.getVelocity();
        appliedVolts = wristMotor.getMotorVoltage();
        supplyCurrent = wristMotor.getSupplyCurrent();
        torqueCurrent = wristMotor.getTorqueCurrent();

        tryUntilOk(5,() -> BaseStatusSignal.setUpdateFrequencyForAll(40.0,
        position, velocity, supplyCurrent, torqueCurrent));
        //replace with port
        tryUntilOk(5, () -> wristMotor.optimizeBusUtilization());
        tryUntilOk(5, () -> wristMotor.getConfigurator().apply(WristConstants.MOTOR_CONFIG));
    }
    public void periodic(){
        if(wristMotor.hasResetOccurred()){
            wristMotor.optimizeBusUtilization();
            wristMotor.getPosition().setUpdateFrequency(40);
        }



    }

    @Override
    public void updateInputs(WristIOInputs inputs){
        inputs.data =
            new WristIOData(
                Units.rotationsToRadians(position.getValueAsDouble()), // with reduction?
                Units.rotationsToRadians(velocity.getValueAsDouble()),
                appliedVolts.getValueAsDouble(),
                torqueCurrent.getValueAsDouble(),
                supplyCurrent.getValueAsDouble(),

                BaseStatusSignal.isAllGood(
                    position, velocity, supplyCurrent, torqueCurrent));
    }
    @Override
    public void runPosition(double positionRad, double feedforward){
        wristMotor.setControl(
            positionTorqueCurrentRequest
                .withPosition(Units.radiansToRotations(positionRad))
                .withFeedForward(feedforward));

    }

}
