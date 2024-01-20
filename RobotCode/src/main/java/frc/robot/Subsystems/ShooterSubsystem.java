package frc.robot.Subsystems;

// import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkBase.IdleMode;

import frc.robot.constants.Constants;
import frc.robot.constants.Ports;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.core.motors.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase
{
	// creates objects
	private double shooterMotorPower, feederMotorPower;

	private final PIDController shooterPID, feederPID;
	public final TeamSparkMAX ShooterMotorMain, ShooterMotorSub, FeederMotorMain, FeederMotorSub;

	private static double voltage = 12;

	public ShooterSubsystem()
	{
		//sets default shooter motor power to o.25 and feeder to 0
		shooterMotorPower = 0.25;
		feederMotorPower = 0;
		//initializes pid
		shooterPID = new PIDController(Constants.SHOOTER_KP, Constants.SHOOTER_KI,
				Constants.SHOOTER_KD);
		feederPID = new PIDController(Constants.FEEDER_KP, Constants.FEEDER_KI,
				Constants.FEEDER_KD);
		//initializes motor object
		ShooterMotorMain = new TeamSparkMAX("Left Shooter Motor Main", Ports.SHOOTER_MOTOR_MAIN);
		ShooterMotorSub = new TeamSparkMAX("Right Shooter Motor Main", Ports.SHOOTER_MOTOR_SUB);
		FeederMotorMain = new TeamSparkMAX("Left Shooter Motor Sub", Ports.FEEDER_MOTOR_MAIN);
		FeederMotorSub = new TeamSparkMAX("Right Shooter Motor Sub", Ports.FEEDER_MOTOR_SUB);
		//the code is able to enable the unable to be able voltage compensation
		ShooterMotorMain.enableVoltageCompensation(voltage);
		ShooterMotorSub.enableVoltageCompensation(voltage);
		FeederMotorMain.enableVoltageCompensation(voltage);
		FeederMotorSub.enableVoltageCompensation(voltage);
		//makes sub motors follow main
		FeederMotorSub.follow(FeederMotorMain);
		ShooterMotorSub.follow(ShooterMotorMain);
		//  inverts the right side
		ShooterMotorSub.setInverted(true);
		FeederMotorSub.setInverted(true);
		//  sets neutral mode for the motors
		ShooterMotorSub.setIdleMode(IdleMode.kBrake);
		FeederMotorSub.setIdleMode(IdleMode.kBrake);
		ShooterMotorMain.setIdleMode(IdleMode.kBrake);
		FeederMotorMain.setIdleMode(IdleMode.kBrake);
	}
	//gets the shooter power
	public double getShooterMotorPower()
	{
		return ShooterMotorMain.get();
	}
	// sets shooter motor power variable based on mathy stuff owen told me to do
	public void setShooterMotorPower(double power, String reason)
	{
		shooterMotorPower = Math.max(Math.min(1, power), -1);

	}
	// sets feeder motor power variable with some mathy stuff owen knows
	public void setFeedMotorPower(double power, String reason)
	{
		feederMotorPower = Math.max(Math.min(1, power), -1);
	}
	//continuously updates shooter speed based on the commands above
	public void periodic()
	{
		double newShooterPower = shooterPID.calculate(ShooterMotorSub.get(), shooterMotorPower);
		ShooterMotorMain.set(newShooterPower);

		double newFeederPower = feederPID.calculate(FeederMotorSub.get(), feederMotorPower);
		FeederMotorMain.set(newFeederPower);
	}
}
