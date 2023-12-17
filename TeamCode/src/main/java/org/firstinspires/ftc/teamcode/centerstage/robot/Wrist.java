package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Wrist {
	public static double WRIST_DOWN = 0.66;
	public static double WRIST_UP = -0.7;

	public CRServo wrist;

	public boolean wristUp = false;
	public boolean initialized = false;

	private final HardwareMap hardwareMap;

	public Wrist(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		wrist = hardwareMap.get(CRServo.class, "wrist");
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad2.right_trigger > 0.5 || gamepad1.right_trigger > 0.5) {
				wristUp = !wristUp;
				while (gamepad2.right_trigger > 0.5 || gamepad1.right_trigger > 0.5);
		}
		if (wristUp) {
			lift();

		} else {
			lower();
		}
	}

	public void lift() {
		wrist.setPower(WRIST_UP);
	}

	public void lower() {
		wrist.setPower(WRIST_DOWN);
	}
}
