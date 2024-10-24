package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Drive;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;

@TeleOp(name = "!!Robot (Main TeleOp)", group = "! Teleop")
public class IntoTheDeepTeleOp extends LinearOpMode {


	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		robot.drive.init();
		robot.claw.init();
		robot.wrist.init(true);

		sleep(1000);
		robot.arm.init();

		waitForStart();


		while (!isStopRequested()) {
            robot.drive.mecanumDrive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(
                            (-gamepad1.left_stick_y - gamepad2.left_stick_y) * Drive.SPEED,
                            (-gamepad1.left_stick_x - gamepad2.left_stick_x) * Drive.SPEED),
                            (-gamepad1.right_stick_x - gamepad2.right_stick_x) * Drive.SPEED * 1.2
                    ));
            robot.drive.mecanumDrive.updatePoseEstimate();

			robot.arm.gamepadInput(gamepad1, gamepad2);
			robot.claw.gamepadInput(gamepad1, gamepad2);
			robot.drive.gamepadInput(gamepad1, gamepad2);
			robot.wrist.gamepadInput(gamepad1, gamepad2);

            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                robot.high_rung();
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                robot.wall();
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                robot.pickup();
            } else if (gamepad1.dpad_left || gamepad2.dpad_left) {
                robot.hang();
            }

			robot.sendTelemetry(telemetry);

		}
	}
}