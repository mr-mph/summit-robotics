package org.firstinspires.ftc.teamcode.intothedeep.robot;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw {

    public static double CLAW_OPEN_WIDE = 0.3;
    public static double CLAW_CLOSED_WIDE = 0.4;

    public static double CLAW_OPEN = 0.35;
    public static double CLAW_CLOSED = 0.57;

    public Servo claw;
    public String clawState = "closed";

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
                if (clawState.equals("open")) {
                    clawState = "closed";
                } else {
                    clawState = "open";
                }
            }
        } else {
            bumperHeld = false;
        }

        if (gamepad1.right_trigger > 0.5 || gamepad2.right_trigger > 0.5) {
            if (!triggerHeld) {
                triggerHeld = true;
                if (clawState.equals("open wide")) {
                    clawState = "closed wide";
                } else {
                    clawState = "open wide";
                }
            }
        } else {
            triggerHeld = false;
        }


        if (clawState.equals("closed")) {
            claw.setPosition(CLAW_CLOSED);
        } else if (clawState.equals("open")) {
            claw.setPosition(CLAW_OPEN);
        } else if (clawState.equals("open wide")) {
            claw.setPosition(CLAW_OPEN_WIDE);
        } else {
            claw.setPosition(CLAW_CLOSED_WIDE);
        }


        if (clawState.contains("closed")) {
            gamepad1.setLedColor(255,0,0, 5000);
            gamepad2.setLedColor(255,0,0, 5000);
        } else {
            gamepad1.setLedColor(0,255,0, 500);
            gamepad2.setLedColor(0,255,0, 500);
        }
    }

    public void open() {
        claw.setPosition(CLAW_OPEN);
        clawState = "open";
    }

    public void close() {
        claw.setPosition(CLAW_CLOSED);
        clawState = "closed";

    }
    public void open_wide() {
        claw.setPosition(CLAW_OPEN_WIDE);
        clawState = "open wide";
    }

    public void close_wide() {
        claw.setPosition(CLAW_CLOSED_WIDE);
        clawState = "closed wide";
    }

}
