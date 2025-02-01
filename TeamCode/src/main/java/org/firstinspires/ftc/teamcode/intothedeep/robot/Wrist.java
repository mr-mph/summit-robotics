package org.firstinspires.ftc.teamcode.intothedeep.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Wrist {
    public static double WRIST_GROUND = -0.5;
    public static double WRIST_WALL = 0.25; // -0.05 (old) -> -0.1
    public static double WRIST_HIGH_RUNG = -0.4;
    public static double WRIST_HIGH_BRINGDOWN = -0.2;

    public static double WRIST_LOW_BASKET = -0.6;
    public static double WRIST_HANG = 0.5;
    public static double WRIST_ADJUST_SPEED = 0.01;


    public CRServo wrist;

    public String wristState = "down";
    public boolean initialized = false;

    private final HardwareMap hardwareMap;

    public double targetPos = 0;

    public Wrist(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init(boolean isAuto) {
        wrist = hardwareMap.get(CRServo.class, "wrist");
        targetPos = WRIST_HANG;
        wrist.setPower(targetPos);
        initialized = true;
    }

    public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {
        if (gamepad1.ps || gamepad2.ps) {
            if (gamepad1.left_bumper || gamepad2.left_bumper) {
                targetPos += WRIST_ADJUST_SPEED;

            } else if (gamepad1.left_trigger > 0.5 || gamepad2.left_trigger > 0.5) {
                targetPos -= WRIST_ADJUST_SPEED;
            }
            wrist.setPower(targetPos);
        }
    }

    public void ground() {
        wristState = "ground";
        targetPos = WRIST_GROUND;
        wrist.setPower(targetPos);
    }

    public void wall() {
        wristState = "wall";
        targetPos = WRIST_WALL;
        wrist.setPower(targetPos);
    }
    public void high_rung() {
        wristState = "high rung";
        targetPos = WRIST_HIGH_RUNG;
        wrist.setPower(targetPos);
    }

    public void hang() {
        wristState = "hang";
        targetPos = WRIST_HANG;
        wrist.setPower(targetPos);
    }

    public void low_basket() {
        wristState = "low basket";
        targetPos = WRIST_LOW_BASKET;
        wrist.setPower(targetPos);
    }

    public void bringdown() {
        wristState = "bringdown";
        targetPos = WRIST_HIGH_BRINGDOWN;
        wrist.setPower(targetPos);
    }
}
