package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;


@TeleOp(name = "!Wrist Test")
public class WristTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		robot.wrist.init();
		waitForStart();

		while (!isStopRequested()) {

			robot.wrist.gamepadInput(gamepad1, gamepad2);
			robot.sendTelemetry(telemetry);

		}
	}
}