package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Drone {
	public static double DRONE_RELEASED = 0.35;
	public static double DRONE_LOCKED = 0.65;

	public Servo dronelauncher;

	public boolean droneReleased = false;
	private boolean gamepadWasPressed = false;
	private boolean gamepadWasPressedAndReleased = false;
	private double timeOfRelease = 0;

	public boolean initialized = false;

	private final HardwareMap hardwareMap;

	public Drone(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		dronelauncher = hardwareMap.get(Servo.class, "drone");
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2, double time) {

		if (gamepad2.a || gamepad1.a) {
			gamepadWasPressed = true;

			if (gamepadWasPressedAndReleased) {
				gamepadWasPressedAndReleased = false;
				if ((time - timeOfRelease) < 2) {
					droneReleased = true;
				}

			}
		}

		if (!gamepad2.a && !gamepad1.a && gamepadWasPressed) {
			gamepadWasPressed = false;
			timeOfRelease = time;

			if (droneReleased) {
				droneReleased = false;
			} else {
				gamepadWasPressedAndReleased = true;
			}
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
