package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.util.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayList;

@Autonomous(name = "!Test Locking", group = "Test")
public class LockTest extends LinearOpMode
{
	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.drive.init();

		Pose2d center = new Pose2d(0, 0, 0);

		while (opModeIsActive())
		{
			robot.drive.lockTo(center);

		}
	}
}