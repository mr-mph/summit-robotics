package org.firstinspires.ftc.teamcode.powerplay;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Config
public class Slide {
	public static int HIGH_JUNCTION_TICKS = 1920;
	public static int MEDIUM_JUNCTION_TICKS = 1380;
	public static int LOW_JUNCTION_TICKS = 930;
	public static int GROUND_JUNCTION_TICKS = 100;

	public static int BASE_TICKS = 0;

	public static double SLIDE_UP_SPEED = 1;
	public static double SLIDE_DOWN_SPEED = 0.6;

	public DcMotor slideleft;
	public DcMotor slideright;

	public int targetTicks;

	private final HardwareMap hardwareMap;

	public Slide(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		slideleft = hardwareMap.get(DcMotorEx.class, "slideleft");
		slideright = hardwareMap.get(DcMotorEx.class, "slideright");
		slideleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		slideright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

		slideleft.setDirection(DcMotorEx.Direction.REVERSE);

		((DcMotorEx) slideleft).setTargetPositionTolerance(10);
		((DcMotorEx) slideright).setTargetPositionTolerance(10);
		slideToTicks(BASE_TICKS);
	}

	public void update(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.y || gamepad2.y) {
			slideToTicks(HIGH_JUNCTION_TICKS);
		} else if (gamepad1.start || gamepad2.start) {
			slideToTicks(MEDIUM_JUNCTION_TICKS);
		} else if (gamepad1.share || gamepad2.share) {
			slideToTicks(LOW_JUNCTION_TICKS);
		} else if (gamepad1.ps || gamepad2.ps) {
			slideToTicks(GROUND_JUNCTION_TICKS);
		} else if (gamepad1.a || gamepad2.a) {
			slideToTicks(BASE_TICKS);
		} else {
			slideToTicks(targetTicks - (int) (gamepad1.right_stick_y + gamepad2.right_stick_y) * 15);
		}
	}

	public void slideToTicks(int ticks) {
		targetTicks = ticks;
		if (slideleft.getCurrentPosition() > targetTicks) {
			slideright.setPower(SLIDE_DOWN_SPEED);
			slideleft.setPower(SLIDE_DOWN_SPEED);
		} else {
			slideright.setPower(SLIDE_UP_SPEED);
			slideleft.setPower(SLIDE_UP_SPEED);
		}

		slideright.setTargetPosition(targetTicks);
		slideleft.setTargetPosition(targetTicks);
		slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

}
