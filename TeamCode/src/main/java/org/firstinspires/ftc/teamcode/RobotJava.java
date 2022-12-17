package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "RobotJava")
public class RobotJava extends LinearOpMode {

  private DcMotor rightback;
  private DcMotor rightfront;
  private DcMotor leftback;
  private DcMotor leftfront;
//  private Servo clawleft;
//  private Servo clawright;
  private DcMotor slideleft;
  private DcMotor slideright;
  private String speedState = "normal";

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double Speed;

    rightback = hardwareMap.get(DcMotor.class, "rightback");
    rightfront = hardwareMap.get(DcMotor.class, "rightfront");
    leftback = hardwareMap.get(DcMotor.class, "leftback");
    leftfront = hardwareMap.get(DcMotor.class, "leftfront");
//    clawleft = hardwareMap.get(Servo.class, "clawleft");
//    clawright = hardwareMap.get(Servo.class, "clawright");
    slideleft = hardwareMap.get(DcMotor.class, "slideleft");
    slideright = hardwareMap.get(DcMotor.class, "slideright");

    waitForStart();

    rightback.setDirection(DcMotorSimple.Direction.REVERSE);
    rightfront.setDirection(DcMotorSimple.Direction.REVERSE);

    rightback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    slideleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    slideleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    slideright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    if (opModeIsActive()) {
      // Put run code here.
      while (opModeIsActive()) {
        if (gamepad1.share) {
          if (speedState.equals("slow")) {
            speedState = "normal";
          } else {
            speedState = "slow";
          }
        } else if (gamepad1.start) {
          if (speedState.equals("fast")) {
            speedState = "normal";
          } else {
            speedState = "fast";
          }
        }

        if (speedState.equals("slow")) {
          Speed = 0.2;
        } else if (speedState.equals("fast")) {
          Speed = 0.8;
        } else {
          Speed = 0.4;
        }

        if (gamepad1.dpad_down) {
          rightback.setPower(Speed);
          leftback.setPower(Speed);
          leftfront.setPower(Speed);
          rightfront.setPower(Speed);
        } else if (gamepad1.dpad_up) {
          rightback.setPower(-Speed);
          leftback.setPower(-Speed);
          leftfront.setPower(-Speed);
          rightfront.setPower(-Speed);
        } else if (gamepad1.dpad_right) {
          rightback.setPower(-Speed);
          leftback.setPower(Speed);
          leftfront.setPower(-Speed);
          rightfront.setPower(Speed);
        } else if (gamepad1.dpad_left) {
          rightback.setPower(Speed);
          leftback.setPower(-Speed);
          leftfront.setPower(Speed);
          rightfront.setPower(-Speed);
        } else if (gamepad1.right_bumper) {
          rightback.setPower(Speed);
          rightfront.setPower(Speed);
          leftfront.setPower(-Speed);
          leftback.setPower(-Speed);
        } else if (gamepad1.left_bumper) {
          rightback.setPower(-Speed);
          rightfront.setPower(-Speed);
          leftfront.setPower(Speed);
          leftback.setPower(Speed);
        } else {
          rightback.setPower(0);
          leftback.setPower(0);
          leftfront.setPower(0);
          rightfront.setPower(0);
        }

        if (gamepad2.dpad_up) {
          slideleft.setPower(1);
          slideright.setPower(1);
        } else if (gamepad2.dpad_down) {
          slideleft.setPower(-1);
          slideright.setPower(-1);
        } else {
          slideleft.setPower(0);
          slideright.setPower(0);
        }

        if (gamepad2.dpad_left) {
//          clawleft.setPosition(0);
//          clawright.setPosition(0);
        } else if (gamepad2.dpad_right) {
//          clawleft.setPosition(1);
//          clawright.setPosition(1);
        }

        telemetry.addData("rightback", rightback.getPower());
        telemetry.addData("leftback", leftback.getPower());
        telemetry.addData("leftfront", leftfront.getPower());
        telemetry.addData("rightfront", rightfront.getPower());
        telemetry.addData("slideleft", slideleft.getCurrentPosition());
        telemetry.update();
      }
    }
  }
}
