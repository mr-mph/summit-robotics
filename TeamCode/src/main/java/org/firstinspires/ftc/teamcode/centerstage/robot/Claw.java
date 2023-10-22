package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Claw {
	public static double BOTTOM_CLAW_CLOSED = 0.36;
	public static double BOTTOM_CLAW_OPEN = 0.0;
	public static double TOP_CLAW_CLOSED = 0.33;
	public static double TOP_CLAW_OPEN = 0.15;


	public CRServo clawtop;
	public CRServo clawbottom;

	public boolean topClawClosed = false;
	public boolean bottomClawClosed = false;

	private final HardwareMap hardwareMap;

	public Claw(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		clawtop = hardwareMap.get(CRServo.class, "clawtop");
		clawbottom = hardwareMap.get(CRServo.class, "clawbottom");
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad2.y || gamepad1.y) {
			if (!bottomClawClosed) {
				// only allow the bottom to open if the top is open
				topClawClosed = !topClawClosed;
				while (gamepad2.y || gamepad1.y);
			}
		} else if (gamepad2.b || gamepad1.b) {
			if (topClawClosed) {
				// only allow the top to close if the bottom is closed
				bottomClawClosed = !bottomClawClosed;
				while (gamepad2.a || gamepad1.a);
			}

		}

		if (topClawClosed) {
			close(clawtop);
		} else {
			open(clawtop);
		}

		if (bottomClawClosed) {
			close(clawbottom);
		} else {
			open(clawbottom);
		}
	}

	public void open(CRServo claw) {
		if (claw == clawbottom) {
			claw.setPower(BOTTOM_CLAW_OPEN);
		} else {
			claw.setPower(TOP_CLAW_OPEN);
		}
	}

	public void close(CRServo claw) {
		if (claw == clawbottom) {
			claw.setPower(BOTTOM_CLAW_CLOSED);
		} else {
			claw.setPower(TOP_CLAW_CLOSED);
		}
	}
}
