package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@TeleOp(name = "Robot Java 2.0")
public class RobotMechanum extends LinearOpMode {

  public static double LEFT_SERVO_CLOSED = 0.1;
  public static double LEFT_SERVO_OPEN = -0.5;
  public static double RIGHT_SERVO_OPEN = 0.5;
  public static double RIGHT_SERVO_CLOSED = -0.1;

  private boolean IsClawClosed = false;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    CRServo clawleft = hardwareMap.get(CRServo.class, "clawleft");
    CRServo clawright = hardwareMap.get(CRServo.class, "clawright");

    DcMotor slideleft = hardwareMap.get(DcMotor.class, "slideleft");
    DcMotor slideright = hardwareMap.get(DcMotor.class, "slideright");

    waitForStart();

    slideleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    slideleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    slideleft.setTargetPosition(0);
    slideleft.setPower(1);
    slideleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    slideright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    slideright.setTargetPosition(0);
    slideright.setPower(1);
    slideright.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    while (!isStopRequested()) {

      drive.setWeightedDrivePower(
              new Pose2d(
                      -gamepad1.left_stick_y/1.5,
                      -gamepad1.left_stick_x/1.5,
                      -gamepad1.right_stick_x/1.5
              )
      );

      drive.update();

      if (gamepad2.dpad_up || gamepad1.y) {
        slideright.setPower(1);
        slideleft.setPower(1);
        slideright.setTargetPosition(-1600);
        slideleft.setTargetPosition(1600);
      } else if (gamepad2.dpad_down  || gamepad1.a) {
        slideright.setPower(0.5);
        slideleft.setPower(0.5);
        slideright.setTargetPosition(0);
        slideleft.setTargetPosition(0);
      } else if (gamepad2.share) {
        slideright.setTargetPosition(-800);
        slideleft.setTargetPosition(800);
      } else if (gamepad2.start) {
      slideright.setTargetPosition(-1200);
      slideleft.setTargetPosition(1200);
    }

      if (IsClawClosed) {
        clawleft.setPower(LEFT_SERVO_CLOSED);
        clawright.setPower(RIGHT_SERVO_CLOSED);
      } else {
        clawleft.setPower(LEFT_SERVO_OPEN);
        clawright.setPower(RIGHT_SERVO_OPEN);
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
      telemetry.addData("IsClawClosed", IsClawClosed);
      telemetry.update();
    }
  }
}