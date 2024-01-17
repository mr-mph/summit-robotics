package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@TeleOp(name = "!!Headless Robot", group = "Teleop")
public class HeadlessTeleOp extends LinearOpMode {

	private boolean dpadUpHeld = false;

	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);


		robot.drive.init();
		robot.drone.init();
		robot.claw.init();
		robot.wrist.init();

		sleep(1000);
		robot.arm.init();

		waitForStart();
		SampleMecanumDrive drive = robot.drive.mecanumDrive;

		while (!isStopRequested()) {
			Pose2d poseEstimate = drive.getPoseEstimate();

			Vector2d input = new Vector2d(
					(-gamepad1.left_stick_y - gamepad2.left_stick_y) * Drive.SPEED,
					(-gamepad1.left_stick_x - gamepad2.left_stick_x) * Drive.SPEED
			).rotated(-poseEstimate.getHeading());

			drive.setWeightedDrivePower(
					new Pose2d(
							input.getX(),
							input.getY(),
							(-gamepad1.right_stick_x - gamepad2.right_stick_x) * Drive.SPEED * 1.2
					)
			);
			drive.update();

			robot.arm.gamepadInput(gamepad1, gamepad2);
			robot.claw.gamepadInput(gamepad1, gamepad2);
			robot.drive.gamepadInput(gamepad1, gamepad2);
			robot.drone.gamepadInput(gamepad1, gamepad2, time);
			robot.wrist.gamepadInput(gamepad1, gamepad2);

			if ((gamepad1.dpad_up || gamepad2.dpad_up)) {
				if (!dpadUpHeld) {
					dpadUpHeld = true;
					if (gamepad1.y || gamepad2.y) {
						robot.raiseArm();
					} else if (gamepad1.b || gamepad2.b) {
						robot.hang();
					} else {
						robot.lowerArm();
					}
				}
			} else {
				dpadUpHeld = false;
			}
			if (gamepad1.dpad_down || gamepad2.dpad_down) {
				robot.arm.armToTicks(Arm.PICKUP_TICKS);
			}

			robot.sendTelemetry(telemetry);
		}
	}
}