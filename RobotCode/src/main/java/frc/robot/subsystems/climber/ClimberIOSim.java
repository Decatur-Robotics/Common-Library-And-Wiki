package frc.robot.subsystems.climber;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.NumericalIntegration;
import edu.wpi.first.math.system.plant.DCMotor;

public class ClimberIOSim implements ClimberIO {
    //moi stands for moment of inertia, calculated by moi = mass * distance from the rotational component squared
    private static final double moi = 0.00001;
    private static final DCMotor gearbox = DCMotor.getKrakenX60(1).withReduction(0);
    private static final Matrix<N2, N2> A = 
    MatBuilder.fill(
        Nat.N2(), 
        Nat.N2(),
        0,
        1,
        0,
        -gearbox.KtNMPerAmp / (gearbox.KvRadPerSecPerVolt * gearbox.rOhms * moi)
    );
    private static final Vector<N2> B = VecBuilder.fill(0.0, gearbox.KtNMPerAmp / moi);

    private Vector<N2> x = VecBuilder.fill(0.0, gearbox.KtNMPerAmp/moi); 

    private Vector<N2> simState;
    private double inputTorqueCurrent = 0.0;
    private double appliedVolts = 0.0;

    public ClimberIOSim(){
        simState = VecBuilder.fill(0.0, 0.0);
    }

    @Override
    public void updateInputs(ClimberIOInputs inputs) {
        inputs.data = new ClimberIOData(
            true,
            simState.get(0),
            simState.get(1),
            appliedVolts,
            0.0,
            inputTorqueCurrent
        );
    }

    
    public void runTorqueCurrent(double current) {
        inputTorqueCurrent = current;
        appliedVolts = gearbox.getVoltage(gearbox.getTorque(inputTorqueCurrent), simState.get(1, 0));
        appliedVolts = MathUtil.clamp(appliedVolts, -12.0, 12.0);
    }

    private void update(double dt){
        inputTorqueCurrent = 
            MathUtil.clamp(inputTorqueCurrent, -gearbox.stallCurrentAmps, gearbox.stallCurrentAmps);
        Matrix<N2, N1> updatedState = 
            NumericalIntegration.rkdp(
                (Matrix<N2, N1> x, Matrix<N1, N1> u) -> A.times(x).plus(B.times(u)),
                simState,
                VecBuilder.fill(inputTorqueCurrent * 15),
                dt);
        simState = VecBuilder.fill(updatedState.get(0, 0), updatedState.get(1,0));
    }
}
