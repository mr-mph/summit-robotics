package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;

@TeleOp(name = "!Wrist Test")
public class WristTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		robot.wrist.init(false);
		waitForStart();

		while (!isStopRequested()) {

			robot.wrist.gamepadInput(gamepad1, gamepad2);
			if (gamepad1.dpad_up || gamepad2.dpad_up) {
				robot.wrist.high_rung();
			} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
				robot.wrist.wall();
			} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
				robot.wrist.ground();
			} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
				robot.wrist.hang();
			}

			robot.sendTelemetry(telemetry);

		}
	}
}