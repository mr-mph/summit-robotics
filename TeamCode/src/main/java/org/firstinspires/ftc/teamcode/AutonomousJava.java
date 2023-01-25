package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
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

		Trajectory location2 = drive.trajectoryBuilder(new Pose2d()) // middle
				.back(25)
				.build();

		Trajectory location1 = drive.trajectoryBuilder(location2.end()) // left
				.strafeRight(20)
				.build();


		Trajectory location3 = drive.trajectoryBuilder(location2.end()) // right
				.strafeLeft(25)
				.build();

		waitForStart();
//		initializeSlide();
		drive.followTrajectory(location2);

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
}
