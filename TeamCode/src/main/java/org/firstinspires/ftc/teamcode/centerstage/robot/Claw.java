package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Claw {
	public static double SERVO_CLOSED = 0.1;
	public static double SERVO_OPEN = -0.5;

	public CRServo clawleft;
	public CRServo clawright;

	public boolean clawClosed = false;

	private final HardwareMap hardwareMap;

	public Claw(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		clawleft = hardwareMap.get(CRServo.class, "clawleft");
		clawright = hardwareMap.get(CRServo.class, "clawright");
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad2.x || gamepad1.x) {
			clawClosed = false;
		} else if (gamepad2.b || gamepad1.b) {
			clawClosed = true;
		}

		if (clawClosed) {
			close();
		} else {
			open();
		}
	}

	public void open() {
		clawleft.setPower(SERVO_OPEN);
		clawright.setPower(-SERVO_OPEN);
	}

	public void close() {
		clawleft.setPower(SERVO_CLOSED);
		clawright.setPower(-SERVO_CLOSED);
	}
}
