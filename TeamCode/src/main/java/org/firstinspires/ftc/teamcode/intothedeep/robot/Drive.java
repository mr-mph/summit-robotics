package org.firstinspires.ftc.teamcode.intothedeep.robot;

import com.acmerobotics.dashboard.config.Config;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
public class Drive {
	public static double SPEED = 0.4;

	public DcMotorEx rightback;
	public DcMotorEx rightfront;
	public DcMotorEx leftback;
	public DcMotorEx leftfront;

	public String speedState = "normal";

	public boolean initialized = false;

	public MecanumDrive mecanumDrive;
	private final HardwareMap hardwareMap;

	public Drive(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init(Pose2d startPose) {
		rightback = hardwareMap.get(DcMotorEx.class, "rightback");
		rightfront = hardwareMap.get(DcMotorEx.class, "rightfront");
		leftback = hardwareMap.get(DcMotorEx.class, "leftback");
		leftfront = hardwareMap.get(DcMotorEx.class, "leftfront");

		mecanumDrive = new MecanumDrive(hardwareMap, startPose);
//		mecanumDrive.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//		mecanumDrive.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

		initialized = true;

	}

//	public void lockTo(Pose2d targetPos) {
//		// from FTC team "Don't Blink"
//		Pose2d currentPos = mecanumDrive.getPoseEstimate();
//		Pose2d difference = targetPos.minus(currentPos);
//		Vector2d deltaVec = difference.vec().rotated(-currentPos.getHeading());
//
//		double deltaHeading = Angle.normDelta(targetPos.getHeading()) - Angle.normDelta(currentPos.getHeading());
//		mecanumDrive.setWeightedDrivePower(new Pose2d(deltaVec, deltaHeading));
//		mecanumDrive.update();
//	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.y || gamepad2.y) {
			if (speedState.equals("slow")) {
				speedState = "normal";
			} else {
				speedState = "fast";
			}
			while (gamepad1.y || gamepad2.y);

		} else if (gamepad1.b || gamepad2.b ) {
			if (speedState.equals("fast")) {
				speedState = "normal";
			} else {
				speedState = "slow";
			}
			while (gamepad1.b || gamepad2.b);
		}

		updateSpeed();
	}

	private void updateSpeed() {
		if (speedState.equals("slow")) {
			SPEED = 0.2;
		} else if (speedState.equals("fast")) {
			SPEED = 0.6;
		} else {
			SPEED = 0.4;
		}
	}

	public void driveStraight(double multiplier) {
		rightback.setPower(SPEED * multiplier);
		leftback.setPower(SPEED * multiplier);
		leftfront.setPower(SPEED * multiplier);
		rightfront.setPower(SPEED * multiplier);
	}

	public void driveStrafe(double multiplier) {
		rightback.setPower(SPEED * multiplier);
		leftback.setPower(-SPEED * multiplier);
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
