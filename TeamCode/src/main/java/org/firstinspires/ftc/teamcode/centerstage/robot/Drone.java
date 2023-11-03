package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Drone {
	public static double DRONE_RELEASED = 0.6;
	public static double DRONE_LOCKED = 0.4;

	public Servo dronelauncher;

	public boolean droneReleased = false;

	private final HardwareMap hardwareMap;

	public Drone(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		dronelauncher = hardwareMap.get(Servo.class, "drone");
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {

		if (gamepad2.a || gamepad1.a) {
			droneReleased = !droneReleased;
			while (gamepad2.a || gamepad1.a);
		}

		if (droneReleased) {
			release();
		} else {
			lock();
		}

	}

	public void release() {
		dronelauncher.setPosition(DRONE_RELEASED);
	}


	public void lock() {
		dronelauncher.setPosition(DRONE_LOCKED);
	}
}
