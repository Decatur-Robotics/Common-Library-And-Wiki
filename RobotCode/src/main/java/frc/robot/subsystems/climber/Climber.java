package frc.robot.subsystems.climber;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;

import org.littletonrobotics.junction.AutoLogOutput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

public class Climber extends SubsystemBase{
    private double position;
    private double voltage;
    private double velocity;
    private final String inputsName;
    
    private final ClimberIOInputsAutoLogged inputs = new ClimberIOInputsAutoLogged();
    private ClimberIO io;

    private MotionMagicVoltage positionRequest;
    private VelocityVoltage velocityRequest;

    public Climber(ClimberIO io) {
        this.inputsName= this.getClass().getSimpleName() + "Inputs";
        this.io = io;
        position = ClimberConstants.STOWED_POSITION;
    }
    
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs(inputsName, inputs);

    }

    public void setPosition(ClimberIOTalonFX climbMotor){
        this.position = position;
        climbMotor.climbMotor.setControl(positionRequest.withPosition(position));
    }

    public double getPosition(ClimberIOTalonFX climbMotor){
        return position;
    }

    public Command zeroCommand(ClimberIOTalonFX climbMotor) {
        return Commands.sequence(
            Commands.runOnce(() -> {
                climbMotor.climbMotor.setVoltage(voltage);
            }),
            Commands.runOnce(() -> {
                climbMotor.climbMotor.setPosition(0.0);
            }));
    }


}
