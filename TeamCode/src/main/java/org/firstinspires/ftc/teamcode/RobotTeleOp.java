package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "!Robot")
public class RobotTeleOp extends LinearOpMode {
	@Override
	public void runOpMode() {

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

			

			robot.handleSlide(gamepad1, gamepad2);
			robot.handleClaw(gamepad1, gamepad2);
			robot.handleSpeedOriginal(gamepad1, gamepad2);
			robot.sendTelemetry(telemetry);
		}
	}
}