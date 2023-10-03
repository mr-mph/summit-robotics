package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Config
public class Arm {

	public static int HIGH_BACKDROP_TICKS = 1920;
	public static int MEDIUM_BACKDROP_TICKS = 1380;
	public static int LOW_BACKDROP_TICKS = 930;
	public static int LOW_CARRY_TICKS = 100;
	public static int BASE_TICKS = 0;

	public static double ARM_UP_SPEED = 1;
	public static double ARM_DOWN_SPEED = 0.6;

	public DcMotorEx armMotor;

	public int targetTicks;

	private final HardwareMap hardwareMap;

	public Arm(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		armMotor = hardwareMap.get(DcMotorEx.class, "arm");
		armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

		armMotor.setDirection(DcMotorEx.Direction.REVERSE);

		armToTicks(BASE_TICKS);
	}

	public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
		if (gamepad1.y || gamepad2.y) {
			armToTicks(HIGH_BACKDROP_TICKS);
		} else if (gamepad1.start || gamepad2.start) {
			armToTicks(MEDIUM_BACKDROP_TICKS);
		} else if (gamepad1.share || gamepad2.share) {
			armToTicks(LOW_BACKDROP_TICKS);
		} else if (gamepad1.ps || gamepad2.ps) {
			armToTicks(LOW_CARRY_TICKS);
		} else if (gamepad1.a || gamepad2.a) {
			armToTicks(BASE_TICKS);
		} else {
			armToTicks(targetTicks - (int) (gamepad1.right_stick_y + gamepad2.right_stick_y) * 15);
		}
	}

	public void armToTicks(int ticks) {
		targetTicks = ticks;
		if (armMotor.getCurrentPosition() > targetTicks) {
			armMotor.setPower(ARM_DOWN_SPEED);
		} else {
			armMotor.setPower(ARM_UP_SPEED);
		}

		armMotor.setTargetPosition(targetTicks);
		armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}


}
