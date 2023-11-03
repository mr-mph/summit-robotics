package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;



@Config
public class Arm {
	public static int PICKUP_TICKS = 4550;
	public static int BACKDROP_TICKS = 3500;
	public static int BASE_TICKS = 100;

	public static double ARM_POSITION_SPEED = 1;
	public static double ARM_ADJUST_SPEED = 0.5;

	public DcMotorEx armMotor;

	public int targetTicks;

	private final HardwareMap hardwareMap;

	public Arm(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		armMotor = hardwareMap.get(DcMotorEx.class, "arm");
		armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
		armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		armMotor.setTargetPositionTolerance(10);

		armToTicks(BASE_TICKS);
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.dpad_up || gamepad2.dpad_up) {
			armToTicks(PICKUP_TICKS);
		} else if (gamepad1.dpad_down || gamepad2.dpad_down) {
			armToTicks(BASE_TICKS);
		} else if (gamepad1.dpad_left || gamepad2.dpad_left) {
			armToTicks(targetTicks - (int) ((200 * ARM_ADJUST_SPEED)));
		} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
			armToTicks(targetTicks + (int) ((200 * ARM_ADJUST_SPEED)));
		}
	armMotor.setPower(ARM_POSITION_SPEED * (Math.abs(targetTicks - armMotor.getCurrentPosition())) / 1000 );
	}

	public void armToTicks(int ticks) {
		targetTicks = ticks;

		armMotor.setTargetPosition(targetTicks);
		armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

}
