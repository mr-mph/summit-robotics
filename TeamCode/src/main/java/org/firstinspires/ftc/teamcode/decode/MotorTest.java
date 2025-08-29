package org.firstinspires.ftc.teamcode.decode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "!!Motor Test (New)")
public class MotorTest extends LinearOpMode {
	@Override
	public void runOpMode() {
		// after init before start

		DcMotorEx testMotor = hardwareMap.get(DcMotorEx.class, "fireTestMotor");

		waitForStart();
		// one time after start

		while (!isStopRequested()) {
			// runs continuously until stop
			testMotor.setPower(-gamepad1.left_stick_y);

		}
	}
}