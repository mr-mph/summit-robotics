package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
public class Robot {
	public static double SERVO_CLOSED = 0.1;
	public static double SERVO_OPEN = -0.5;
	public static int HIGH_JUNCTION_TICKS = 1600;
	public static int MEDIUM_JUNCTION_TICKS = 1150;
	public static int LOW_JUNCTION_TICKS = 650;
	public static int GROUND_JUNCTION_TICKS = 50;
	public static int BASE_TICKS = 0;

	public static double SLIDE_UP_SPEED = 0.6;
	public static double SLIDE_DOWN_SPEED = 0.4;
	public static double SPEED = 0.4;

	public DcMotorEx rightback;
	public DcMotorEx rightfront;
	public DcMotorEx leftback;
	public DcMotorEx leftfront;

	public CRServo clawleft;
	public CRServo clawright;

	public DcMotor slideleft;
	public DcMotor slideright;

	public ColorSensor colorsensor;

	public boolean clawClosed = false;

	public int targetTicks;

	public String speedState = "normal";

	public SampleMecanumDrive drive;
	private final HardwareMap hardwareMap;

	public Robot(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}


	public void initializeSlide() {
		slideleft = hardwareMap.get(DcMotorEx.class, "slideleft");
		slideright = hardwareMap.get(DcMotorEx.class, "slideright");
		slideleft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		slideright.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

		slideleft.setDirection(DcMotorEx.Direction.REVERSE);

		((DcMotorEx) slideleft).setTargetPositionTolerance(10);
		((DcMotorEx) slideleft).setTargetPositionTolerance(10);
		slideToTicks(BASE_TICKS);
	}

	public void initializeClaw() {
		clawleft = hardwareMap.get(CRServo.class, "clawleft");
		clawright = hardwareMap.get(CRServo.class, "clawright");
	}

	public void initializeDrivetrain() {
		colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");

		rightback = hardwareMap.get(DcMotorEx.class, "rightback");
		rightfront = hardwareMap.get(DcMotorEx.class, "rightfront");
		leftback = hardwareMap.get(DcMotorEx.class, "leftback");
		leftfront = hardwareMap.get(DcMotorEx.class, "leftfront");

		drive = new SampleMecanumDrive(hardwareMap);
		drive.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

		leftback.setDirection(DcMotorEx.Direction.REVERSE);
		leftfront.setDirection(DcMotorEx.Direction.REVERSE);

		rightback.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		leftback.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		leftfront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
		rightfront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
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

	public void clawOpen() {
		clawleft.setPower(SERVO_OPEN);
		clawright.setPower(-SERVO_OPEN);
	}

	public void clawClose() {
		clawleft.setPower(SERVO_CLOSED);
		clawright.setPower(-SERVO_CLOSED);
	}

	public void handleSpeed(Gamepad gamepad1, Gamepad gamepad2) {
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

	public void handleSpeedOriginal(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.left_trigger == 1 || gamepad2.left_trigger == 1) {
			if (speedState.equals("slow")) {
				speedState = "normal";
			} else {
				speedState = "slow";
			}
			while (gamepad1.left_trigger == 1 || gamepad2.left_trigger == 1);

		} else if (gamepad1.right_trigger == 1 || gamepad2.right_trigger == 1) {
			if (speedState.equals("fast")) {
				speedState = "normal";
			} else {
				speedState = "fast";
			}
			while (gamepad1.right_trigger == 1 || gamepad2.right_trigger == 1);

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

		if (slideleft.getCurrentPosition() > 600) {
			SPEED *= 0.6;
		}
	}

	public void handleSlide(Gamepad gamepad1, Gamepad gamepad2) {
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

	public void handleClaw(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad2.x || gamepad1.x) {
			clawClosed = false;
		} else if (gamepad2.b || gamepad1.b) {
			clawClosed = true;
		}

		if (clawClosed) {
			clawClose();
		} else {
			clawOpen();
		}
	}

	public void sendTelemetry(Telemetry telemetry) {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		telemetry.addData("leftpos", slideleft.getCurrentPosition());
		telemetry.addData("rightpos", slideright.getCurrentPosition());
		telemetry.addData("speedState", speedState);
		telemetry.update();
	}
}
