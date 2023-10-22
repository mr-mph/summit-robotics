package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;

@TeleOp(name = "!Drone Test", group = "Test")
public class DroneTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		robot.drone.init();
		waitForStart();
		robot.drone.init();


		while (!isStopRequested()) {
			robot.drone.gamepadInput(gamepad1, gamepad2);
			robot.sendTelemetry(telemetry);
		}
	}
}