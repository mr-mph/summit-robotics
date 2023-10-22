package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;


@TeleOp(name = "!Arm Test")
public class ArmTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.arm.init();


		while (!isStopRequested()) {
			robot.arm.gamepadInput(gamepad1, gamepad2);
		}
	}
}