package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Wrist {
	public static double WRIST_DOWN = 0.32; // old is 0.6
	public static double WRIST_PRECISE_BACKDROP = 0.03;
	public static double WRIST_UP = -0.73;
	public static double WRIST_HANG = -1;

	public CRServo wrist;

	public String wristState = "down";
	public boolean initialized = false;

	private final HardwareMap hardwareMap;

	public Wrist(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init(boolean isAuto) {
		wrist = hardwareMap.get(CRServo.class, "wrist");
		wrist.setPower(isAuto ? WRIST_HANG : WRIST_DOWN);
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (Math.abs(gamepad1.right_stick_y) > 0.3 || Math.abs(gamepad2.right_stick_y) > 0.3) {
			wrist.setPower(wrist.getPower() + 0.005 * gamepad1.right_stick_y + 0.005 * gamepad2.right_stick_y);
		} else {

			if (wristState.equals("up")) {
				wrist.setPower(WRIST_UP);
			} else if (wristState.equals("down")) {
				wrist.setPower(WRIST_DOWN);
			} else if (wristState.equals("hang")) {
				wrist.setPower(WRIST_HANG);
			}
		}
	}

	public void lift() {
		wristState = "up";
		wrist.setPower(WRIST_UP);
	}

	public void lower() {
		wristState = "down";
		wrist.setPower(WRIST_DOWN);
	}
	public void hang() {
		wristState = "hang";
		wrist.setPower(WRIST_HANG);
	}
}
