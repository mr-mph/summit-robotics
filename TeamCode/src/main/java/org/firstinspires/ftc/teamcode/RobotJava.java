package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp(name = "Robot Java")
public class RobotJava extends LinearOpMode {

	public static double SERVO_CLOSED = 0.1;
	public static double SERVO_OPEN = -0.5;
	public static int HIGH_JUNCTION_TICKS = 1600;
	public static int MEDIUM_JUNCTION_TICKS = 1150;
	public static int LOW_JUNCTION_TICKS = 650;
	public static int GROUND_JUNCTION_TICKS = 50;
	public static double SLIDE_UP_SPEED = 0.8;
	public static double SLIDE_DOWN_SPEED = 0.6;
	public static double SENSITIVITY = 0.4;

	private DcMotor rightback;
	private DcMotor rightfront;
	private DcMotor leftback;
	private DcMotor leftfront;

	private CRServo clawleft;
	private CRServo clawright;
	private DcMotor slideleft;
	private DcMotor slideright;

	private boolean clawClosed = false;


	@Override
	public void runOpMode() {

		rightback = hardwareMap.get(DcMotor.class, "rightback");
		rightfront = hardwareMap.get(DcMotor.class, "rightfront");
		leftback = hardwareMap.get(DcMotor.class, "leftback");
		leftfront = hardwareMap.get(DcMotor.class, "leftfront");
		clawleft = hardwareMap.get(CRServo.class, "clawleft");
		clawright = hardwareMap.get(CRServo.class, "clawright");
		slideleft = hardwareMap.get(DcMotor.class, "slideleft");
		slideright = hardwareMap.get(DcMotor.class, "slideright");

		waitForStart();

		initializeDrive();

		initializeSlide();

		if (opModeIsActive()) {
			// Put run code here.
			while (opModeIsActive()) {

				if (gamepad1.dpad_down || gamepad2.dpad_down) {
					rightback.setPower(-SENSITIVITY);
					leftback.setPower(-SENSITIVITY);
					leftfront.setPower(-SENSITIVITY);
					rightfront.setPower(-SENSITIVITY);
				} else if (gamepad1.dpad_up || gamepad2.dpad_up) {
					rightback.setPower(SENSITIVITY);
					leftback.setPower(SENSITIVITY);
					leftfront.setPower(SENSITIVITY);
					rightfront.setPower(SENSITIVITY);
				} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
					rightback.setPower(SENSITIVITY * 1.1);
					leftback.setPower(-SENSITIVITY * 1.1);
					leftfront.setPower(SENSITIVITY);
					rightfront.setPower(-SENSITIVITY);
				} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
					rightback.setPower(-SENSITIVITY * 1.1);
					leftback.setPower(SENSITIVITY * 1.1);
					leftfront.setPower(-SENSITIVITY);
					rightfront.setPower(SENSITIVITY);
				} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
					rightback.setPower(-SENSITIVITY * 1.2);
					rightfront.setPower(-SENSITIVITY * 1.2);
					leftfront.setPower(SENSITIVITY * 1.2);
					leftback.setPower(SENSITIVITY * 1.2);
				} else if (gamepad1.left_bumper || gamepad2.left_bumper) {
					rightback.setPower(SENSITIVITY * 1.2);
					rightfront.setPower(SENSITIVITY * 1.2);
					leftfront.setPower(-SENSITIVITY * 1.2);
					leftback.setPower(-SENSITIVITY * 1.2);
				} else {
					rightback.setPower(0);
					leftback.setPower(0);
					leftfront.setPower(0);
					rightfront.setPower(0);
				}

				if (gamepad1.y || gamepad2.y) {
					slideright.setPower(SLIDE_UP_SPEED);
					slideleft.setPower(SLIDE_UP_SPEED);
					slideright.setTargetPosition(-HIGH_JUNCTION_TICKS);
					slideleft.setTargetPosition(HIGH_JUNCTION_TICKS);
				} else if (gamepad1.a || gamepad2.a) {
					slideright.setPower(SLIDE_DOWN_SPEED);
					slideleft.setPower(SLIDE_DOWN_SPEED);
					slideright.setTargetPosition(0);
					slideleft.setTargetPosition(0);
				} else if (gamepad1.start || gamepad2.start) {
					slideright.setPower(SLIDE_UP_SPEED);
					slideleft.setPower(SLIDE_UP_SPEED);
					slideright.setTargetPosition(-MEDIUM_JUNCTION_TICKS);
					slideleft.setTargetPosition(MEDIUM_JUNCTION_TICKS);
				} else if (gamepad1.share || gamepad2.share) {
					slideright.setPower(SLIDE_UP_SPEED);
					slideleft.setPower(SLIDE_UP_SPEED);
					slideright.setTargetPosition(-LOW_JUNCTION_TICKS);
					slideleft.setTargetPosition(LOW_JUNCTION_TICKS);
				} else if (gamepad1.ps || gamepad2.ps) {
					slideright.setPower(SLIDE_UP_SPEED);
					slideleft.setPower(SLIDE_UP_SPEED);
					slideright.setTargetPosition(-GROUND_JUNCTION_TICKS);
					slideleft.setTargetPosition(GROUND_JUNCTION_TICKS);
				}

				if (clawClosed) {
					clawleft.setPower(SERVO_CLOSED);
					clawright.setPower(-SERVO_CLOSED);
				} else {
					clawleft.setPower(SERVO_OPEN);
					clawright.setPower(-SERVO_OPEN);
				}

				if (gamepad2.x || gamepad1.x) {
					clawClosed = false;
				} else if (gamepad2.b || gamepad1.b) {
					clawClosed = true;
				}

				telemetry.addData("slideleft", slideleft.getCurrentPosition());
				telemetry.addData("slideright", slideright.getCurrentPosition());
				telemetry.addData("servoleft", clawleft.getPower());
				telemetry.addData("servoright", clawright.getPower());
				telemetry.addData("IsClawClosed", clawClosed);
				telemetry.update();
			}
		}
	}
	private void initializeSlide() {
		slideleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		slideleft.setTargetPosition(0);
		slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		slideright.setTargetPosition(0);
		slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}
	private void initializeDrive() {
		leftback.setDirection(DcMotorSimple.Direction.REVERSE);
		leftfront.setDirection(DcMotorSimple.Direction.REVERSE);

		rightback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		leftback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		leftfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		rightfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
	}
}