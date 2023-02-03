package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Robot Java 3.0")
public class RobotJava3 extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.newInitializeSlide();
		robot.initializeDrivetrain();
		robot.initializeClaw();

		while (!isStopRequested()) {

			robot.drive.setWeightedDrivePower(
					new Pose2d(
							(-gamepad1.left_stick_y - gamepad2.left_stick_y) * Robot.SPEED,
							(-gamepad1.left_stick_x - gamepad2.left_stick_x) * Robot.SPEED,
							(-gamepad1.right_stick_x - gamepad2.right_stick_x) * Robot.SPEED * 1.2
					)
			);
			robot.drive.update();

			robot.newHandleSlide(gamepad1, gamepad2);
			robot.handleClaw(gamepad1, gamepad2);
			robot.handleSpeed(gamepad1, gamepad2);
			robot.sendTelemetry(telemetry);
		}
	}
}