package org.firstinspires.ftc.teamcode.intothedeep.robot;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Extender {
	public static double HOOK_DOWN = 0.1;
	public static double HOOK_UP = 0.18;
	public static double HOOK_INIT = 1;


	public static double LINKAGE_IN = 0.45;
	public static double LINKAGE_OUT = 0.17;

	private final HardwareMap hardwareMap;

	public Servo hook;
	public Servo linkage;

	public boolean initialized = false;

	private boolean lowered = false;

	public Extender(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		hook = hardwareMap.get(Servo.class, "hook");
		linkage = hardwareMap.get(Servo.class, "hooklinkage");


		hook.setPosition(HOOK_INIT);
		linkage.setPosition(LINKAGE_IN);
		lowered = true;
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {

//		if (gamepad1.right_bumper || gamepad2.right_bumper) {
//				hook.setPosition(HOOK_UP);
//		} else if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
//				hook.setPosition(HOOK_DOWN);
//		}
//
//		if (gamepad1.left_bumper || gamepad2.left_bumper) {
//				linkage.setPosition(LINKAGE_IN);
//		} else if (gamepad1.left_trigger > 0.5 || gamepad2.left_trigger > 0.5) {
//				linkage.setPosition(LINKAGE_OUT);
//		}

		if (gamepad1.ps || gamepad2.ps) {
			if (lowered) {
				hook.setPosition(HOOK_INIT);
				lowered = false;
			} else {
				hook.setPosition(HOOK_UP);
				lowered = true;
			}
			while (gamepad1.ps || gamepad2.ps) {}
		}

		if (gamepad1.touchpad_finger_1) {
			linkage.setPosition((LINKAGE_IN + 0.02) + (((gamepad1.touchpad_finger_1_y) - (-1)) * ((LINKAGE_OUT - 0.02) - LINKAGE_IN) / (2)));
			if (gamepad1.touchpad) {
				hook.setPosition(HOOK_DOWN);
				lowered = true;
			} else if (lowered){
				hook.setPosition(HOOK_UP);
			}
		}

		if (gamepad2.touchpad_finger_1) {
			linkage.setPosition((LINKAGE_IN + 0.02) + (((gamepad2.touchpad_finger_1_y) - (-1)) * ((LINKAGE_OUT - 0.02) - LINKAGE_IN) / (2)));
			if (gamepad2.touchpad) {
				hook.setPosition(HOOK_DOWN);
				lowered = true;
			} else if (lowered) {
				hook.setPosition(HOOK_UP);
			}
		}



	}


}
