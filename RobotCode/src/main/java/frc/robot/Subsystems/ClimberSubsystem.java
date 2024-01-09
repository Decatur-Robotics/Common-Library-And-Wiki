package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.ITeamTalon;
import frc.lib.core.motors.TeamTalonFX;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.Ports;

public class ClimberSubsystem extends SubsystemBase{
    
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
       
       public void setPowers (double leftPower, double rightPower, String reason) {
            if()

       }
       public boolean leftMotorPowerCheck(double power) {
        return (extendMotorLeft.getCurrentEncoderValue() > ClimberConstants.maxExtensionLeft && power >= 0) || (extendMotorLeft.getCurrentEncoderValue() < ClimberConstants.minExtensionLeft && power <= 0);
       }
    }

}
