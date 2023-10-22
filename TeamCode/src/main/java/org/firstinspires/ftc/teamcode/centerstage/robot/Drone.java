package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Drone {
	public static double DRONE_RELEASED = 1;
	public static double DRONE_LOCKED = 0;

	public CRServo dronelauncher;

	public boolean droneReleased = false;

	private final HardwareMap hardwareMap;

	public Drone(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		dronelauncher = hardwareMap.get(CRServo.class, "dronelauncher");
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad2.x || gamepad1.x) {
			droneReleased = true;
		} else if (gamepad2.b || gamepad1.b) {
			droneReleased = false;
		}

		if (droneReleased) {
			release();
		} else {
			lock();
		}

	}

	public void release() {
		dronelauncher.setPower(DRONE_RELEASED);
	}

	public void lock() {
		dronelauncher.setPower(DRONE_LOCKED);
	}
}
