package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.robot.Timings;
import org.firstinspires.ftc.teamcode.centerstage.vision.RedPropThreshold;

@Config
@Autonomous(name = "!Vision Red Front Auto", group = "Test")
public class VisionAutoRedFront extends LinearOpMode

{
	public static int BACKDROP_ALIGN_LEFT = 1320;
	public static int BACKDROP_ALIGN_CENTER = 1220;
	public static int BACKDROP_ALIGN_RIGHT = 1120;

	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);
		Drive drive = robot.drive;

		RedPropThreshold redPropDetector = robot.camera.initRed();

		robot.drive.init();
		robot.drone.init();
		robot.claw.init();

		sleep(1000);
		robot.arm.init();

		waitForStart();
		robot.arm.armToTicks(Arm.BASE_TICKS);

		String teamPropPosition = redPropDetector.getPropPosition();
		sleep(1500);
		telemetry.addData("Blue Prop Position", teamPropPosition);
		telemetry.update();


		drive.driveStrafe(1);
		sleep(Timings.FIRST_FORWARD);

		drive.driveTurn(-1);
		sleep(Timings.TURN);

		drive.driveStraight(-1);
		sleep(Timings.BACKWARD_FROM_FRONT);

		drive.driveStrafe(1);
		if (teamPropPosition.equals("LEFT")) {
			sleep(BACKDROP_ALIGN_LEFT);
		}  else if (teamPropPosition.equals("RIGHT")) {
			sleep(BACKDROP_ALIGN_RIGHT);
		} else {
			sleep(BACKDROP_ALIGN_CENTER);
		}

		drive.driveStop();
		robot.raiseArm();
		sleep(1000);

		drive.driveStraight(-1);
		sleep(Timings.BACKDROP_BACKWARD);

		robot.openClaw();
		sleep(1000);

		drive.driveStraight(1);
		sleep(Timings.BACKDROP_FORWARD);

		robot.lowerArm();
		drive.driveStraight(-1);
		sleep(Timings.PARK_BACKWARD);

		drive.driveStop();
		robot.lowerArm();

	}
}