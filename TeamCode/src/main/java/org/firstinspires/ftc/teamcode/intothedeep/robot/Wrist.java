package org.firstinspires.ftc.teamcode.intothedeep.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Wrist {
    public static double WRIST_GROUND = -0.8;
    public static double WRIST_WALL = -0.05; // -0.05 (old) -> -0.1
    public static double WRIST_HIGH_RUNG = -0.6;
    public static double WRIST_HIGH_BRINGDOWN = -0.4;

    public static double WRIST_LOW_BASKET = -0.9;
    public static double WRIST_HANG = 0.16;
    public static double WRIST_ADJUST_SPEED = 0.01;


    public CRServo wrist;

    public String wristState = "down";
    public boolean initialized = false;

    private final HardwareMap hardwareMap;

    public double wristAdjustment = 0;

    public Wrist(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init(boolean isAuto) {
        wrist = hardwareMap.get(CRServo.class, "wrist");
        wrist.setPower(isAuto ? WRIST_HANG : WRIST_GROUND);
        initialized = true;
    }

    public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
        if (gamepad1.ps || gamepad2.ps) {
            if (gamepad1.left_bumper || gamepad2.left_bumper) {
                wristAdjustment += WRIST_ADJUST_SPEED;

            } else if (gamepad1.left_trigger > 0.5 || gamepad2.left_trigger > 0.5) {
                wristAdjustment -= WRIST_ADJUST_SPEED;
            }
        }

        if (wristState.equals("ground")) {
            wrist.setPower(WRIST_GROUND+wristAdjustment);
        } else if (wristState.equals("wall")) {
            wrist.setPower(WRIST_WALL+wristAdjustment);
        } else if (wristState.equals("high rung")) {
            wrist.setPower(WRIST_HIGH_RUNG+wristAdjustment);
        } else if (wristState.equals("hang")) {
            wrist.setPower(WRIST_HANG + wristAdjustment);
        } else if (wristState.equals("low basket")) {
            wrist.setPower(WRIST_LOW_BASKET + wristAdjustment);
        } else if (wristState.equals("bringdown")) {
            wrist.setPower(WRIST_HIGH_BRINGDOWN+wristAdjustment);
        }

        if (gamepad1.right_stick_button || gamepad2.right_stick_button) {
            wristAdjustment = 0;
        }
    }

    public void ground() {
        wristState = "ground";
//        wrist.setPower(WRIST_GROUND);
    }

    public void wall() {
        wristState = "wall";
//        wrist.setPower(WRIST_WALL);
    }
    public void high_rung() {
        wristState = "high rung";
//        wrist.setPower(WRIST_HIGH_RUNG);
    }

    public void hang() {
        wristState = "hang";
//        wrist.setPower(WRIST_HANG);
    }

    public void low_basket() {
        wristState = "low basket";
//        wrist.setPower(WRIST_LOW_BASKET);
    }

    public void bringdown() {
        wristState = "bringdown";
//        wrist.setPower(WRIST_HIGH_BRINGDOWN);
    }
}
