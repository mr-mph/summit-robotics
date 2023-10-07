package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Claw {
	public static double BOTTOM_CLAW_CLOSED = 0.4;
	public static double TOP_CLAW_CLOSED = 0.39;
	public static double CLAW_OPEN = 0.1;

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
		if (gamepad2.x || gamepad1.x) {
			topClawClosed = !topClawClosed;
			while (gamepad2.x || gamepad1.x);
		} else if (gamepad2.b || gamepad1.b) {
			bottomClawClosed = !bottomClawClosed;
			while (gamepad2.b || gamepad1.b);
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
		claw.setPower(CLAW_OPEN);
	}

	public void close(CRServo claw) {
		if (claw == clawbottom) {
			claw.setPower(BOTTOM_CLAW_CLOSED);
		} else {
			claw.setPower(TOP_CLAW_CLOSED);
		}
	}
}
