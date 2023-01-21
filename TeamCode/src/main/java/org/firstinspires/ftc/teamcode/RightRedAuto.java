package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "BlueBlue")
public class RightRedAuto extends LinearOpMode {

    private CRServo clawleft;
    private CRServo clawright;
    private DcMotor slideleft;
    private DcMotor slideright;
    private ColorSensor colorsensor;

    SampleMecanumDrive drive;


    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        NormalizedRGBA normalizedColors;
        int color;
        float hue;
        float value;
        String path;

        CRServo clawleft = hardwareMap.get(CRServo.class, "clawleft");
        CRServo clawright = hardwareMap.get(CRServo.class, "clawright");
        DcMotor slideleft = hardwareMap.get(DcMotor.class, "slideleft");
        DcMotor slideright = hardwareMap.get(DcMotor.class, "slideright");

        drive = new SampleMecanumDrive(hardwareMap);

        ColorSensor colorsensor = hardwareMap.get(ColorSensor.class, "colorsensor");


        slideleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {

                //close claw
                clawleft.setPower(0.1);
                clawright.setPower(-0.1);


                Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                        .back(35)
                        .build();
                drive.followTrajectory(myTrajectory); //go backwards & stop right in front of color sleeve

                telemetry.addData("Slide encoder", slideleft.getCurrentPosition());
                normalizedColors = ((NormalizedColorSensor) colorsensor).getNormalizedColors();
                color = normalizedColors.toColor();
                hue = JavaUtil.colorToHue(color);

                value = JavaUtil.colorToValue(color);
                telemetry.addData("Hue", Double.parseDouble(JavaUtil.formatNumber(hue, 0)));
                telemetry.addData("Value", Double.parseDouble(JavaUtil.formatNumber(value, 3)));// Show the color on the Robot Controller screen.
                JavaUtil.showColor(hardwareMap.appContext, color);
                // Use hue to determine if it's red, green, blue, etc..
                if (hue < 30) {
                    telemetry.addData("Color", "Red"); // location 1
                    redpath();
                } else if (hue < 150) {
                    telemetry.addData("Color", "Green"); // location 2
                    greenpath();
                } else if (hue < 225) {
                    bluepath();
                    telemetry.addData("Color", "Blue"); // location 3
                }
                telemetry.update();
            }
        }
    }

    private void redpath() {
        Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(30)
                .build();
        //strafe left slightly to be in location 1, put trajectory inside
        drive.followTrajectory(myTrajectory);
    }

    private void greenpath() {
        //strafe right(more than 1 tile, 30 ish inches?), put trajectory inside
    }

    private void bluepath() {
        //strafe right(more than 2 tile, 54 inches?), put trajectory inside
    }

    //private void stopMotors() {
        //rightRear.setPower(0);
        //leftRear.setPower(0);
        //leftFront.setPower(0);
        //rightFront.setPower(0);
    }

    //private void moveForward(double speed, int milliseconds) {
        //rightRear.setPower(speed);
        //leftRear.setPower(speed);
        //leftFront.setPower(speed);
        //rightFront.setPower(speed);
        //sleep((long) (milliseconds));
        //stopMotors();
    //}

    //private void moveBackward(double speed, int milliseconds) {
        //rightRear.setPower(-speed);
        //leftRear.setPower(-speed);
        //leftFront.setPower(-speed);
        //rightFront.setPower(-speed);
        //sleep((long) (milliseconds));
        //stopMotors();
    //}

    //private void strafeRight(double speed, int milliseconds) {
        //rightback.setPower(-speed);
        //leftback.setPower(speed);
        //leftfront.setPower(-speed);
        //rightfront.setPower(speed);
        //sleep((long) (milliseconds));
        //stopMotors();
    //}

    //private void strafeLeft(double speed, int milliseconds) {
        //rightback.setPower(speed);
        //leftback.setPower(-speed);
        //leftfront.setPower(speed);
        //rightfront.setPower(-speed);
        //sleep((long) (milliseconds));
        //stopMotors();
    //}

    //private void rotateLeft(double speed, int milliseconds) {
        //rightback.setPower(-speed);
        //leftback.setPower(speed);
        //leftfront.setPower(speed);
        //rightfront.setPower(-speed);
        //sleep((long) (milliseconds));
        //stopMotors();
    //}

    //private void rotateRight(double speed, int milliseconds) {
        //rightback.setPower(speed);
        //leftback.setPower(-speed);
        //leftfront.setPower(-speed);
        //rightfront.setPower(speed);
        //sleep((long) (milliseconds));
        //stopMotors();
    //}
//why make a function for the slide again?
    //private void setSlide(double power, int milliseconds) {
        //slideleft.setPower(power);
        //slideright.setPower(-power);
        //sleep(milliseconds);
        //slideleft.setPower(0);
        //slideright.setPower(0);
    //}
//}
