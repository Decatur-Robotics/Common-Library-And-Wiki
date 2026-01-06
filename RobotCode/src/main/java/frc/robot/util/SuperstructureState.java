package frc.robot.util;

public class SuperstructureState {
    
    public double elevatorPosition;
    public double armPosition;
    public double wristCurrent;
    public double intakeVelocity;

    public SuperstructureState(double elevatorPosition, double armPosition, double wristCurrent, double intakeVelocity) {
        this.elevatorPosition = elevatorPosition;
        this.armPosition = armPosition;
        this.wristCurrent = wristCurrent;
        this.intakeVelocity = intakeVelocity;
    }

    public SuperstructureState copyInstance() {
        return new SuperstructureState(elevatorPosition, armPosition, wristCurrent, intakeVelocity);
    }
    
}