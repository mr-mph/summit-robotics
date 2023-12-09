package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;

@Config
@Autonomous(name = "!!Blue Back Auto", group = "Auto")
public class TimingAutoBlueBack extends LinearOpMode

{
	public static int FIRST_STRAFE = 300;
	public static int FORWARD = 2950;
	public static int BACKDROP_ALIGN_STRAFE = 1320;
	public static int BACKDROP_FORWARD = 800;
	public static int WAIT_FOR_ARM_TO_LIFT = 1000;
	public static int WAIT_FOR_RELEASE = 1000;
	public static int BACKDROP_BACKWARD = 1000;
	public static int TURN_AROUND = 1400;

	public static int FINAL_BACKWARD = 800;


	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);
		Drive drive = robot.drive;

		robot.drive.init();
		robot.drone.init();
		robot.claw.init();

		sleep(1000);
		robot.arm.init();

		waitForStart();
		robot.arm.armToTicks(Arm.BASE_TICKS);
		sleep(1500);


		drive.driveStrafe(1);
		sleep(FIRST_STRAFE);

		drive.driveStop();
		sleep(15000); // temp

		drive.driveStraight(1);
		sleep(FORWARD);

		drive.driveStrafe(1);
		sleep(BACKDROP_ALIGN_STRAFE);

		drive.driveStop();
		robot.arm.armToTicks(Arm.BACKDROP_TICKS);
		sleep(WAIT_FOR_ARM_TO_LIFT);

		drive.driveStraight(1);
		sleep(BACKDROP_FORWARD);
		drive.driveStop();

		robot.claw.topClawClosed = false;
		robot.claw.open(robot.claw.clawtop);
		sleep(WAIT_FOR_RELEASE);

		drive.driveStraight(-1);
		sleep(BACKDROP_BACKWARD);

		drive.driveTurn(1);
		sleep(TURN_AROUND);

		drive.driveStraight(-1);
		sleep(FINAL_BACKWARD);

		drive.driveStop();

	}
}