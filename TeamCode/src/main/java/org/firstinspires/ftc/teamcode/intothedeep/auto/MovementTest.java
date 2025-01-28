package org.firstinspires.ftc.teamcode.intothedeep.auto;

import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Wrist;
import org.firstinspires.ftc.teamcode.MecanumDrive;


@Config
@Autonomous(name = "Auto Movement Test", group = "! Auto")
public class MovementTest extends LinearOpMode

{

	@Override
	public void runOpMode() {
		Robot robot = new Robot(hardwareMap);

		Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));

		robot.drive.init(startPose);
		MecanumDrive drive = robot.drive.mecanumDrive;

		robot.claw.init();

		robot.arm.init();
		robot.wrist.init(true);


		waitForStart();
		robot.wrist.wall();
		robot.arm.armToTicks(Arm.WALL_TICKS);

		TrajectoryActionBuilder square = drive.actionBuilder(new Pose2d(0,0, Math.toRadians(90)))
				.setTangent(90)
				.splineToLinearHeading(new Pose2d(0, 24, Math.toRadians(90)), Math.toRadians(0), new TranslationalVelConstraint(20))
				.splineToLinearHeading(new Pose2d(24, 24, Math.toRadians(90)), Math.toRadians(-90), new TranslationalVelConstraint(20))
				.splineToLinearHeading(new Pose2d(24, 0, Math.toRadians(90)), Math.toRadians(-180), new TranslationalVelConstraint(20))
				.splineToLinearHeading(new Pose2d(0, 0, Math.toRadians(90)), Math.toRadians(-270), new TranslationalVelConstraint(20))

				.endTrajectory();


		while (!isStopRequested()) {
			Actions.runBlocking(square.build());
		}
	}



}