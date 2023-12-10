package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.robot.Timings;

@Autonomous(name = "!!Blue Front Auto", group = "Auto")
public class TimingAutoBlueFront extends LinearOpMode
{
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
		sleep(Timings.FIRST_STRAFE);

		drive.driveStraight(1);
		sleep(Timings.FORWARD_FROM_FRONT);

		drive.driveStrafe(1);
		sleep(Timings.BACKDROP_ALIGN_STRAFE);

		drive.driveStop();
		robot.arm.armToTicks(Arm.BACKDROP_TICKS);
		sleep(Timings.WAIT_FOR_ARM_TO_LIFT);

		drive.driveStraight(1);
		sleep(Timings.BACKDROP_FORWARD);
		drive.driveStop();

		robot.claw.topClawClosed = false;
		robot.claw.open(robot.claw.clawtop);
		sleep(Timings.WAIT_FOR_RELEASE);

		drive.driveStraight(-1);
		sleep(Timings.BACKDROP_BACKWARD);

		drive.driveTurn(1);
		sleep(Timings.TURN_AROUND);

		drive.driveStraight(-1);
		sleep(Timings.FINAL_BACKWARD);

		drive.driveStop();

	}
}