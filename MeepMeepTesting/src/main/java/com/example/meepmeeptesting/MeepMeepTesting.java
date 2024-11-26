package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.ActionTimeline;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
	public static void main(String[] args) {
		Pose2d startPose = new Pose2d(6,-63, Math.toRadians(90));

		MeepMeep meepMeep = new MeepMeep(800);

		RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
				// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
				.setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
				.build();

		DriveShim drive = myBot.getDrive();

		TrajectoryActionBuilder specimenPlace = drive.actionBuilder(startPose)
				.strafeTo(new Vector2d(6, -34)) //  place on high chamber
				.endTrajectory();

		TrajectoryActionBuilder specimenPlace2 = specimenPlace.fresh()
				.strafeTo(new Vector2d(6, -38)) //  back up
				.endTrajectory();

		TrajectoryActionBuilder scoot = specimenPlace2.fresh()
				.strafeTo(new Vector2d(0, -38)) //  back up
				.endTrajectory();


//		TrajectoryActionBuilder backtoStart = specimenPlace.fresh()
//				.strafeTo(new Vector2d(6,-63));


		TrajectoryActionBuilder pushSample = scoot.fresh()
				.strafeToLinearHeading(new Vector2d(36,-35), Math.toRadians(0))
				.strafeTo(new Vector2d(36,-13)) // off to the side


				.strafeTo(new Vector2d(46,-13)) // 1st initial
				.strafeTo(new Vector2d(46,-58)) // 1st in
				.strafeTo(new Vector2d(46,-50)) // back out
				.endTrajectory();

		TrajectoryActionBuilder pushSample2 = pushSample.fresh()
				.turnTo(Math.toRadians(-90))
				.endTrajectory();

		TrajectoryActionBuilder moveTo3rd = scoot.fresh()
				.strafeToLinearHeading(new Vector2d(46,-50), Math.toRadians(-90))
				.endTrajectory();


		TrajectoryActionBuilder forward = pushSample2.fresh()
				.strafeTo(new Vector2d(46,-54)) // in to grab sample
				.endTrajectory();


		TrajectoryActionBuilder forward2 = forward.fresh()
				.strafeTo(new Vector2d(46,-55), new TranslationalVelConstraint(5)) // in to grab sample
				.endTrajectory();


		TrajectoryActionBuilder backAgain = forward2.fresh()
				.strafeTo(new Vector2d(46,-44)) // in to grab sample
				.endTrajectory();

		TrajectoryActionBuilder scoreSpecimen2 = backAgain.fresh()
				.strafeToLinearHeading(new Vector2d(6,-40),Math.toRadians(90)) // read to place specimen
				.strafeTo(new Vector2d(6,-33))
				.endTrajectory();


		TrajectoryActionBuilder park = scoreSpecimen2.fresh()
				.strafeTo(new Vector2d(34, -60))
				.endTrajectory();

		myBot.runAction(new SequentialAction(
				new InstantAction(() -> {
	//			robot.arm.armMotor.setPower(0.6);
	//			robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
	//			robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				specimenPlace.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
//					robot.wrist.bringdown();
				}),
				specimenPlace2.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
//					robot.wrist.high_rung();
				}),
				scoot.build(),
				new InstantAction(() -> {
//					robot.claw.open();
				}),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.BASKET_TICKS);
				}),
				pushSample.build(),
				new InstantAction(() -> {
//					robot.wrist.wall();
//					robot.arm.armToTicks(Arm.WALL_TICKS);
				}),
				pushSample2.build(),
				new SleepAction(0.5),
				forward.build(),
				new SleepAction(0.5),
				forward2.build(),
				new SleepAction(0.5),
				new InstantAction(() -> {
//					robot.claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
//					robot.arm.armToTicks(100);
				}),
				new SleepAction(0.3),
				backAgain.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				scoreSpecimen2.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
//					robot.wrist.bringdown();
				}),
				specimenPlace2.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
//					robot.wrist.high_rung();
				}),
				scoot.build(),
				new InstantAction(() -> {
//					robot.claw.open();
				}),


				moveTo3rd.build(),
				new InstantAction(() -> {
//					robot.wrist.wall();
//					robot.arm.armToTicks(Arm.WALL_TICKS);
				}),
				new SleepAction(0.5),
				forward.build(),
				new SleepAction(0.5),
				forward2.build(),
				new SleepAction(0.5),
				new InstantAction(() -> {
//					robot.claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
//					robot.arm.armToTicks(100);
				}),
				new SleepAction(0.3),
				backAgain.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				scoreSpecimen2.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
//					robot.wrist.bringdown();
				}),
				specimenPlace2.build(),
				new InstantAction(() -> {
//					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
//					robot.wrist.high_rung();
				}),
				scoot.build(),
				new InstantAction(() -> {
//					robot.claw.open();
				}),

				park.build(),
				new InstantAction(() -> {
//					robot.wrist.wall();
//					robot.arm.armToTicks(Arm.WALL_TICKS);
				}), new SleepAction(0.5)));


//		myBot.runAction(specimenPlace.build());
//		myBot.runAction(backOut.build());
//		myBot.runAction(scoreSpecimen2.build());
//		myBot.runAction(park.build());


		meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
				.setDarkMode(false)
				.setBackgroundAlpha(0.95f)
				.addEntity(myBot)
				.start();
	}
}