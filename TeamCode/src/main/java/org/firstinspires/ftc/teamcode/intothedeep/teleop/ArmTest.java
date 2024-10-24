package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;

@TeleOp(name = "!Arm Test")
public class ArmTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        Robot robot = new Robot(hardwareMap);

        robot.arm.init();
        robot.wrist.init(true);
        waitForStart();
        robot.arm.armMotor.setPower(0.2);
        robot.arm.armToTicks(Arm.GROUND_TICKS);

        while (!isStopRequested()) {
            robot.arm.gamepadInput(gamepad1, gamepad2);

            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                robot.high_rung();
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                robot.wall();
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                robot.pickup();
            } else if (gamepad1.dpad_left || gamepad2.dpad_left) {
                robot.hang();
            }


            robot.sendTelemetry(telemetry);

        }
    }
}