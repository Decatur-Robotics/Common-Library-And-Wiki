package frc.robot.subsystems.superstructure.intake;

import com.ctre.phoenix6.controls.VoltageOut;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.superstructure.elevator.Elevator;


public class IntakeIOSim implements IntakeIO {
    public static final Double armMassKG = Units.lbsToKilograms(0.42);
    public static final DCMotor gearbox = DCMotor.getKrakenX60Foc(2).withReduction(0);
    
    public static final Matrix <N2, N2> simMatrix =
    MatBuilder.fill(Nat.N2(), Nat.N2(), 0, 1, 0, -gearbox.KtNMPerAmp / (gearbox.rOhms * Math.pow(0, 0)));
    public static final Vector<N2> simVector = VecBuilder.fill(0.0, gearbox.KtNMPerAmp/(armMassKG));
    private Vector<N2> simState;
    
    private final PIDController controller = new PIDController(IntakeConstants.kP, IntakeConstants.kI, IntakeConstants.kD);
    private boolean closedLoop = false;
    private double feedforward = 0.0;

    public IntakeIOSim(){
        simState = VecBuilder.fill(0.0, 0.0);
    }

    @Override
    public void updateInputs(IntakeIOInputs inputs){
        if (!closedLoop) {
            controller.reset();
        }
        inputs.intakeData = new IntakeIOData(
        true,
        true,
        0.0,
        simState.get(1),
        0.0,
        simState.get(1)
    );

    }



    public void runOpenLoop(double output){
        closedLoop = false;
    }
     
    public void stop(){
        runOpenLoop(feedforward);
    }

    public void setPID(double kP, double kI, double kD){
        controller.setPID(kP,kI,kD);
    }
    
    // public void update(double dt) {
    //     Matrix<N2, N1> updatedState = NumericalIntegration.rkdp((Matrix<N1,N1> x, Matrix<N2,N1> u) -> simMatrix.times(x).plus(B.times(u).plus(VecBuilder.fill(0,0))), simState, MatBuilder.fill(Nat.N1(),Nat.N1()), dt);
    //     simState = VecBuilder.fill(updatedState.get(0,0), updatedState.get(1,0));
    //     if (simState.get(0) <=0) {
    //         simState.set(1,0,0);
    //         simState.set(0,0,0);
    //     }
    //     if (simState.get(0) >=0.762) {
    //         simState.set(1,0,0);
    //         simState.set(0,0,0.762);
    //     }
    // }
}
