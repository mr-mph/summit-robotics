package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;



@Config
public class Arm {
	public static int PICKUP_TICKS = 0;
	public static int BASE_TICKS = 200;
	public static int INIT_HEIGHT = 590;
	public static int BACKDROP_TICKS = 800;
	public static int BACKDROP_TICKS_2ND = 1000;
	public static int HANG_TICKS = 1800;

	public static double ARM_POSITION_SPEED = 1;
	public static double ARM_ADJUST_SPEED = 0.1;

	public DcMotorEx armMotor;

	public int targetTicks;
	public boolean initialized = false;

	private final HardwareMap hardwareMap;

	public Arm(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		armMotor = hardwareMap.get(DcMotorEx.class, "arm");
		armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
		armMotor.setTargetPositionTolerance(10);

		armMotor.setPower(ARM_POSITION_SPEED/2);
		armToTicks(INIT_HEIGHT);
		initialized = true;
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.dpad_up || gamepad2.dpad_up) {
			if (gamepad1.x || gamepad2.x) {
				armToTicks(BACKDROP_TICKS);
				while (gamepad1.dpad_up || gamepad2.dpad_up) {}
			} else if (gamepad1.y || gamepad2.y) {
				armToTicks(BACKDROP_TICKS_2ND);
				while (gamepad1.dpad_up || gamepad2.dpad_up) {}
			} else if (gamepad1.b || gamepad2.b) {
				armToTicks(HANG_TICKS);
				while (gamepad1.dpad_up || gamepad2.dpad_up) {}
			} else {
				armToTicks(BASE_TICKS);
			}

		} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
			armToTicks(PICKUP_TICKS);
		} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
			int newTicks = targetTicks - (int) ((100 * ARM_ADJUST_SPEED));
			if (newTicks < PICKUP_TICKS && !(gamepad1.ps || gamepad2.ps)) {
				newTicks = PICKUP_TICKS;
			}
			armToTicks(newTicks);


		} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
			int newTicks = targetTicks + (int) ((100 * ARM_ADJUST_SPEED));
			if (newTicks > HANG_TICKS && !(gamepad1.ps || gamepad2.ps)) {
				newTicks = HANG_TICKS;
			}
			armToTicks(newTicks);
		}
	armMotor.setPower(ARM_POSITION_SPEED * (Math.abs(targetTicks - armMotor.getCurrentPosition())) / 1000 );
	}

	public void armToTicks(int ticks) {
		targetTicks = ticks;

		armMotor.setTargetPosition(targetTicks);
		armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

}
