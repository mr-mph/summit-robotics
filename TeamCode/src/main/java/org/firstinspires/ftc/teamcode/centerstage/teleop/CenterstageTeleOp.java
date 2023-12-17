package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;

@TeleOp(name = "!!Robot (Main TeleOp)", group = "Teleop")
public class CenterstageTeleOp extends LinearOpMode {


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


		while (!isStopRequested()) {
			robot.drive.mecanumDrive.setWeightedDrivePower(

					new Pose2d(
							(-gamepad1.left_stick_y - gamepad2.left_stick_y) * Drive.SPEED,
							(-gamepad1.left_stick_x - gamepad2.left_stick_x) * Drive.SPEED,
							(-gamepad1.right_stick_x - gamepad2.right_stick_x) * Drive.SPEED * 1.2
					)
			);
			robot.drive.mecanumDrive.update();

			robot.arm.gamepadInput(gamepad1, gamepad2);
			robot.claw.gamepadInput(gamepad1, gamepad2);
			robot.drive.gamepadInput(gamepad1, gamepad2);
			robot.drone.gamepadInput(gamepad1, gamepad2, time);
			robot.wrist.gamepadInput(gamepad1, gamepad2);

			robot.sendTelemetry(telemetry);

//			if (gamepad1.x || gamepad2.x) {
//				robot.arm.armToTicks(robot.arm.targetTicks - 200);
//				robot.claw.topClawClosed = false;
//				while (gamepad1.x || gamepad2.x) {}
//				robot.arm.armToTicks(Arm.BACKDROP_TICKS);
//			}
		}
	}
}