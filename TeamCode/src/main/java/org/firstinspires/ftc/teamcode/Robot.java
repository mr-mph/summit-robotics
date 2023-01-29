package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
public class Robot {
	public double SERVO_CLOSED = 0.1;
	public double SERVO_OPEN = -0.5;
	public int HIGH_JUNCTION_TICKS = 1600;
	public int MEDIUM_JUNCTION_TICKS = 1150;
	public int LOW_JUNCTION_TICKS = 650;
	public int GROUND_JUNCTION_TICKS = 50;
	public double SLIDE_UP_SPEED = 0.8;
	public double SLIDE_DOWN_SPEED = 0.6;
	public double SPEED = 0.4;

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

	public String speedState = "normal";

	public SampleMecanumDrive drive;
	private final HardwareMap hardwareMap;

	public Robot(HardwareMap hardwareMapParam) {
		hardwareMap = hardwareMapParam;
	}

	public void initializeSlide() {
		slideleft = hardwareMap.get(DcMotor.class, "slideleft");
		slideright = hardwareMap.get(DcMotor.class, "slideright");
		slideleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		slideright.setDirection(DcMotorSimple.Direction.REVERSE);

		slideleft.setTargetPosition(0);
		slideright.setTargetPosition(0);
		slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
		slideright.setPower(SLIDE_UP_SPEED);
		slideleft.setPower(SLIDE_UP_SPEED);
		slideright.setTargetPosition(ticks);
		slideleft.setTargetPosition(ticks);
	}

	public void slideDown() {
		slideright.setPower(SLIDE_DOWN_SPEED);
		slideleft.setPower(SLIDE_DOWN_SPEED);
		slideright.setTargetPosition(0);
		slideleft.setTargetPosition(0);
	}

	public void clawOpen() {
		clawleft.setPower(SERVO_OPEN);
		clawright.setPower(-SERVO_OPEN);
	}

	public void clawClose() {
		clawleft.setPower(SERVO_CLOSED);
		clawright.setPower(-SERVO_CLOSED);
	}
}
