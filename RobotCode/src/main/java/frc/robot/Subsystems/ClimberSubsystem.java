package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.ITeamTalon;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Ports;

public class ClimberSubsystem extends SubsystemBase {

    public ITeamTalon[] extendMotors;

    private ITeamTalon extendMotorLeft;
    private ITeamTalon extendMotorRight;
    
    public ClimberSubsystem(ITeamTalon motorExtend) {
        // sets extension of left and right motors to given extension length
        extendMotorLeft = motorExtend;
       extendMotorRight = motorExtend;
       
       extendMotorLeft.setNeutralMode(NeutralMode.Brake);
       extendMotorRight.setNeutralMode(NeutralMode.Brake);

       new TeamTalonFX("Subsystems.Climber.ExtendRight", Ports.CLIMBER_EXTEND_RIGHT_MOTOR);
       new TeamTalonFX("Subsystems.Climber.ExtendLeft", Ports.CLIMBER_EXTEND_LEFT_MOTOR);
    }
       
    public void setPowers(double leftPower, double rightPower, String reason) {
        if(!(leftMotorPowerCheck(leftPower))) {
            leftPower=0;
        }
        if(!(rightMotorPowerCheck(rightPower))) {
            rightPower=0;
        }
        if (leftPower > 0) leftPower /= 3;
        if(rightPower > 0) rightPower /= 3;

        extendMotorLeft.set(-leftPower/3);
        extendMotorLeft.set(-rightPower/3);
    }

    
    public boolean leftMotorPowerCheck(double power) {
        return (extendMotorLeft.getCurrentEncoderValue() > ClimberConstants.MAX_EXTENSION_LEFT && power >= 0) || (extendMotorLeft.getCurrentEncoderValue() < ClimberConstants.MIN_EXTENSION_LEFT && power <= 0);
    }
    public boolean rightMotorPowerCheck(double power) {
        return (extendMotorRight.getCurrentEncoderValue() > ClimberConstants.MAX_EXTENSION_RIGHT && power <= 0) || (extendMotorRight.getCurrentEncoderValue() < ClimberConstants.MIN_EXTENSION_RIGHT && power >= 0);   
    }

}
