package org.firstinspires.ftc.teamcode.intothedeep.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
import org.firstinspires.ftc.teamcode.intothedeep.teleop.IntoTheDeepTeleOp;


@TeleOp(name = "!Arm Test")
public class ArmTest extends LinearOpMode {
    @Override
    public void runOpMode() {

        Robot robot = new Robot(hardwareMap);
        IntoTheDeepTeleOp teleop = new IntoTheDeepTeleOp();

        robot.arm.init();
        robot.wrist.init(true);
        waitForStart();
        robot.arm.armMotor.setPower(0.2);
        robot.arm.armToTicks(Arm.GROUND_TICKS);

        while (!isStopRequested()) {
            robot.arm.gamepadInput(gamepad1, gamepad2);

            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                teleop.high_rung();
            } else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                teleop.wall();
            } else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                teleop.pickup();
            } else if (gamepad1.dpad_left || gamepad2.dpad_left) {
                teleop.hang();
            }


            robot.sendTelemetry(telemetry);

        }
    }
}