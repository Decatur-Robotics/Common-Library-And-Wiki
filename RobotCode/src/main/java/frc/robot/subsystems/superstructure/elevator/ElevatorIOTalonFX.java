package frc.robot.subsystems.superstructure.elevator;

import java.io.ObjectInputFilter.Config;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;

import frc.robot.Ports;
import frc.robot.Constants.RobotType;
import frc.robot.subsystems.superstructure.elevator.ElevatorIO.ElevatorIOInputs;

public class ElevatorIOTalonFX implements ElevatorIO {
    public TalonFX mainMotor, followerMotor;

    private TalonFXConfiguration config = new TalonFXConfiguration();

    private VoltageOut voltageRequest;
    private final PositionTorqueCurrentFOC positionTorqueCurrentRequest;

    private final StatusSignal<Angle> position;
    private final StatusSignal<Voltage> voltage;
    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<Current> supplyAmps;
    private final StatusSignal<Current> torqueCurrent;
    private final StatusSignal<Voltage> followerVoltage;
    private final StatusSignal<AngularVelocity> followerVelocity;
    private final StatusSignal<Current> followerSupplyAmps;
    private final StatusSignal<Current> followerTorqueCurrent;

    


    public ElevatorIOTalonFX(){
        mainMotor = new TalonFX(Constants.getRobotType() == RobotType.COMPETITION ? Ports.ELEVATOR_MOTOR_MAIN : Ports.ELEVATOR_MOTOR_MAIN);
        followerMotor = new TalonFX(Constants.getRobotType() == RobotType.COMPETITION ? Ports.ELEVATOR_MOTOR_FOLLOWER : Ports.ELEVATOR_MOTOR_FOLLOWER);
        followerMotor.setControl(new Follower(Ports.ELEVATOR_MOTOR_MAIN, true));

        config.Slot0 = new Slot0Configs()
            .withKP(ElevatorConstants.kP)
            .withKI(ElevatorConstants.kI)
            .withKD(ElevatorConstants.kD)
            .withKS(ElevatorConstants.kS)
            .withKV(ElevatorConstants.kV)
            .withKA(ElevatorConstants.kA);

        position = mainMotor.getPosition();
        voltage = mainMotor.getMotorVoltage();
        velocity = mainMotor.getVelocity();
        supplyAmps = mainMotor.getSupplyCurrent();
        torqueCurrent = mainMotor.getTorqueCurrent();
        followerVoltage = followerMotor.getMotorVoltage();
        followerVelocity = followerMotor.getVelocity();
        followerSupplyAmps = followerMotor.getSupplyCurrent();
        followerTorqueCurrent = followerMotor.getTorqueCurrent();

        positionTorqueCurrentRequest = new PositionTorqueCurrentFOC(0.0).withUpdateFreqHz(0.0);

        BaseStatusSignal.setUpdateFrequencyForAll(20, position, voltage, velocity, supplyAmps, torqueCurrent, followerVoltage, followerVelocity, followerSupplyAmps, followerTorqueCurrent);

    }

    public void periodic(){
        if (mainMotor.hasResetOccurred()|| followerMotor.hasResetOccurred()){
            mainMotor.optimizeBusUtilization();
            followerMotor.optimizeBusUtilization();
            mainMotor.getPosition().setUpdateFrequency(40);
        }
        
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
      inputs.data = new ElevatorIO.ElevatorIOData(
            mainMotor.isConnected(),
            followerMotor.isConnected(),
            position.getValueAsDouble(),
            voltage.getValueAsDouble(),
            velocity.getValueAsDouble(),
            supplyAmps.getValueAsDouble(),
            torqueCurrent.getValueAsDouble(),
            followerVoltage.getValueAsDouble(),
            followerVelocity.getValueAsDouble(),
            followerSupplyAmps.getValueAsDouble(),
            followerTorqueCurrent.getValueAsDouble()
        );
          
        
    }
    
    //IDK if this goes here or in the subsystem
    public void setVoltage(double voltage) {
        mainMotor.setControl(voltageRequest.withOutput(voltage));
    }

    @Override
    public void stop() {
        mainMotor.stopMotor();
    }

    @Override
    public void runPosition(double position, double feedForward) {
        mainMotor.setControl(positionTorqueCurrentRequest.withPosition(position)
            .withPosition(0.0)
            .withFeedForward(0.0));
    }

    @Override
    public void setPID(ElevatorConstants constants){
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
