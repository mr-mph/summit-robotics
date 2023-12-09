package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;


@TeleOp(name = "!Claw Test")
public class ClawTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		robot.claw.init();
		waitForStart();

		while (!isStopRequested()) {

			robot.claw.gamepadInput(gamepad1, gamepad2);
			robot.sendTelemetry(telemetry);

		}
	}
}