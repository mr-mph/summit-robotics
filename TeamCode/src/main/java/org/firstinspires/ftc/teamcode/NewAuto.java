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

@Autonomous(name = "New Auto (needs work)")
public class NewAuto extends LinearOpMode {

    private Servo claw;
    private DcMotor leftfront;
    private DcMotor rightback;
    private DcMotor leftback;
    private DcMotor rightfront;
    private ColorSensor colorsensor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        NormalizedRGBA normalizedColors;
        int color;
        float hue;
        float value;

        claw = hardwareMap.get(Servo.class, "claw");
        leftfront = hardwareMap.get(DcMotor.class, "leftfront");
        rightback = hardwareMap.get(DcMotor.class, "rightback");
        leftback = hardwareMap.get(DcMotor.class, "leftback");
        rightfront = hardwareMap.get(DcMotor.class, "rightfront");
        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");
        
        claw.setDirection(Servo.Direction.REVERSE);
        leftfront.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        
        if (opModeIsActive()) {
            
            claw.setPosition(1);
            moveForward(0.25, 2800);
            claw.setPosition(0);
            moveBackward(0.25, 1800);
            strafeRight(0.25, 250);
            rotateRight(0.25, 1950);
            moveForward(0.25, 2600);
            stopMotors();
        }
        while (opModeIsActive()) {
            // Display reflected light.
            telemetry.addData("Light detected", ((OpticalDistanceSensor) colorsensor).getLightDetected());
            // Read color from the sensor.
            normalizedColors = ((NormalizedColorSensor) colorsensor).getNormalizedColors();
            // Convert RGB values to Hue, Saturation, and Value.
            // See https://en.wikipedia.org/wiki/HSL_and_HSV for details on HSV color model.
            color = normalizedColors.toColor();
            hue = JavaUtil.colorToHue(color);
            value = JavaUtil.colorToValue(color);
            telemetry.addData("Hue", Double.parseDouble(JavaUtil.formatNumber(hue, 0)));
            telemetry.addData("Value", Double.parseDouble(JavaUtil.formatNumber(value, 3)));// Show the color on the Robot Controller screen.
            JavaUtil.showColor(hardwareMap.appContext, color);
            // Use hue to determine if it's red, green, blue, etc..
            if (hue < 30) {
                telemetry.addData("Color", "Red"); // Strafes to the right
                strafeRight(0.25, 2600);
                requestOpModeStop();
            } else if (hue < 150) {
                telemetry.addData("Color", "Green"); // Stays in place
                stopMotors();
                requestOpModeStop();
            } else if (hue < 225) {
                telemetry.addData("Color", "Blue"); // Strafes to the left
                strafeLeft(0.25, 2600);
                requestOpModeStop();
            }
            telemetry.update();
        }
    }

    private void stopMotors() {
        rightback.setPower(0);
        leftback.setPower(0);
        leftfront.setPower(0);
        rightfront.setPower(0);
    }

    private void moveForward(double speed, int milliseconds) {
        rightback.setPower(speed);
        leftback.setPower(speed);
        leftfront.setPower(speed);
        rightfront.setPower(speed);
        sleep((long) (milliseconds));
        stopMotors();
    }

    private void moveBackward(double speed, int milliseconds) {
        rightback.setPower(-speed);
        leftback.setPower(-speed);
        leftfront.setPower(-speed);
        rightfront.setPower(-speed);
        sleep((long) (milliseconds));
        stopMotors();
    }

    private void strafeRight(double speed, int milliseconds) {
        rightback.setPower(-speed);
        leftback.setPower(speed);
        leftfront.setPower(-speed);
        rightfront.setPower(speed);
        sleep((long) (milliseconds));
        stopMotors();
    }

    private void strafeLeft(double speed, int milliseconds) {
        rightback.setPower(speed);
        leftback.setPower(-speed);
        leftfront.setPower(speed);
        rightfront.setPower(-speed);
        sleep((long) (milliseconds));
        stopMotors();
    }

    private void rotateLeft(double speed, int milliseconds) {
        rightback.setPower(speed);
        leftback.setPower(-speed);
        leftfront.setPower(-speed);
        rightfront.setPower(speed);
        sleep((long) (milliseconds));
        stopMotors();
    }

    private void rotateRight(double speed, int milliseconds) {
        rightback.setPower(-speed);
        leftback.setPower(speed);
        leftfront.setPower(speed);
        rightfront.setPower(-speed);
        sleep((long) (milliseconds));
        stopMotors();
    }
}
