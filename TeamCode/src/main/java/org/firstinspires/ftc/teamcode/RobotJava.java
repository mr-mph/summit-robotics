package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Robot Java")
public class RobotJava extends LinearOpMode {

  private DcMotor rightback;
  private DcMotor rightfront;
  private DcMotor leftback;
  private DcMotor leftfront;
  private CRServo clawleft;
  private CRServo clawright;
  private DcMotor slideleft;
  private DcMotor slideright;
  private String speedState = "normal";
  private boolean IsClawClosed = false;

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
    clawleft = hardwareMap.get(CRServo.class, "clawleft");
    clawright = hardwareMap.get(CRServo.class, "clawright");
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
    slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    slideleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
          while (gamepad1.share) {}
        } else if (gamepad1.start) {
          if (speedState.equals("fast")) {
            speedState = "normal";
          } else {
            speedState = "fast";
          }
          while (gamepad1.start) {}
        }

        if (speedState.equals("slow")) {
          Speed = 0.1;
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
          slideleft.setPower(0.6);
          slideright.setPower(0.6);
          slideright.setTargetPosition(-1800);
          slideleft.setTargetPosition(1800);
          slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.dpad_down) {
          slideleft.setPower(0.6);
          slideright.setPower(0.6);
          slideright.setTargetPosition(0);
          slideleft.setTargetPosition(0);
          slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }


        if (IsClawClosed) {
          clawleft.setPower(-0.5);
          clawright.setPower(0.5);
        } else {
          clawleft.setPower(0);
          clawright.setPower(0);
        }

        if (gamepad2.dpad_left) {
          IsClawClosed = false;
        } else if (gamepad2.dpad_right) {
          IsClawClosed = true;
        }

        telemetry.addData("slideleft", slideleft.getCurrentPosition());
        telemetry.addData("slideright", slideright.getCurrentPosition());
        telemetry.addData("servoleft", clawleft.getPower());
        telemetry.addData("servoright", clawright.getPower());
        telemetry.addData("speedState", speedState);
        telemetry.update();
      }
    }
  }
}