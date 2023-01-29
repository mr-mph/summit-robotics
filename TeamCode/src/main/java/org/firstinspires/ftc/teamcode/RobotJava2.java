package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Robot Java 2.0")
public class RobotJava2 extends LinearOpMode {
	private final FtcDashboard dashboard = FtcDashboard.getInstance();
	@Override
	public void runOpMode() {

		Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());
		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.initializeSlide();
		robot.initializeDrivetrain();
		robot.initializeClaw();

		while (!isStopRequested()) {

			robot.drive.setWeightedDrivePower(
					new Pose2d(
							(-gamepad1.left_stick_y - gamepad2.left_stick_y) * robot.SPEED,
							(-gamepad1.left_stick_x - gamepad2.left_stick_x) * robot.SPEED,
							(-gamepad1.right_stick_x - gamepad2.right_stick_x) * robot.SPEED * 1.2
					)
			);
			robot.drive.update();

			if (gamepad1.y || gamepad2.y) {
				robot.slideToTicks(robot.HIGH_JUNCTION_TICKS);
			} else if (gamepad1.start || gamepad2.start) {
				robot.slideToTicks(robot.MEDIUM_JUNCTION_TICKS);
			} else if (gamepad1.share || gamepad2.share) {
				robot.slideToTicks(robot.LOW_JUNCTION_TICKS);
			} else if (gamepad1.ps || gamepad2.ps) {
				robot.slideToTicks(robot.GROUND_JUNCTION_TICKS);
			} else if (gamepad1.a || gamepad2.a) {
				robot.slideDown();
			}

			if (robot.clawClosed) {
				robot.clawClose();
			} else {
				robot.clawOpen();
			}

			if (gamepad1.x || gamepad2.x) {
				robot.clawClosed = false;
			} else if (gamepad1.b || gamepad2.b) {
				robot.clawClosed = true;
			}

			// Speed toggle logic
			if (gamepad1.left_bumper || gamepad2.left_bumper) {
				if (robot.speedState.equals("slow")) {
					robot.speedState = "normal";
				} else {
					robot.speedState = "slow";
				}
				while (gamepad1.left_bumper || gamepad2.left_bumper) {
				}
			} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
				if (robot.speedState.equals("fast")) {
					robot.speedState = "normal";
				} else {
					robot.speedState = "fast";
				}
				while (gamepad1.right_bumper || gamepad2.right_bumper) {
				}
			}

			if (robot.speedState.equals("slow")) {
				robot.SPEED = 0.2;
			} else if (robot.speedState.equals("fast")) {
				robot.SPEED = 0.6;
			} else {
				robot.SPEED = 0.4;
			}

			if (robot.slideleft.getCurrentPosition() > 200) {
				robot.SPEED /= ((double) robot.slideleft.getCurrentPosition() / 1000);
			}

			telemetry.addData("slideleft", robot.slideleft.getCurrentPosition());
			telemetry.addData("slideright", robot.slideright.getCurrentPosition());
			telemetry.addData("IsClawClosed", robot.clawClosed);
			telemetry.update();
		}
	}
}