package org.firstinspires.ftc.teamcode.powerplay;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name = "!Test Slide")
public class TestSlide extends LinearOpMode {
	@Override
	public void runOpMode() {

		waitForStart();
		DcMotorEx slideleft = hardwareMap.get(DcMotorEx.class, "slideleft");
		DcMotorEx slideright = hardwareMap.get(DcMotorEx.class, "slideright");
		slideleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		slideright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


		while (!isStopRequested()) {

			slideleft.setPower(-gamepad1.left_stick_y - gamepad2.left_stick_y);
			slideright.setPower(-gamepad1.right_stick_y - gamepad2.right_stick_y);
		}
	}
}