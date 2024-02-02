package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;



@Config
public class Arm {
	public static int FLOOR_TICKS = -300;
	public static int PICKUP_TICKS = 50;
	public static int BASE_TICKS = 200;
	public static int INIT_HEIGHT = 590;
	public static int PRECISE_BACKDROP_TICKS = 600;
	public static int HANG_TICKS = 2000;
	public static int BACKDROP_TICKS = 2600;


	public static double ARM_POSITION_SPEED = 0.5;
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
		if (gamepad1.dpad_left || gamepad2.dpad_left) {
			int newTicks = targetTicks - (int) ((100 * ARM_ADJUST_SPEED));
			if (newTicks < 0 && !(gamepad1.ps || gamepad2.ps)) {
				newTicks = 0;
			}
			armToTicks(newTicks);
		} else if (gamepad1.dpad_right || gamepad2.dpad_right) {
			int newTicks = targetTicks + (int) ((100 * ARM_ADJUST_SPEED));
			if (newTicks > BACKDROP_TICKS && !(gamepad1.ps || gamepad2.ps)) {
				newTicks = BACKDROP_TICKS;
			}
			armToTicks(newTicks);
		}
	armMotor.setPower(ARM_POSITION_SPEED * (Math.abs(targetTicks - armMotor.getCurrentPosition())) / 500 );
	}

	public void armToTicks(int ticks) {
		targetTicks = ticks;

		armMotor.setTargetPosition(targetTicks);
		armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
	}

}
