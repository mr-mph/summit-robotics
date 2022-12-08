package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Robot (Blocks to Java)")
public class Robot extends LinearOpMode {

  private DcMotor rightback;
  private DcMotor rightfront;
  private Servo claw;
  private DcMotor leftback;
  private DcMotor leftfront;
  private DcMotor Slide;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    double Speed;

    rightback = hardwareMap.get(DcMotor.class, "rightback");
    rightfront = hardwareMap.get(DcMotor.class, "rightfront");
    claw = hardwareMap.get(Servo.class, "claw");
    leftback = hardwareMap.get(DcMotor.class, "leftback");
    leftfront = hardwareMap.get(DcMotor.class, "leftfront");
    Slide = hardwareMap.get(DcMotor.class, "Slide");

    waitForStart();
    // You will have to determine which motor to reverse for your robot.
    // In this example, the right motor was reversed so that positive
    // applied power makes it move the robot in the forward direction.
    rightback.setDirection(DcMotorSimple.Direction.REVERSE);
    // You will have to determine which motor to reverse for your robot.
    // In this example, the right motor was reversed so that positive
    // applied power makes it move the robot in the forward direction.
    rightfront.setDirection(DcMotorSimple.Direction.REVERSE);
    claw.setDirection(Servo.Direction.REVERSE);
    rightback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        if (gamepad1.left_trigger == 1) {
          Speed = 0.3;
        } else if (gamepad1.right_trigger == 1) {
          Speed = 0.2;
        } else {
          Speed = 0.5;
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
        if (Speed < 0) {
          Speed = 0;
        } else if (Speed > 1) {
          Speed = 1;
        }
        if (gamepad2.a) {
          Slide.setPower(1);
        } else if (gamepad2.y) {
          Slide.setPower(-1);
        } else {
          Slide.setPower(0);
        }
        if (Slide.getCurrentPosition() > 600) {
          Slide.setPower(0.15);
        }
        if (gamepad1.x) {
          claw.setPosition(0);
        } else if (gamepad1.b) {
          claw.setPosition(1);
        }
        Speed = Math.round(Speed * 100) / 100;
        telemetry.addData("rightback", rightback.getPower());
        telemetry.addData("leftback", leftback.getPower());
        telemetry.addData("leftfront", leftfront.getPower());
        telemetry.addData("rightfront", rightfront.getPower());
        telemetry.addData("Slide", Slide.getCurrentPosition());
        telemetry.update();
      }
      telemetry.update();
    }
  }
}
