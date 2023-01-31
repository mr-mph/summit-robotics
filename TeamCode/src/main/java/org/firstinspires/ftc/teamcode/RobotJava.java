package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.acmerobotics.dashboard.FtcDashboard;



@TeleOp(name = "Robot Java")
public class RobotJava extends LinearOpMode {
	private final FtcDashboard dashboard = FtcDashboard.getInstance();
	@Override
	public void runOpMode() {

		Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());
		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.initializeDrivetrain();
		robot.initializeClaw();
		robot.initializeSlide();

		while (!isStopRequested()) {

			if (gamepad1.dpad_up || gamepad2.dpad_up) {
				robot.driveStraight(1);
			} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
				robot.driveStraight(-1);
			} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
				robot.driveStrafe(1);
			} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
				robot.driveStrafe(-1);
			} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
				robot.driveTurn(1.2);
			} else if (gamepad1.left_bumper || gamepad2.left_bumper) {
				robot.driveTurn(-1.2);
			} else {
				robot.driveStop();
			}

			if (gamepad1.y || gamepad2.y) {
				robot.slideToTicks(Robot.HIGH_JUNCTION_TICKS);
			} else if (gamepad1.start || gamepad2.start) {
				robot.slideToTicks(Robot.MEDIUM_JUNCTION_TICKS);
			} else if (gamepad1.share || gamepad2.share) {
				robot.slideToTicks(Robot.LOW_JUNCTION_TICKS);
			} else if (gamepad1.ps || gamepad2.ps) {
				robot.slideToTicks(Robot.GROUND_JUNCTION_TICKS);
			} else if (gamepad1.a || gamepad2.a) {
				robot.slideDown();
			}

			if (robot.clawClosed) {
				robot.clawClose();
			} else {
				robot.clawOpen();
			}

			if (gamepad2.x || gamepad1.x) {
				robot.clawClosed = false;
			} else if (gamepad2.b || gamepad1.b) {
				robot.clawClosed = true;
			}

			// Speed toggle logic
			if (gamepad1.left_trigger == 1 || gamepad2.left_trigger == 1) {
				if (robot.speedState.equals("slow")) {
					robot.speedState = "normal";
				} else {
					robot.speedState = "slow";
				}
				while (gamepad1.left_trigger == 1 || gamepad2.left_trigger == 1) {
				}
			} else if (gamepad1.right_trigger == 1 || gamepad2.right_trigger == 1 ) {
				if (robot.speedState.equals("fast")) {
					robot.speedState = "normal";
				} else {
					robot.speedState = "fast";
				}
				while (gamepad1.right_trigger == 1 || gamepad2.right_trigger == 1 ) {
				}
			}

			if (robot.speedState.equals("slow")) {
				Robot.SPEED = 0.2;
			} else if (robot.speedState.equals("fast")) {
				Robot.SPEED = 0.6;
			} else {
				Robot.SPEED = 0.4;
			}

			if (robot.slideleft.getCurrentPosition() > 100) {
				Robot.SPEED *= 0.6;
			}

			telemetry.addData("slideleft", robot.slideleft.getCurrentPosition());
			telemetry.addData("slideright", robot.slideright.getCurrentPosition());
			telemetry.addData("slideleftpower", robot.slideleft.getPower());
			telemetry.addData("sliderightpower", robot.slideright.getPower());
			telemetry.addData("IsClawClosed", robot.clawClosed);
			telemetry.update();
		}
	}
}