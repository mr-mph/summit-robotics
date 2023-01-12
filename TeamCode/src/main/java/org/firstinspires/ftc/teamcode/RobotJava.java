package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
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
    slideleft.setTargetPosition(0);
    slideleft.setPower(0.5);
    slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    slideright.setTargetPosition(0);
    slideright.setPower(0.5);
    slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);


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

        if (gamepad2.dpad_up || gamepad1.y) {
          slideright.setTargetPosition(-1800);
          slideleft.setTargetPosition(1800);
        } else if (gamepad2.dpad_down  || gamepad1.a) {
          slideright.setTargetPosition(0);
          slideleft.setTargetPosition(0);
        } else if (gamepad2.share) {
          slideright.setTargetPosition(-1000);
          slideleft.setTargetPosition(1000);
        } else if (gamepad2.start) {
        slideright.setTargetPosition(-1400);
        slideleft.setTargetPosition(1400);
      }

        if (IsClawClosed) {
          clawleft.setPower(-0.5);
          clawright.setPower(0.5);
        } else {
          clawleft.setPower(0);
          clawright.setPower(0);
        }

        if (gamepad2.dpad_left || gamepad1.x) {
          IsClawClosed = false;
        } else if (gamepad2.dpad_right || gamepad1.b) {
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