package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Config
public class Drive {
	public static double SPEED = 0.6;

	public DcMotorEx rightback;
	public DcMotorEx rightfront;
	public DcMotorEx leftback;
	public DcMotorEx leftfront;

	public String speedState = "normal";

	public SampleMecanumDrive mecanumDrive;
	private final HardwareMap hardwareMap;

	public Drive(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		rightback = hardwareMap.get(DcMotorEx.class, "rightback");
		rightfront = hardwareMap.get(DcMotorEx.class, "rightfront");
		leftback = hardwareMap.get(DcMotorEx.class, "leftback");
		leftfront = hardwareMap.get(DcMotorEx.class, "leftfront");

		mecanumDrive = new SampleMecanumDrive(hardwareMap);
		mecanumDrive.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

		leftback.setDirection(DcMotorEx.Direction.REVERSE);
		leftfront.setDirection(DcMotorEx.Direction.REVERSE);

		rightback.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		leftback.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		leftfront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		rightfront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.left_bumper || gamepad2.left_bumper) {
			if (speedState.equals("slow")) {
				speedState = "normal";
			} else {
				speedState = "slow";
			}
			while (gamepad1.left_bumper || gamepad2.left_bumper);

		} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
			if (speedState.equals("fast")) {
				speedState = "normal";
			} else {
				speedState = "fast";
			}

			while (gamepad1.right_bumper || gamepad2.right_bumper);
		}

		updateSpeed();
	}

	private void updateSpeed() {
		if (speedState.equals("slow")) {
			SPEED = 0.2;
		} else if (speedState.equals("fast")) {
			SPEED = 1;
		} else {
			SPEED = 0.6;
		}
	}

	public void driveStraight(double multiplier) {
		rightback.setPower(SPEED * multiplier);
		leftback.setPower(SPEED * multiplier);
		leftfront.setPower(SPEED * multiplier);
		rightfront.setPower(SPEED * multiplier);
	}

	public void driveStrafe(double multiplier) {
		rightback.setPower(SPEED * multiplier * 1.1);
		leftback.setPower(-SPEED * multiplier * 1.1);
		leftfront.setPower(SPEED * multiplier);
		rightfront.setPower(-SPEED * multiplier);
	}

	public void driveTurn(double multiplier) {
		rightback.setPower(-SPEED * multiplier);
		leftback.setPower(SPEED * multiplier);
		leftfront.setPower(SPEED * multiplier);
		rightfront.setPower(-SPEED * multiplier);
	}

	public void driveStop() {
		rightback.setPower(0);
		leftback.setPower(0);
		leftfront.setPower(0);
		rightfront.setPower(0);
	}
}
