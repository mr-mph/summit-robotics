package org.firstinspires.ftc.teamcode.centerstage.unused;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;

@Disabled
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
//			robot.drive.lockTo(center);

		}
	}
}