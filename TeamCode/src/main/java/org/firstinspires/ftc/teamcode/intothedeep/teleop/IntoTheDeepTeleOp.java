package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Drive;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Wrist;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Claw;


@Config
@TeleOp(name = "!!Robot (Main TeleOp)")
public class IntoTheDeepTeleOp extends LinearOpMode {

	Robot robot;
	public static double touchpadSensitivity = 0.5;


	@Override
	public void runOpMode() {

		double touchpadX = 0;
		boolean touchpadPressed = false;


		robot = new Robot(hardwareMap);


		robot.drive.init(new Pose2d(6,-63, Math.toRadians(90)));
		robot.claw.init();
		robot.extender.init();


		robot.wrist.init(true);

		sleep(1000);
		robot.arm.init();

		waitForStart();
		wall();


		while (!isStopRequested()) {

			double angVel = -gamepad1.right_stick_x - gamepad2.right_stick_x;


			if (gamepad1.touchpad_finger_1) {
				if (!touchpadPressed) touchpadX = gamepad1.touchpad_finger_1_x;
				touchpadPressed = true;
				angVel += (touchpadX-gamepad1.touchpad_finger_1_x) * touchpadSensitivity;
			} else if (gamepad2.touchpad_finger_1) {
				if (!touchpadPressed) touchpadX = gamepad2.touchpad_finger_1_x;
				touchpadPressed = true;
				angVel += (touchpadX-gamepad2.touchpad_finger_1_x) * touchpadSensitivity;
			} else {
				touchpadPressed = false;
			}

            robot.drive.mecanumDrive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(
                            (-gamepad1.left_stick_y - gamepad2.left_stick_y) * Drive.SPEED,
                            (-gamepad1.left_stick_x - gamepad2.left_stick_x) * Drive.SPEED * 1.3),
                            (angVel) * Drive.SPEED * 1.2
                    )
            );


			robot.drive.mecanumDrive.updatePoseEstimate();



			robot.arm.gamepadInput(gamepad1, gamepad2);
			robot.claw.gamepadInput(gamepad1, gamepad2);
			robot.drive.gamepadInput(gamepad1, gamepad2);
			robot.wrist.gamepadInput(gamepad1, gamepad2);
			robot.extender.gamepadInput(gamepad1, gamepad2);


			if (gamepad1.dpad_up || gamepad2.dpad_up) {
                high_rung();
	            robot.drive.speedState = "normal";
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                wall();
				robot.drive.speedState = "slow";
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                pickup();
	            robot.drive.speedState = "slow";
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