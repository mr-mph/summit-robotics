package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;

@TeleOp(name = "!Extender Test")
public class ExtenderTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		robot.extender.init();
		waitForStart();

		while (!isStopRequested()) {

			robot.extender.gamepadInput(gamepad1, gamepad2);



			boolean finger = false;

			// Display finger 1 x & y position if finger detected
			if(gamepad1.touchpad_finger_1)
			{
				finger = true;
				telemetry.addLine(String.format("Finger 1: x=%5.2f y=%5.2f\n", gamepad1.touchpad_finger_1_x, gamepad1.touchpad_finger_1_y));

			}

			// Display finger 2 x & y position if finger detected
			if(gamepad1.touchpad_finger_2)
			{
				finger = true;
				telemetry.addLine(String.format("Finger 2: x=%5.2f y=%5.2f\n", gamepad1.touchpad_finger_2_x, gamepad1.touchpad_finger_2_y));
			}

			if(!finger)
			{
				telemetry.addLine("No fingers");
			}

			telemetry.update();


		}
	}
}