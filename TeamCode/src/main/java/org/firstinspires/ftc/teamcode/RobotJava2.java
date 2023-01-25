package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@TeleOp(name = "Robot Java 2.0")
public class RobotJava2 extends LinearOpMode {

	public static double SERVO_CLOSED = 0.1;
	public static double SERVO_OPEN = -0.5;
	public static int HIGH_JUNCTION_TICKS = 1600;
	public static int MEDIUM_JUNCTION_TICKS = 1150;
	public static int LOW_JUNCTION_TICKS = 650;
	public static int GROUND_JUNCTION_TICKS = 50;
	public static double SLIDE_UP_SPEED = 0.8;
	public static double SLIDE_DOWN_SPEED = 0.6;
	public static double SPEED = 0.4;

	private CRServo clawleft;
	private CRServo clawright;

	private DcMotor slideleft;
	private DcMotor slideright;

	private boolean clawClosed = false;
	private String speedState = "normal";

	@Override
	public void runOpMode() {

		SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

		drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		clawleft = hardwareMap.get(CRServo.class, "clawleft");
		clawright = hardwareMap.get(CRServo.class, "clawright");

		slideleft = hardwareMap.get(DcMotor.class, "slideleft");
		slideright = hardwareMap.get(DcMotor.class, "slideright");

		waitForStart();
		initializeSlide();

		while (!isStopRequested()) {

			drive.setWeightedDrivePower(
					new Pose2d(
							(-gamepad1.left_stick_y - gamepad2.left_stick_y) * SPEED,
							(-gamepad1.left_stick_x - gamepad2.left_stick_x) * SPEED,
							(-gamepad1.right_stick_x - gamepad2.right_stick_x) * SPEED * 1.2
					)
			);
			drive.update();

			if (gamepad1.y || gamepad2.y) {
				slideToTicks(HIGH_JUNCTION_TICKS);
			} else if (gamepad1.start || gamepad2.start) {
				slideToTicks(MEDIUM_JUNCTION_TICKS);
			} else if (gamepad1.share || gamepad2.share) {
				slideToTicks(LOW_JUNCTION_TICKS);
			} else if (gamepad1.ps || gamepad2.ps) {
				slideToTicks(GROUND_JUNCTION_TICKS);
			} else if (gamepad1.a || gamepad2.a) {
				slideDown();
			}

			if (clawClosed) {
				clawClosed();
			} else {
				clawOpen();
			}

			if (gamepad1.x || gamepad2.x) {
				clawClosed = false;
			} else if (gamepad1.b || gamepad2.b) {
				clawClosed = true;
			}

			// Speed toggle logic
			if (gamepad1.left_bumper || gamepad2.left_bumper) {
				if (speedState.equals("slow")) {
					speedState = "normal";
				} else {
					speedState = "slow";
				}
				while (gamepad1.left_bumper || gamepad2.left_bumper) {
				}
			} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
				if (speedState.equals("fast")) {
					speedState = "normal";
				} else {
					speedState = "fast";
				}
				while (gamepad1.right_bumper || gamepad2.right_bumper) {
				}
			}

			if (speedState.equals("slow")) {
				SPEED = 0.2;
			} else if (speedState.equals("fast")) {
				SPEED = 0.6;
			} else {
				SPEED = 0.4;
			}

			telemetry.addData("slideleft", slideleft.getCurrentPosition());
			telemetry.addData("slideright", slideright.getCurrentPosition());
			telemetry.addData("IsClawClosed", clawClosed);
			telemetry.update();
		}
	}
	void initializeSlide() {
		slideleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		slideleft.setTargetPosition(0);
		slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		slideright.setTargetPosition(0);
		slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

	private void slideToTicks(int ticks) {
		slideright.setPower(SLIDE_UP_SPEED);
		slideleft.setPower(SLIDE_UP_SPEED);
		slideright.setTargetPosition(-ticks);
		slideleft.setTargetPosition(ticks);
	}

	private void slideDown() {
		slideright.setPower(SLIDE_DOWN_SPEED);
		slideleft.setPower(SLIDE_DOWN_SPEED);
		slideright.setTargetPosition(0);
		slideleft.setTargetPosition(0);
	}

	private void clawOpen() {
		clawleft.setPower(SERVO_OPEN);
		clawright.setPower(-SERVO_OPEN);
	}

	private void clawClosed() {
		clawleft.setPower(SERVO_CLOSED);
		clawright.setPower(-SERVO_CLOSED);
	}
}