package frc.lib.examples.pivot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.CoastOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.robot.constants.Ports;

public class PivotIOTalonFX implements PivotIO{
    
    private TalonFX mainMotor, followerMotor;

    private PositionDutyCycle positionRequest;
    private VoltageOut voltageRequest;

    private TalonFXConfiguration config = new TalonFXConfiguration();

    private double pivotPosition;

    private CANcoder encoder;

    public PivotIOTalonFX(){
        mainMotor = new TalonFX(Ports.pivotMotorMain);
        followerMotor = new TalonFX(Ports.pivotMotorFollower);
        encoder = new CANcoder(0);
        followerMotor.setControl(new Follower(Ports.pivotMotorMain, MotorAlignmentValue.Opposed));

        pivotPosition = mainMotor.getPosition().getValueAsDouble();
        positionRequest = new PositionDutyCycle(pivotPosition);

        mainMotor.getConfigurator().apply(config);
        followerMotor.getConfigurator().apply(config);

        mainMotor.setPosition(encoder.getPosition().getValueAsDouble());
        followerMotor.setPosition(encoder.getPosition().getValueAsDouble());
    }

    @Override
    public void updateInputs(PivotIOInputs inputs){
        inputs.pivotData = new PivotIOData(
            mainMotor.isConnected(),
            followerMotor.isConnected(),
            mainMotor.getMotorVoltage().getValueAsDouble(),
            followerMotor.getMotorVoltage().getValueAsDouble(),
            mainMotor.getPosition().getValueAsDouble(),
            followerMotor.getPosition().getValueAsDouble(),
            encoder.getPosition().getValueAsDouble(),
            mainMotor.getVelocity().getValueAsDouble(),
            followerMotor.getVelocity().getValueAsDouble(),
            mainMotor.getAcceleration().getValueAsDouble(),
            followerMotor.getAcceleration().getValueAsDouble(),
            mainMotor.getDeviceTemp().getValueAsDouble(),
            followerMotor.getDeviceTemp().getValueAsDouble()
        );
    }

    @Override
    public void setVoltage(double voltage){
        mainMotor.setVoltage(voltage);
    }

    @Override
    public void setPosition(double position){
        this.pivotPosition = position;
        mainMotor.setControl(positionRequest.withPosition(position));
    }

    @Override
    public void coast(){
        mainMotor.setControl(new CoastOut());
    }
}
