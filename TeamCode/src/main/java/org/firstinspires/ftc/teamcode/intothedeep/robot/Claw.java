package org.firstinspires.ftc.teamcode.intothedeep.robot;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw {
    public static double CLAW_OPEN = 0.15;
    public static double CLAW_CLOSED = 0.26;

    public Servo claw;
    public boolean clawClosed = false;

    public boolean initialized = false;

    private boolean bumperHeld = false;
    private boolean triggerHeld = false;

    private final HardwareMap hardwareMap;

    public Claw(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public void init() {
        claw = hardwareMap.get(Servo.class, "claw");

        claw.setPosition(CLAW_CLOSED);
        initialized = true;
    }

    public void gamepadInput(Gamepad gamepad1, Gamepad gamepad2) {

        if (gamepad1.right_bumper || gamepad2.right_bumper) {
            if (!bumperHeld) {
                bumperHeld = true;
                clawClosed = !clawClosed;
            }
        } else {
            bumperHeld = false;
        }


        if (clawClosed) {
            claw.setPosition(CLAW_CLOSED);
        } else {
            claw.setPosition(CLAW_OPEN);
        }


        if (clawClosed) {
            gamepad1.setLedColor(255,0,0, 5000);
            gamepad2.setLedColor(255,0,0, 5000);
        } else {
            gamepad1.setLedColor(0,255,0, 500);
            gamepad2.setLedColor(0,255,0, 500);
        }
    }

    public void open() {
        claw.setPosition(CLAW_OPEN);
    }

    public void close() {
        claw.setPosition(CLAW_CLOSED);

    }

}
