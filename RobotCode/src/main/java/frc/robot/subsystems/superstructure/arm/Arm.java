package frc.robot.subsystems.superstructure.arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.subsystems.superstructure.elevator.ElevatorIOInputsAutoLogged;
import frc.robot.subsystems.superstructure.elevator.ElevatorIOTalonFX;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
public class Arm extends SubsystemBase{
    private double position;
    private double voltage;
    private double velocity;
    private ArmIOTalonFX motor;
    private final ArmIOInputsAutoLogged inputs = new ArmIOInputsAutoLogged();

    private CANcoder throughBoreEncoder;

    private MotionMagicVoltage positionRequest;
    private ArmIO io;
    private boolean isEstopped;

    public Arm (ArmIO io){
        this.io = io;
        position = ArmConstants.STOWED_POSITION;
        throughBoreEncoder = new CANcoder(Ports.ARM_ENCODER);
        throughBoreEncoder.getConfigurator().apply(ArmConstants.ENCODER_CONFIG);
    }

    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Arm Inputs", inputs);

        if(isEstopped){
            io.stop();
        }
    }

    public void setPosition(double position){
        motor.motor.setControl(positionRequest.withPosition(position));
    }

    

    public double getPosition(){
        return throughBoreEncoder.getPosition().getValueAsDouble();
    }

    public Command zeroCommand(ArmIOTalonFX motor){
        return Commands.sequence(
        Commands.runOnce(() -> {
            motor.motor.setVoltage(voltage);
        }),
        Commands.runOnce(() -> {
            motor.motor.setPosition(0.0);
        }));
    }
}
