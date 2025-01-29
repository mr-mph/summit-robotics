package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Twist2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Drive;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Wrist;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Claw;



@TeleOp(name = "Field-Centric Robot")
public class FieldCentric extends LinearOpMode {

	Robot robot;


	@Override
	public void runOpMode() {


		robot = new Robot(hardwareMap);

		robot.drive.init(new Pose2d(6,-63, Math.toRadians(90)));
		robot.claw.init();
		robot.wrist.init(true);

		sleep(1000);
		robot.arm.init();

		waitForStart();
		wall();


		while (!isStopRequested()) {
			double driveX = (gamepad1.left_stick_x + gamepad2.left_stick_x) * Drive.SPEED;
			double driveY = (-gamepad1.left_stick_y - gamepad2.left_stick_y) * Drive.SPEED;
			double turn = (-gamepad1.right_stick_x - gamepad2.right_stick_x) * Drive.SPEED * 0.8;

			// Rotate the input based on robot's heading
			double heading = -robot.drive.mecanumDrive.pose.heading.toDouble();
			double cosHeading = Math.cos(heading);
			double sinHeading = Math.sin(heading);

			Vector2d rotatedInput = new Vector2d(
					(driveX * cosHeading - driveY * sinHeading) ,
					driveX * sinHeading + driveY * cosHeading * 1.5
			);

			// Pass the corrected inputs to the mecanum drive
			robot.drive.mecanumDrive.setDrivePowers(
					new PoseVelocity2d(rotatedInput, turn)
			);

			// Update the pose estimate
			robot.drive.mecanumDrive.updatePoseEstimate();


			robot.arm.gamepadInput(gamepad1, gamepad2);
			robot.claw.gamepadInput(gamepad1, gamepad2);
			robot.drive.gamepadInput(gamepad1, gamepad2);
			robot.wrist.gamepadInput(gamepad1, gamepad2);

			if (gamepad1.dpad_up || gamepad2.dpad_up) {
				high_rung();
				robot.drive.speedState = "fast";
			} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
				wall();
				robot.drive.speedState = "normal";
			} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
				pickup();
				robot.drive.speedState = "normal";
			} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
				low_basket();
			} else if (gamepad1.x || gamepad2.x) {
				if (gamepad1.a || gamepad2.a) {
					robot.arm.armMotor.setPower(0.2);
					robot.arm.armToTicks(-300);
				} else {
					hang();
				}
			} else if (gamepad1.share || gamepad2.share) {
				robot.arm.armMotor.setPower(1);
				robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
				robot.wrist.bringdown();
				sleep(500);
				robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
				robot.wrist.high_rung();
			}

			robot.sendTelemetry(telemetry);

		}
	}

	public void high_rung() {
		robot.arm.armMotor.setPower(1);
		robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
		sleep(200);
		robot.wrist.high_rung();
	}

	public void wall() {
		robot.wrist.wall();
		robot.arm.armMotor.setPower(1);
		robot.arm.armToTicks(Arm.WALL_TICKS);
	}

	public void pickup() {
		robot.arm.armMotor.setPower(1);
		robot.arm.armToTicks(Arm.GROUND_TICKS);
		sleep(200);
		robot.wrist.ground();
	}

	public void hang() {
		robot.wrist.hang();
		robot.arm.armMotor.setPower(1);
		robot.arm.armToTicks(Arm.HANG_TICKS);
	}

	public void low_basket() {
		robot.wrist.low_basket();
		robot.arm.armMotor.setPower(1);
		robot.arm.armToTicks(Arm.BASKET_TICKS);
	}
}