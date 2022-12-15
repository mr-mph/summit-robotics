package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@Autonomous(name = "Right Side Red")
public class RightRedAuto extends LinearOpMode {

    private Servo claw;
    private DcMotor leftfront;
    private DcMotor rightback;
    private DcMotor leftback;
    private DcMotor rightfront;
    private DcMotor Slide;
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

        Slide = hardwareMap.get(DcMotor.class, "Slide");

        colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");

        Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftback.setDirection(DcMotorSimple.Direction.REVERSE);
        leftfront.setDirection(DcMotorSimple.Direction.REVERSE);

        rightback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (opModeIsActive()) {

            claw.setPosition(1);
            setSlide(0.1, 100);
            strafeRight(0.25, 1400);
            claw.setPosition(0);
            strafeLeft(0.25, 1400);
            while (opModeIsActive()) {
                rightback.setPower(0.25);
                leftback.setPower(0.25);
                leftfront.setPower(0.25);
                rightfront.setPower(0.25);

                telemetry.addData("Slide encoder", Slide.getCurrentPosition());
                normalizedColors = ((NormalizedColorSensor) colorsensor).getNormalizedColors();
                color = normalizedColors.toColor();
                hue = JavaUtil.colorToHue(color);

                value = JavaUtil.colorToValue(color);
                telemetry.addData("Hue", Double.parseDouble(JavaUtil.formatNumber(hue, 0)));
                telemetry.addData("Value", Double.parseDouble(JavaUtil.formatNumber(value, 3)));// Show the color on the Robot Controller screen.
                JavaUtil.showColor(hardwareMap.appContext, color);
                // Use hue to determine if it's red, green, blue, etc..
                moveForward(0.25, 0);
                if (hue < 30) {
                    telemetry.addData("Color", "Red"); // location 1
                    strafeLeft(0.25, 2600);
                    requestOpModeStop();
                } else if (hue < 150) {
                    telemetry.addData("Color", "Green"); // location 2
                    stopMotors();
                    requestOpModeStop();
                } else if (hue < 225) {
                    telemetry.addData("Color", "Blue"); // location 3
                    strafeRight(0.25, 2600);
                    requestOpModeStop();
                }
                telemetry.update();
            }
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

    private void setSlide(double power, int milliseconds) {
        Slide.setPower(power);
        sleep(milliseconds);
        Slide.setPower(0);
    }
}
