package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "Autonomous")
public class AutonomousJava extends LinearOpMode {

	public static double SERVO_CLOSED = 0.1;
	public static double SERVO_OPEN = -0.5;
	public static double SLIDE_UP_SPEED = 0.8;
	public static double SLIDE_DOWN_SPEED = 0.6;

	private CRServo clawleft;
	private CRServo clawright;

	private DcMotor slideleft;
	private DcMotor slideright;

	private ColorSensor colorsensor;

	private SampleMecanumDrive drive;


	@Override
	public void runOpMode() {

		clawleft = hardwareMap.get(CRServo.class, "clawleft");
		clawright = hardwareMap.get(CRServo.class, "clawright");
		slideleft = hardwareMap.get(DcMotor.class, "slideleft");
		slideright = hardwareMap.get(DcMotor.class, "slideright");
		colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
		drive = new SampleMecanumDrive(hardwareMap);

		Trajectory toColorsensor = drive.trajectoryBuilder(new Pose2d(35.5,62, Math.toRadians(90)))
				.lineTo(new Vector2d(36,36))
				.build();

		Trajectory location1 = drive.trajectoryBuilder(toColorsensor.end())
				.lineTo(new Vector2d(60,36))
				.build();

		Trajectory location3 = drive.trajectoryBuilder(toColorsensor.end())
				.lineTo(new Vector2d(12,36))
				.build();

		waitForStart();
//		initializeSlide();
		drive.followTrajectory(toColorsensor);

		while (!isStopRequested()) {
			int color = ((NormalizedColorSensor) colorsensor).getNormalizedColors().toColor();
			float hue = JavaUtil.colorToHue(color);
			if (hue < 30) {
				telemetry.addData("Color", "Red"); // location 1 left
				drive.followTrajectory(location1);
				requestOpModeStop();
			} else if (hue < 150) {
				telemetry.addData("Color", "Green"); // location 2 stop
				requestOpModeStop();
			} else if (hue < 225) {
				telemetry.addData("Color", "Blue"); // location 3 right
				drive.followTrajectory(location3);
				requestOpModeStop();
			}
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
