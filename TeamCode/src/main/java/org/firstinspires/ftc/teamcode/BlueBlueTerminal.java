package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@Autonomous(name = "BlueBlueTerminal (Blocks to Java)")
public class BlueBlueTerminal extends LinearOpMode {

  private Servo claw;
  private DcMotor leftfront;
  private DcMotor rightback;
  private DcMotor leftback;
  private DcMotor rightfront;
  private ColorSensor colorsensor_REV_ColorRangeSensor;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    int gain;
    NormalizedRGBA normalizedColors;
    int color;
    float hue;
    float saturation;
    float value;

    claw = hardwareMap.get(Servo.class, "claw");
    leftfront = hardwareMap.get(DcMotor.class, "leftfront");
    rightback = hardwareMap.get(DcMotor.class, "rightback");
    leftback = hardwareMap.get(DcMotor.class, "leftback");
    rightfront = hardwareMap.get(DcMotor.class, "rightfront");
    colorsensor_REV_ColorRangeSensor = hardwareMap.get(ColorSensor.class, "colorsensor");

    // This op mode demonstrates the color and distance features of the REV sensor.
    gain = 2;
    claw.setDirection(Servo.Direction.REVERSE);
    leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
    telemetry.update();
    waitForStart();
    if (opModeIsActive()) {
      if (opModeIsActive()) {
        claw.setPosition(1);
        rightback.setPower(0.25);
        leftback.setPower(0.25);
        leftfront.setPower(0.25);
        rightfront.setPower(0.25);
        sleep(1800);
        claw.setPosition(0);
        rightback.setPower(-0.25);
        leftback.setPower(-0.25);
        leftfront.setPower(-0.25);
        rightfront.setPower(-0.25);
        sleep(1800);
        rightback.setPower(0.25);
        leftback.setPower(-0.25);
        leftfront.setPower(0.25);
        rightfront.setPower(-0.25);
        sleep(250);
        rightback.setPower(0.25);
        leftback.setPower(0.25);
        leftfront.setPower(-0.25);
        rightfront.setPower(-0.25);
        sleep(1950);
        rightback.setPower(0.25);
        leftback.setPower(0.25);
        leftfront.setPower(0.25);
        rightfront.setPower(0.25);
        sleep(1600);
        rightback.setPower(0);
        leftback.setPower(0);
        leftfront.setPower(0);
        rightfront.setPower(0);
      }
      while (opModeIsActive()) {
        // Display reflected light.
        telemetry.addData("Light detected", ((OpticalDistanceSensor) colorsensor_REV_ColorRangeSensor).getLightDetected());
        // Read color from the sensor.
        normalizedColors = ((NormalizedColorSensor) colorsensor_REV_ColorRangeSensor).getNormalizedColors();
        telemetry.addData("Red", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.red, 3)));
        telemetry.addData("Green", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.green, 3)));
        telemetry.addData("Blue", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.blue, 3)));
        // Convert RGB values to Hue, Saturation, and Value.
        // See https://en.wikipedia.org/wiki/HSL_and_HSV for details on HSV color model.
        color = normalizedColors.toColor();
        hue = JavaUtil.colorToHue(color);
        saturation = JavaUtil.colorToSaturation(color);
        value = JavaUtil.colorToValue(color);
        telemetry.addData("Hue", Double.parseDouble(JavaUtil.formatNumber(hue, 0)));
        telemetry.addData("Saturation", Double.parseDouble(JavaUtil.formatNumber(saturation, 3)));
        telemetry.addData("Value", Double.parseDouble(JavaUtil.formatNumber(value, 3)));
        telemetry.addData("Alpha", Double.parseDouble(JavaUtil.formatNumber(normalizedColors.alpha, 3)));
        // Show the color on the Robot Controller screen.
        JavaUtil.showColor(hardwareMap.appContext, color);
        // Use hue to determine if it's red, green, blue, etc..
        telemetry.update();
        if (saturation > 0) {
          if (hue < 30) {
            telemetry.addData("Color", "Red");
            leftback.setPower(-0.25);
            leftfront.setPower(0.25);
            rightback.setPower(0.25);
            rightfront.setPower(-0.25);
            sleep(2600);
            requestOpModeStop();
            telemetry.update();
          } else if (hue < 150) {
            telemetry.addData("Color", "Green");
            leftback.setPower(0);
            leftfront.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            requestOpModeStop();
            telemetry.update();
          } else if (hue < 225) {
            telemetry.addData("Color", "Blue");
            rightback.setPower(-0.25);
            rightfront.setPower(0.25);
            leftback.setPower(0.25);
            leftfront.setPower(-0.25);
            sleep(2600);
            requestOpModeStop();
            telemetry.update();
          }
          telemetry.update();
        }
        telemetry.update();
      }
      telemetry.update();
    }
  }
}
