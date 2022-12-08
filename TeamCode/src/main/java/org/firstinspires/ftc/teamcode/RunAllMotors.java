package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "RunAllMotors (Blocks to Java)")
public class RunAllMotors extends LinearOpMode {

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
        leftfront.setPower(0.4);
        rightfront.setPower(0.4);
        rightback.setPower(0.4);
        leftback.setPower(0.4);
      }
    }
  }
}
