package org.firstinspires.ftc.teamcode.intothedeep.auto;

import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Wrist;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;


@Config
@Autonomous(name = "!! Extra Specimen Auto Test ", group = "Auto")
public class CloseAuto2 extends LinearOpMode

{

	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);

		Pose2d startPose = new Pose2d(6,-63, Math.toRadians(90));

		robot.drive.init(startPose);
		MecanumDrive drive = robot.drive.mecanumDrive;

		robot.claw.init();

		robot.arm.init();
		robot.wrist.init(true);


		waitForStart();

		TrajectoryActionBuilder specimenPlace = drive.actionBuilder(startPose)
				.strafeTo(new Vector2d(6, -34)); //  place on high chamber

		TrajectoryActionBuilder specimenPlace2 = drive.actionBuilder(new Pose2d(6,-34, Math.toRadians(90)))
				.strafeTo(new Vector2d(6, -38)); //  back up


		TrajectoryActionBuilder backtoStart = drive.actionBuilder(new Pose2d(new Vector2d(6, -38), Math.toRadians(90)))
				.strafeTo(new Vector2d(6,-63));


		TrajectoryActionBuilder pushSample = drive.actionBuilder(new Pose2d(new Vector2d(6, -38), Math.toRadians(90)))
				.strafeToLinearHeading(new Vector2d(36,-35), Math.toRadians(0))
				.strafeTo(new Vector2d(36,-13)) // off to the side


				.strafeTo(new Vector2d(46,-13)) // 1st initial
				.strafeTo(new Vector2d(46,-58)) // 1st in
				.strafeToLinearHeading(new Vector2d(46,-50),Math.toRadians(-90)); // back out and turn

		TrajectoryActionBuilder forward = drive.actionBuilder(new Pose2d(new Vector2d(46, -50), Math.toRadians(-90)))
				.strafeTo(new Vector2d(46,-54)); // in to grab sample

		TrajectoryActionBuilder forward2 = drive.actionBuilder(new Pose2d(new Vector2d(46, -54), Math.toRadians(-90)))
				.strafeTo(new Vector2d(46,-56)); // in to grab sample

		TrajectoryActionBuilder backAgain = drive.actionBuilder(new Pose2d(new Vector2d(46, -56), Math.toRadians(-90)))
				.strafeTo(new Vector2d(46,-44)); // in to grab sample

		TrajectoryActionBuilder scoreSpecimen2 = drive.actionBuilder(new Pose2d(new Vector2d(46, -44), Math.toRadians(-90)))
				.strafeToLinearHeading(new Vector2d(6,-33),Math.toRadians(90)); // read to place specimen


		TrajectoryActionBuilder park = drive.actionBuilder(new Pose2d(new Vector2d(6,-33), Math.toRadians(90)))
				.strafeTo(new Vector2d(34, -60));


		Actions.runBlocking(new SequentialAction(
				new InstantAction(() -> {
					robot.arm.armMotor.setPower(0.6);
					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				specimenPlace.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					robot.wrist.bringdown();
				}),
				specimenPlace2.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					robot.wrist.high_rung();
					robot.claw.open();
				}),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.BASKET_TICKS);
				}),
				pushSample.build(),
				new InstantAction(() -> {
					robot.wrist.wall();
					robot.arm.armToTicks(Arm.WALL_TICKS);
				}),
				new SleepAction(1),
				forward.build(),
				new SleepAction(0.5),
				forward2.build(),
				new SleepAction(0.5),
				new InstantAction(() -> {
					robot.claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
					robot.arm.armToTicks(100);
				}),
				new SleepAction(0.3),
				backAgain.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				scoreSpecimen2.build(),

				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					robot.wrist.bringdown();
				}),
				specimenPlace2.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					robot.wrist.high_rung();
					robot.claw.open();
				}),
				park.build(),
				new InstantAction(() -> {
					robot.wrist.wall();
					robot.arm.armToTicks(Arm.WALL_TICKS);
				}), new SleepAction(0.5)
		));

		// TEST: Specimen placing
//		Actions.runBlocking(new SequentialAction(
//				new InstantAction(() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
//				}),
//				specimenPlace.build(),
//				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
//					robot.wrist.bringdown();
//				}),
//				specimenPlace2.build(),
//				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
//					robot.wrist.high_rung();
//					robot.claw.open();
//				}),
//				backtoStart.build(),
//				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.WALL_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_WALL);
//				}), new SleepAction(0.5)
//		));



	}


}