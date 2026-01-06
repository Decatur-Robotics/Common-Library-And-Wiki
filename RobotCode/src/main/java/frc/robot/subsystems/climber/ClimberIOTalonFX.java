package frc.robot.subsystems.climber;

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
import frc.robot.Constants;
import frc.robot.Constants.RobotType;
import frc.robot.Ports;
import frc.robot.subsystems.climber.ClimberIO.ClimberIOInputs;

public class ClimberIOTalonFX implements ClimberIO{
    public TalonFX climbMotor;

    private TalonFXConfiguration config = new TalonFXConfiguration();

    private VoltageOut voltageRequest;
    private final PositionTorqueCurrentFOC positionTorqueCurrentRequest;

    private final StatusSignal<Angle> position;
    private final StatusSignal<Voltage> voltage;
    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<Current> supplyAmps;
    private final StatusSignal<Current> torqueCurrent;

    public ClimberIOTalonFX() {
        climbMotor = new TalonFX(Constants.getRobotType() == RobotType.COMPETITION ? Ports.CLIMBER_MOTOR : Ports.CLIMBER_MOTOR);

        config.Slot0 = new Slot0Configs()
        .withKP(ClimberConstants.kP)
        .withKI(ClimberConstants.kI)
        .withKD(ClimberConstants.kD)
        .withKS(ClimberConstants.kS)
        .withKV(ClimberConstants.kV)
        .withKA(ClimberConstants.kA);
        
        position = climbMotor.getPosition();
        voltage = climbMotor.getMotorVoltage();
        velocity = climbMotor.getVelocity();
        supplyAmps = climbMotor.getSupplyCurrent();
        torqueCurrent = climbMotor.getTorqueCurrent();

        positionTorqueCurrentRequest = new PositionTorqueCurrentFOC(0.0).withUpdateFreqHz(0.0);

        BaseStatusSignal.setUpdateFrequencyForAll(20, position, voltage, velocity, supplyAmps);
    }

    //This is the periodic function, anything that needs to be updated every loop should go here
    public void periodic() {
        if(climbMotor.hasResetOccurred()){
            climbMotor.optimizeBusUtilization();
            climbMotor.getPosition().setUpdateFrequency(40);
        }
    }

        public void updateInputs(ClimberIOInputs inputs) {
            inputs.data = new ClimberIO.ClimberIOData(
                climbMotor.isConnected(),
                position.getValueAsDouble(),
                voltage.getValueAsDouble(),
                velocity.getValueAsDouble(),
                supplyAmps.getValueAsDouble(),
                torqueCurrent.getValueAsDouble()
            );
        }

        public void setVoltage(double voltage){
            climbMotor.setControl(voltageRequest.withOutput(voltage));
        }

        public void stop() {
            climbMotor.stopMotor();
        }

        public void setPID(ClimberConstants constants) {
            config.Slot0.kP = constants.kP;
            config.Slot0.kI = constants.kI;
            config.Slot0.kD = constants.kD;
            config.Slot0.kS = constants.kS;
            config.Slot0.kV = constants.kV;
            config.Slot0.kA = constants.kA;
        }
            
}
    
        
