package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@Autonomous(name = "Autonomous1 (Blocks to Java)")
public class AutonomousJava extends LinearOpMode {

  private DcMotor leftfront;
  private Servo claw;
  private ColorSensor colorsensor;
  private DcMotor rightback;
  private DcMotor leftback;
  private DcMotor rightfront;

  double Speed;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    int sensedColor;

    leftfront = hardwareMap.get(DcMotor.class, "leftfront");
    claw = hardwareMap.get(Servo.class, "claw");
    colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
    rightback = hardwareMap.get(DcMotor.class, "rightback");
    leftback = hardwareMap.get(DcMotor.class, "leftback");
    rightfront = hardwareMap.get(DcMotor.class, "rightfront");

    waitForStart();
    Speed = 0.25;
    if (opModeIsActive()) {
      leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
      claw.setPosition(1);
      rotate_90_degrees_to_R(2);
      // reach terminal tape
      Move_Forward(1.5);
      // stop and open the claw to drop the cone
      claw.setPosition(0);
      // Go backwards for x/2 seconds
      Move_Backward(1.5);
      // rotate 90 degrees L
      rotate_90_degrees_to_L(0.5);
      // Go forwards to read the cone to see where to park
      Move_Forward(1.7);
      // Create variable to store values from color sensor
      telemetry.addData("Red", colorsensor.red());
      telemetry.addData("Green", colorsensor.green());
      telemetry.addData("Blue", colorsensor.blue());
      sensedColor = Color.rgb(colorsensor.red(), colorsensor.green(), colorsensor.blue());
      if (JavaUtil.colorToSaturation(sensedColor) >= 0.6 && JavaUtil.colorToHue(sensedColor) >= 40 && JavaUtil.colorToHue(sensedColor) <= 60) {
        // If color sleeve is yellow, it STOPS in Location 2
        Stop_Motors();
      }
      if (JavaUtil.colorToSaturation(sensedColor) >= 0.6 && JavaUtil.colorToHue(sensedColor) >= 168 && JavaUtil.colorToHue(sensedColor) <= 198) {
        // If color sleeve is blue, it goes LEFT to Location 1
        Strafe_Left(1.7);
        if (JavaUtil.colorToSaturation(sensedColor) >= 0.6 && JavaUtil.colorToHue(sensedColor) >= 168 && JavaUtil.colorToHue(sensedColor) <= 198) {
          // If color sleeve is pink, it goes RIGHT to Location 3
          Strafe_Right(1.7);
          telemetry.update();
        }
      }
    }
  }

  /**
   * Describe this function...
   */
  private void Move_Forward(double Seconds) {
    rightback.setPower(Speed);
    leftback.setPower(Speed);
    leftfront.setPower(Speed);
    rightfront.setPower(Speed);
    sleep((long) (Seconds * 1000));
    Stop_Motors();
  }

  /**
   * Describe this function...
   */
  private void Stop_Motors() {
    rightback.setPower(0);
    leftback.setPower(0);
    leftfront.setPower(0);
    rightfront.setPower(0);
    Stop_Motors();
  }

  /**
   * Describe this function...
   */
  private void Strafe_Right(double Seconds) {
    rightback.setPower(-Speed);
    leftback.setPower(Speed);
    leftfront.setPower(-Speed);
    rightfront.setPower(Speed);
    sleep((long) (Seconds * 1000));
    Stop_Motors();
  }

  /**
   * Describe this function...
   */
  private void rotate_90_degrees_to_L(double Seconds) {
    rightback.setPower(Speed);
    leftback.setPower(Speed);
    leftfront.setPower(-Speed);
    rightfront.setPower(-Speed);
    sleep((long) (Seconds * 1000));
    Stop_Motors();
  }

  /**
   * Describe this function...
   */
  private void rotate_90_degrees_to_R(int Seconds) {
    rightback.setPower(-Speed);
    leftback.setPower(-Speed);
    leftfront.setPower(Speed);
    rightfront.setPower(Speed);
    sleep((long) (Seconds * 2000));
    Stop_Motors();
  }

  /**
   * Describe this function...
   */
  private void Move_Backward(double Seconds) {
    rightback.setPower(-Speed);
    leftback.setPower(-Speed);
    leftfront.setPower(-Speed);
    rightfront.setPower(-Speed);
    sleep((long) (Seconds * 1000));
    Stop_Motors();
  }

  /**
   * Describe this function...
   */
  private void Strafe_Left(double Seconds) {
    rightback.setPower(Speed);
    leftback.setPower(-Speed);
    leftfront.setPower(Speed);
    rightfront.setPower(-Speed);
    sleep((long) (Seconds * 1000));
    Stop_Motors();
  }
}
