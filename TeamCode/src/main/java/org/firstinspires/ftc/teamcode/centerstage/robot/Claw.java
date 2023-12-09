package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Claw {
	public static double TOP_CLAW_CLOSED = 0.48;
	public static double TOP_CLAW_OPEN = 0.58;

	public CRServo clawtop;
	public CRServo clawbottom;

	public boolean topClawClosed = true;
//	public boolean bottomClawClosed = false;
	public boolean initialized = false;

	private final HardwareMap hardwareMap;

	public Claw(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		clawtop = hardwareMap.get(CRServo.class, "clawtop");
//		clawbottom = hardwareMap.get(CRServo.class, "clawbottom");
		close(clawtop);
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
//		if (gamepad2.right_bumper || gamepad1.right_bumper) {
//			if (!bottomClawClosed) {
//				// only allow the bottom to open if the top is open
//				topClawClosed = !topClawClosed;
//				while (gamepad2.right_bumper || gamepad1.right_bumper);
//			}
//		} else if (gamepad2.right_trigger > 0.5 || gamepad1.right_trigger > 0.5) {
//			if (topClawClosed) {
//				// only allow the top to close if the bottom is closed
//				bottomClawClosed = !bottomClawClosed;
//				while (gamepad2.right_trigger > 0.5 || gamepad1.right_trigger > 0.5);
//			}
//		}

		if (gamepad2.right_bumper || gamepad1.right_bumper) {
				topClawClosed = !topClawClosed;
				while (gamepad2.right_bumper || gamepad1.right_bumper);
		}

		if (topClawClosed) {
			close(clawtop);
			gamepad1.setLedColor(255,255,0, 5000);
			gamepad2.setLedColor(255,255,0, 5000);

		} else {
			open(clawtop);
			gamepad1.setLedColor(0,255,0, 5000);
			gamepad2.setLedColor(0,255,0, 5000);
		}

//		if (bottomClawClosed) {
//			close(clawbottom);
//		} else {
//			open(clawbottom);
//		}
	}

	public void open(CRServo claw) {
//		if (claw == clawbottom) {
//			claw.setPower(BOTTOM_CLAW_OPEN);
//		} else {
//			claw.setPower(TOP_CLAW_OPEN);
//		}
		claw.setPower(TOP_CLAW_OPEN);
	}

	public void close(CRServo claw) {
//		if (claw == clawbottom) {
//			claw.setPower(BOTTOM_CLAW_CLOSED);
//		} else {
//			claw.setPower(TOP_CLAW_CLOSED);
//		}

		claw.setPower(TOP_CLAW_CLOSED);
	}
}
