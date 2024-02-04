package org.firstinspires.ftc.teamcode.centerstage.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Config
@Disabled
@Autonomous(name = "!!Turning Test")
public class TurningTest extends LinearOpMode {
	public static double TURN_ANGLE = 90;

	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);


		robot.drive.init();
		robot.arm.init();
		robot.wrist.init(false);

		TrajectorySequence turn = robot.drive.mecanumDrive.trajectorySequenceBuilder(new Pose2d(0, 0, 0))
				.turn(Math.toRadians(TURN_ANGLE))
				.build();
		waitForStart();


		while (!isStopRequested()) {
			robot.drive.mecanumDrive.followTrajectorySequence(turn);

		}
	}
}