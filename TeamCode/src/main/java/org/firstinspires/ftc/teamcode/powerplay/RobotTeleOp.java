//package org.firstinspires.ftc.teamcode.powerplay;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
//
//@Disabled
//@TeleOp(name = "!Old Robot")
//public class RobotTeleOp extends LinearOpMode {
//	@Override
//	public void runOpMode() {
//
//		Robot robot = new Robot(hardwareMap);
//
//		waitForStart();
//		robot.drive.init();
//		robot.claw.init();
//		robot.slide.init();
//
//		while (!isStopRequested()) {
//
//			if (gamepad1.dpad_up || gamepad2.dpad_up) {
//				robot.drive.driveStraight(1);
//			} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
//				robot.drive.driveStraight(-1);
//			} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
//				robot.drive.driveStrafe(1);
//			} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
//				robot.drive.driveStrafe(-1);
//			} else if (gamepad1.right_bumper || gamepad2.right_bumper) {
//				robot.drive.driveTurn(1.2);
//			} else if (gamepad1.left_bumper || gamepad2.left_bumper) {
//				robot.drive.driveTurn(-1.2);
//			} else {
//				robot.drive.driveStop();
//			}
//
//
//
//			robot.slide.update(gamepad1, gamepad2);
//			robot.claw.gamepadInput(gamepad1, gamepad2);
//			robot.drive.gamepadInput(gamepad1, gamepad2);
//			robot.sendTelemetry(telemetry);
//		}
//	}
//}