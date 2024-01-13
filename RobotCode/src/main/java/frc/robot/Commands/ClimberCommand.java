package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.ClimberSubsystem;
import frc.robot.constants.ClimberConstants;

public class ClimberCommand extends Command{
    public ClimberSubsystem climber;
    public DoubleSupplier leftInput, rightInput;

    public final double DEADBAND_RANGE = 0.1;
    public void ExtendRetractArm(ClimberSubsystem c1, DoubleSupplier leftInput, DoubleSupplier rightInput) {
        climber = c1;
        addRequirements(climber);
        this.leftInput = leftInput;
        this.rightInput = rightInput;
    }

    public void execute() {
        double realLeftPower = 0;
        double realRightPower = 0;
        if(Math.abs(leftInput.getAsDouble())>DEADBAND_RANGE) {
            realLeftPower = ClimberConstants.MAX_SPEED * -leftInput.getAsDouble();
        }
        
        climber.setPowers(realLeftPower, realRightPower, "climbing");

    }
}