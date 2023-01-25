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

		while (!isStopRequested()) {

			if (gamepad1.dpad_up || gamepad2.dpad_up) {
				driveStraight(1);
			} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
				driveStraight(-1);
			} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
				driveStrafe(1);
			} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
				driveStrafe(-1);
			} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
				driveTurn(1.2);
				leftback.setPower(SENSITIVITY * 1.2);
			} else if (gamepad1.left_bumper || gamepad2.left_bumper) {
				driveTurn(-1.2);
			} else {
				stopRobot();
			}

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

			if (gamepad2.x || gamepad1.x) {
				clawClosed = false;
			} else if (gamepad2.b || gamepad1.b) {
				clawClosed = true;
			}

			telemetry.addData("slideleft", slideleft.getCurrentPosition());
			telemetry.addData("slideright", slideright.getCurrentPosition());
			telemetry.addData("IsClawClosed", clawClosed);
			telemetry.update();
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

	private void driveStraight(double multiplier) {
		rightback.setPower(SENSITIVITY * multiplier);
		leftback.setPower(SENSITIVITY * multiplier);
		leftfront.setPower(SENSITIVITY * multiplier);
		rightfront.setPower(SENSITIVITY * multiplier);
	}

	private void driveStrafe(double multiplier) {
		rightback.setPower(SENSITIVITY * multiplier * 1.1);
		leftback.setPower(-SENSITIVITY * multiplier * 1.1);
		leftfront.setPower(SENSITIVITY * multiplier);
		rightfront.setPower(-SENSITIVITY * multiplier);
	}

	private void driveTurn(double multiplier) {
		rightback.setPower(-SENSITIVITY * multiplier);
		leftback.setPower(SENSITIVITY * multiplier);
		leftfront.setPower(SENSITIVITY * multiplier);
		rightfront.setPower(-SENSITIVITY * multiplier);
	}

	private void stopRobot() {
		rightback.setPower(0);
		leftback.setPower(0);
		leftfront.setPower(0);
		rightfront.setPower(0);
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