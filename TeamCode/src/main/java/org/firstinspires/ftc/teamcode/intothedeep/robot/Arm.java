package org.firstinspires.ftc.teamcode.intothedeep.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;



@Config
public class Arm {

    // TODO: arm needs to move before the wrist
    public static int INIT_HEIGHT = 0;
    public static int GROUND_TICKS = 350;
    public static int WALL_TICKS = 50;
    public static int HIGH_RUNG_TICKS = 1500; // -> 1000 to place (was 1380)
    public static int HIGH_RUNG_BRINGDOWN_TICKS = (int) (500*1.8);
    public static int HIGH_RUNG_BRINGUP_TICKS = (int) (650*1.8);
    public static int HANG_TICKS = (int) (1600*1.0714285714); // -> -300 to hang
    public static int BASKET_TICKS = (int) (2150*1.0714285714);

    public static double ARM_POSITION_SPEED = 1;
    public static double ARM_ADJUST_SPEED = 0.1;

    public DcMotorEx armMotor;

    public int targetTicks;
    public boolean initialized = false;

    private double armAdjustment = 0;

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

        if (!(gamepad1.ps || gamepad2.ps)) {

            if (gamepad1.left_bumper || gamepad2.left_bumper) {
                targetTicks += (int) ((100 * ARM_ADJUST_SPEED));
                if (targetTicks < -50 && !(gamepad1.ps || gamepad2.ps)) {
                    targetTicks = -50;
                }

            } else if (gamepad1.left_trigger > 0.5 || gamepad2.left_trigger > 0.5) {
                targetTicks -= (int) ((100 * ARM_ADJUST_SPEED));
                if (targetTicks > BASKET_TICKS && !(gamepad1.ps || gamepad2.ps)) {
                    targetTicks = BASKET_TICKS;
                }
            }

            armToTicks(targetTicks);
        }

//        if (gamepad1.left_stick_button || gamepad2.left_stick_button) {
//            armAdjustment = 0;
//        }
        armMotor.setPower(Math.max((ARM_POSITION_SPEED * (Math.abs(targetTicks - armMotor.getCurrentPosition())) / 500), 0.1));
    }

    public void armToTicks(int ticks) {
        targetTicks = ticks;

        armMotor.setTargetPosition(targetTicks); // +((int) armAdjustment)
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


}
