package frc.robot.subsystems.superstructure.arm;


import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.MathUtil;
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
import frc.robot.subsystems.superstructure.elevator.ElevatorIO.ElevatorIOData;
import frc.robot.subsystems.superstructure.elevator.ElevatorIO.ElevatorIOInputs;

public class ArmIOSim implements ArmIO{
    public static final Double armMassKG = Units.lbsToKilograms(16);
    public static final DCMotor gearbox = DCMotor.getFalcon500Foc(1).withReduction(45);

    public static final Matrix<N2, N2> A = 
    MatBuilder.fill(Nat.N2(), Nat.N2(), 0, 1, 0, -gearbox.KtNMPerAmp/(gearbox.rOhms*Math.pow(0, 0)));
    public static final Vector<N2> B = VecBuilder.fill(0.0, gearbox.KtNMPerAmp/(armMassKG));

    private Vector<N2> simState;
    private double inputTorqueCurrent;
    private double appliedVoltage;

    private final PIDController controller = new PIDController(0.0, 0.0, 0.0);
    private boolean closedLoop = false;
    private double feedForward = 0;

    public ArmIOSim() {
        simState = VecBuilder.fill(0.0, 0.0);
    }
    @Override
    public void updateInputs(ArmIOInputs inputs) {
        if(!closedLoop){
            controller.reset();
        }
        else{
            inputTorqueCurrent = controller.calculate(simState.get(0, 0), 0.0) + feedForward;
            // update(1/1000);
        }

        inputs.data = new ArmIOData(
        true,
        simState.get(0),
        simState.get(1),
        appliedVoltage,
        Math.copySign(inputTorqueCurrent, appliedVoltage),
        Math.copySign(inputTorqueCurrent, appliedVoltage)
        );
    }

    public void runOpenLoop(double output) {
        setInputTorqueCurrent(output);
        closedLoop = false;
    
    }

    public void runVolts(double volts) {
        appliedVoltage = volts;
        closedLoop = false;
    }


    public void stop(){
        runOpenLoop(0);
    }


    public void runPosition(double position, double feedForward) {
        controller.setSetpoint(position);
        this.feedForward = feedForward;
        closedLoop = true;
    }

    public void setPID(double kP, double kI, double kD) {
        controller.setPID(kP, kI, kD);
        
    }

    public void setInputTorqueCurrent(double torqueCurrent) {
        this.inputTorqueCurrent = torqueCurrent;
        appliedVoltage = gearbox.getVoltage(gearbox.getTorque(inputTorqueCurrent), simState.get(1, 0));
    }

    public void setInputVoltage(double voltage) {
        setInputTorqueCurrent(gearbox.getCurrent(simState.get(1, 0), voltage));
    }

        public void update(double dt) {
        inputTorqueCurrent = MathUtil.clamp(inputTorqueCurrent, -gearbox.stallCurrentAmps, gearbox.stallCurrentAmps);
        Matrix<N2, N1> updatedState = NumericalIntegration.rkdp((Matrix<N2, N1> x, Matrix<N1, N1> u) -> A.times(x).plus(B.times(u).plus(VecBuilder.fill(0, 0))), simState, MatBuilder.fill(Nat.N1(), Nat.N1(), inputTorqueCurrent), dt);
        simState = VecBuilder.fill(updatedState.get(0, 0), updatedState.get(1, 0));
        if(simState.get(0)<=0){
            simState.set(1, 0, 0);
            simState.set(0, 0, 0);
        }
        if (simState.get(0)>= 0.762){
            simState.set(1, 0, 0);
            simState.set(0, 0, 0.762);{
            
        }
    }}
}
