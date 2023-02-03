package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

	public static double SLIDE_UP_SPEED = 0.8;
	public static double SLIDE_DOWN_SPEED = 0.6;
	public static double SPEED = 0.4;

	public DcMotor rightback;
	public DcMotor rightfront;
	public DcMotor leftback;
	public DcMotor leftfront;

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
		slideleft = hardwareMap.get(DcMotor.class, "slideleft");
		slideright = hardwareMap.get(DcMotor.class, "slideright");
		slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		slideright.setDirection(DcMotorSimple.Direction.REVERSE);

		slideleft.setTargetPosition(BASE_TICKS);
		slideright.setTargetPosition(BASE_TICKS);
		slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

	public void newInitializeSlide() {
		slideleft = hardwareMap.get(DcMotor.class, "slideleft");
		slideright = hardwareMap.get(DcMotor.class, "slideright");
		slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		slideright.setDirection(DcMotorSimple.Direction.REVERSE);

		targetTicks = BASE_TICKS;
		slideleft.setTargetPosition(targetTicks);
		slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		slideright.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

	}
	public void initializeClaw() {
		clawleft = hardwareMap.get(CRServo.class, "clawleft");
		clawright = hardwareMap.get(CRServo.class, "clawright");
	}

	public void initializeDrivetrain() {
		colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");

		rightback = hardwareMap.get(DcMotor.class, "rightback");
		rightfront = hardwareMap.get(DcMotor.class, "rightfront");
		leftback = hardwareMap.get(DcMotor.class, "leftback");
		leftfront = hardwareMap.get(DcMotor.class, "leftfront");

		drive = new SampleMecanumDrive(hardwareMap);
		drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

		leftback.setDirection(DcMotorSimple.Direction.REVERSE);
		leftfront.setDirection(DcMotorSimple.Direction.REVERSE);

		rightback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		leftback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		leftfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		rightfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
		if (ticks < slideleft.getCurrentPosition()) {
			slideright.setPower(SLIDE_DOWN_SPEED);
			slideleft.setPower(SLIDE_DOWN_SPEED);
		} else {
			slideright.setPower(SLIDE_UP_SPEED);
			slideleft.setPower(SLIDE_UP_SPEED);
		}
		slideright.setTargetPosition(ticks);
		slideleft.setTargetPosition(ticks);
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

		if (speedState.equals("slow")) {
			SPEED = 0.2;
		} else if (speedState.equals("fast")) {
			SPEED = 0.6;
		} else {
			SPEED = 0.4;
		}

		if (slideleft.getCurrentPosition() > 100) {
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
		telemetry.addData("slideleft", slideleft.getCurrentPosition());
		telemetry.addData("slideright", slideright.getCurrentPosition());
		telemetry.addData("slideleftpower", slideleft.getPower());
		telemetry.addData("sliderightpower", slideright.getPower());
		telemetry.addData("IsClawClosed", clawClosed);
		telemetry.update();
	}

	public void newHandleSlide(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.y || gamepad2.y) {
			targetTicks = HIGH_JUNCTION_TICKS;
		} else if (gamepad1.start || gamepad2.start) {
			targetTicks = MEDIUM_JUNCTION_TICKS;
		} else if (gamepad1.share || gamepad2.share) {
			targetTicks = LOW_JUNCTION_TICKS;
		} else if (gamepad1.ps || gamepad2.ps) {
			targetTicks = GROUND_JUNCTION_TICKS;
		} else if (gamepad1.a || gamepad2.a) {
			targetTicks = BASE_TICKS;
		}
		if (slideleft.getCurrentPosition() > targetTicks) {
			slideleft.setPower(SLIDE_DOWN_SPEED);
		} else {
			slideleft.setPower(SLIDE_UP_SPEED);
		}

		slideleft.setTargetPosition(targetTicks);
		slideright.setPower(slideleft.getPower());
	}
}
