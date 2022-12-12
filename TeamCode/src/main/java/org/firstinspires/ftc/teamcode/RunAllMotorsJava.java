package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "RunAllMotorsJava")
public class RunAllMotorsJava extends LinearOpMode {

  private DcMotor leftfront;
  private DcMotor rightfront;
  private DcMotor rightback;
  private DcMotor leftback;

  /**
   * This function is executed when this Op Mode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    leftfront = hardwareMap.get(DcMotor.class, "leftfront");
    rightfront = hardwareMap.get(DcMotor.class, "rightfront");
    rightback = hardwareMap.get(DcMotor.class, "rightback");
    leftback = hardwareMap.get(DcMotor.class, "leftback");

    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        telemetry.addLine("Running leftfront motor");
        leftfront.setPower(0.4);
        telemetry.update();
        sleep(3000);
        leftfront.setPower(0.0);
        telemetry.clear();

        telemetry.addLine("Running rightfront motor");
        rightfront.setPower(0.4);
        telemetry.update();
        sleep(3000);
        rightfront.setPower(0.0);
        telemetry.clear();

        telemetry.addLine("Running rightback motor");
        rightback.setPower(0.4);
        telemetry.update();
        sleep(3000);
        rightback.setPower(0.0);
        telemetry.clear();

        telemetry.addLine("Running leftback motor");
        leftback.setPower(0.4);
        telemetry.update();
        sleep(3000);
        leftback.setPower(0.0);
        telemetry.clear();
      }
    }
  }
}
