package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw {
	public static double LEFT_CLAW_OPEN = 0.42;
	public static double LEFT_CLAW_CLOSED = 0.2;
	public static double RIGHT_CLAW_OPEN = 0.23;

	public static double RIGHT_CLAW_PRECISE_OPEN = 0;
	public static double RIGHT_CLAW_CLOSED = 0.44;

	public Servo clawright;
	public Servo clawleft;

	public boolean rightClawClosed = true;
	public boolean leftClawClosed = true;
	public boolean initialized = false;

	private boolean bumperHeld = false;
	private boolean triggerHeld = false;

	private final HardwareMap hardwareMap;

	public Claw(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		clawleft = hardwareMap.get(Servo.class, "clawleft");
		clawright = hardwareMap.get(Servo.class, "clawright");

		clawleft.setPosition(LEFT_CLAW_CLOSED);
		clawright.setPosition(RIGHT_CLAW_CLOSED);
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {

		if (gamepad1.right_bumper || gamepad2.right_bumper) {
			if (!bumperHeld) {
				bumperHeld = true;
				leftClawClosed = !leftClawClosed;
			}
		} else {
			bumperHeld = false;
		}

		if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
			if (!triggerHeld) {
				triggerHeld = true;
				rightClawClosed = !rightClawClosed;
			}
		} else {
			triggerHeld = false;
		}


		if (leftClawClosed) {
			clawleft.setPosition(LEFT_CLAW_CLOSED);
		} else {
			clawleft.setPosition(LEFT_CLAW_OPEN);
		}
		if (rightClawClosed) {
			clawright.setPosition(RIGHT_CLAW_CLOSED);
		} else {
			clawright.setPosition(RIGHT_CLAW_OPEN);

		}

		if (!rightClawClosed && !leftClawClosed) {
			gamepad1.setLedColor(0,255,0, 5000);
			gamepad2.setLedColor(0,255,0, 5000);
		} else if (rightClawClosed && leftClawClosed) {
			gamepad1.setLedColor(255,0,0, 5000);
			gamepad2.setLedColor(255,0,0, 5000);
		} else {
			gamepad1.setLedColor(255,255,0, 500);
			gamepad2.setLedColor(255,255,0, 500);
		}
	}

	public void open(String claw) {
		if (claw.equals("left")) {
			leftClawClosed = false;
			clawleft.setPosition(LEFT_CLAW_OPEN);
		} else {
			rightClawClosed = false;
			clawright.setPosition(RIGHT_CLAW_OPEN);
		}
	}

	public void close(String claw) {
		if (claw.equals("left")) {
			leftClawClosed = true;
			clawleft.setPosition(LEFT_CLAW_CLOSED);
		} else {
			rightClawClosed = true;
			clawright.setPosition(RIGHT_CLAW_CLOSED);
		}
	}

	public void preciseOpenRight() {
		rightClawClosed = false;
		clawright.setPosition(RIGHT_CLAW_PRECISE_OPEN);
	}
}
