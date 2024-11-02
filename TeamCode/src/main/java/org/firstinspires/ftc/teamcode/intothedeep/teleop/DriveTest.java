package org.firstinspires.ftc.teamcode.intothedeep.teleop;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Drive;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;

@TeleOp(name = "!Drive Test", group = "! Teleop")
public class DriveTest extends LinearOpMode {


	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);
		DcMotor arm = hardwareMap.get(DcMotor.class, "arm");

		robot.drive.init(new Pose2d(new Vector2d(18,-63),-90));

		waitForStart();

		while (!isStopRequested()) {
			robot.drive.mecanumDrive.setDrivePowers(
				new PoseVelocity2d(new Vector2d(
					(-gamepad1.left_stick_y - gamepad2.left_stick_y) * Drive.SPEED,
					(-gamepad1.left_stick_x - gamepad2.left_stick_x) * Drive.SPEED),
				(-gamepad1.right_stick_x - gamepad2.right_stick_x) * Drive.SPEED * 1.2
		));
			robot.drive.mecanumDrive.updatePoseEstimate();
			robot.drive.gamepadInput(gamepad1, gamepad2);

			robot.sendTelemetry(telemetry);

		}
	}
}