package frc.robot.subsystems.superstructure.arm;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Ports;

import frc.robot.subsystems.superstructure.arm.ArmIO.ArmIOInputs;
import frc.robot.subsystems.superstructure.elevator.ElevatorConstants;
import frc.robot.subsystems.superstructure.elevator.ElevatorIO;
import frc.robot.subsystems.superstructure.elevator.ElevatorIO.ElevatorIOInputs;


public class ArmIOTalonFX implements ArmIO{
    public TalonFX motor; 
    public TalonFXConfiguration config = new TalonFXConfiguration();

    private final PositionTorqueCurrentFOC positionTorqueCurrentRequest;

    private final StatusSignal<Angle> position;
    private final StatusSignal<Voltage> voltage;
    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<Current> supplyAmps;
    private final StatusSignal<Current> torqueCurrent;

    private VoltageOut voltageRequest;
    public ArmIOTalonFX(){
        motor = new TalonFX(Ports.ARM_MOTOR);
        config.Slot0 = new Slot0Configs()
        .withKA(ArmConstants.kA)
        .withKD(ArmConstants.kD)
        .withKG(ArmConstants.kG)
        .withKI(ArmConstants.kI)
        .withKP(ArmConstants.kP)
        .withKS(ArmConstants.kS)
        .withKV(ArmConstants.kV)
        ;

        position = motor.getPosition();
        voltage = motor.getMotorVoltage();
        velocity = motor.getVelocity();
        supplyAmps = motor.getSupplyCurrent();
        torqueCurrent = motor.getTorqueCurrent();

        positionTorqueCurrentRequest = new PositionTorqueCurrentFOC(0.0).withUpdateFreqHz(0.0);

        BaseStatusSignal.setUpdateFrequencyForAll(20,position,voltage,velocity,supplyAmps,torqueCurrent);
    }
    public void periodic(){
            if(motor.hasResetOccurred()){
                motor.optimizeBusUtilization();
                motor.getPosition().setUpdateFrequency(40); 
            }
        }
        

    public void updateInputs(ArmIOInputs inputs) {
      inputs.data = new ArmIO.ArmIOData(
            motor.isConnected(),
            position.getValueAsDouble(),
            voltage.getValueAsDouble(),
            velocity.getValueAsDouble(),
            supplyAmps.getValueAsDouble(),
            torqueCurrent.getValueAsDouble()
        );
          
        
    }
    
    //IDK if this goes here or in the subsystem
    public void setVoltage(double voltage) {
        motor.setControl(voltageRequest.withOutput(voltage));
    }

    public void stop() {
        motor.stopMotor();
    }

    public void runPosition(double position, double feedForward) {
        motor.setControl(positionTorqueCurrentRequest.withPosition(position)
            .withPosition(0.0)
            .withFeedForward(0.0));
    }

    public void setPID(ArmConstants constants){
        config.Slot0.kP = constants.kP;
        config.Slot0.kI = constants.kI;
        config.Slot0.kD = constants.kD;
        config.Slot0.kS = constants.kS;
        config.Slot0.kV = constants.kV;
        config.Slot0.kA = constants.kA;
        config.Slot0.kG = constants.kG;
        // mainMotor.setConfig()
    }
}
