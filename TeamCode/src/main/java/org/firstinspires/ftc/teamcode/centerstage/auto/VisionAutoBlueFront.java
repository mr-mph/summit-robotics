package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.robot.Timings;
import org.firstinspires.ftc.teamcode.centerstage.vision.BluePropThreshold;

@Config
@Autonomous(name = "!Vision Blue Front Auto", group = "Test")
public class VisionAutoBlueFront extends LinearOpMode

{
	public static int BACKDROP_ALIGN_LEFT = 1320;
	public static int BACKDROP_ALIGN_CENTER = 1220;
	public static int BACKDROP_ALIGN_RIGHT = 1120;

	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);
		Drive drive = robot.drive;

		BluePropThreshold bluePropDetector = robot.camera.initBlue();

		robot.drive.init();
		robot.drone.init();
		robot.claw.init();

		sleep(1000);
		robot.arm.init();

		waitForStart();
		robot.arm.armToTicks(Arm.BASE_TICKS);

		String teamPropPosition = bluePropDetector.getPropPosition();
		sleep(1500);

		drive.driveStrafe(-1);
		sleep(Timings.FIRST_STRAFE);

		drive.driveStraight(1);
		sleep(Timings.FORWARD_FROM_FRONT);

		drive.driveStrafe(-1);
		if (teamPropPosition.equals("LEFT")) {
			sleep(BACKDROP_ALIGN_LEFT);
		}  else if (teamPropPosition.equals("RIGHT")) {
			sleep(BACKDROP_ALIGN_RIGHT);
		} else {
			sleep(BACKDROP_ALIGN_CENTER);
		}

		drive.driveStop();
		robot.arm.armToTicks(Arm.BACKDROP_TICKS);
		sleep(1000);

		drive.driveStraight(1);
		sleep(Timings.BACKDROP_FORWARD);
		drive.driveStop();

		robot.claw.topClawClosed = false;
		robot.claw.open(robot.claw.clawtop);
		sleep(1000);

		drive.driveStraight(-1);
		sleep(Timings.BACKDROP_BACKWARD);

		drive.driveTurn(-1);
		sleep(Timings.TURN_AROUND);

		drive.driveStraight(-1);
		sleep(Timings.PARK_BACKWARD);

		drive.driveStop();

	}
}