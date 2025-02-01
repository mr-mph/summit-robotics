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
				.setConstraints(65, 60, Math.toRadians(180), Math.toRadians(180), 11)
				.build();

		DriveShim drive = myBot.getDrive();

		TrajectoryActionBuilder specimenPlace_1 = drive.actionBuilder(startPose)
				.strafeTo(new Vector2d(6, -30)) //  place on high chamber
				.endTrajectory(); // should be 10, -34 used to be -33.5

		TrajectoryActionBuilder specimenPlace2_1 = specimenPlace_1.fresh()
				.strafeTo(new Vector2d(9, -38)) //  back up
				.endTrajectory();



//		TrajectoryActionBuilder scoot = specimenPlace2.fresh()
//				.strafeTo(new Vector2d(0, -38)) // scoot
//				.endTrajectory();

//		TrajectoryActionBuilder backtoStart = specimenPlace.fresh()
//				.strafeTo(new Vector2d(6,-63));


		TrajectoryActionBuilder pushSample = specimenPlace2_1.fresh()
				.strafeToLinearHeading(new Vector2d(34,-38), Math.toRadians(0))
				.setTangent(Math.toRadians(90))

//				.splineToConstantHeading(new Vector2d(24,-38), Math.toRadians(0)) // 1st sample
				.splineToConstantHeading(new Vector2d(34,-18), Math.toRadians(90))
				.splineToConstantHeading(new Vector2d(46,-18), Math.toRadians(-90))
				.splineToConstantHeading(new Vector2d(46,-50), Math.toRadians(90))

				.endTrajectory();


		TrajectoryActionBuilder pushSample_2 = pushSample.fresh()

//				.splineToConstantHeading(new Vector2d(46,-18), Math.toRadians(90))
//				.splineToConstantHeading(new Vector2d(54,-18), Math.toRadians(-90))
//				.splineToConstantHeading(new Vector2d(54,-50), Math.toRadians(90))
				.endTrajectory();

		TrajectoryActionBuilder pushSample2 = pushSample_2.fresh()
//				.turnTo(Math.toRadians(-90))
				.strafeToLinearHeading(new Vector2d(46,-50), Math.toRadians(-90))
				.endTrajectory();



		TrajectoryActionBuilder forward = pushSample2.fresh()
				.strafeTo(new Vector2d(46,-56), new TranslationalVelConstraint(5)) // in to grab sample
				.endTrajectory(); // should be -54

		TrajectoryActionBuilder forward_2 = pushSample2.fresh()
				.strafeTo(new Vector2d(46,-57), new TranslationalVelConstraint(5)) // in to grab sample
				.endTrajectory(); // should be -54 (was -52.5)

		TrajectoryActionBuilder backAgain = forward.fresh()
				.strafeTo(new Vector2d(46,-52)) // in to grab sample
				.endTrajectory();

		TrajectoryActionBuilder backAgain2 = forward_2.fresh()
				.strafeTo(new Vector2d(46,-52)) // in to grab sample
				.endTrajectory();

		TrajectoryActionBuilder scoreSpecimen2 = backAgain.fresh()
				.setTangent(Math.toRadians(90))
				.splineToLinearHeading(new Pose2d(3,-30, Math.toRadians(90)),Math.toRadians(90)) // ready to place specimen
//				.splineToConstantHeading(new Vector2d(3,-29), Math.toRadians(90)) // should be -34 was -30

//				.strafeToLinearHeading(new Vector2d(6,-28),Math.toRadians(90)) // ready to place specimen

				.endTrajectory();

		TrajectoryActionBuilder specimenPlace2_2 = scoreSpecimen2.fresh()
				.strafeTo(new Vector2d(3, -38)) //  back up
				.endTrajectory();

		TrajectoryActionBuilder backUp = specimenPlace2_2.fresh()
				.strafeTo(new Vector2d(3, -42)) //  back up
				.endTrajectory();

		TrajectoryActionBuilder moveTo3rd = backUp.fresh()
				.setTangent(Math.toRadians(-90))
				.splineToLinearHeading(new Pose2d(46,-50, Math.toRadians(-90)), Math.toRadians(-90)) // was -50
				.endTrajectory(); // maybe needs to be changed back to -50 or less?


		TrajectoryActionBuilder scoreSpecimen3 = backAgain.fresh()
				.splineToLinearHeading(new Pose2d(-1,-30, Math.toRadians(90)),Math.toRadians(90)) // read to place specimen
//				.strafeTo(new Vector2d(1,-29)) // should be -34 was 27.75

//				.strafeToLinearHeading(new Vector2d(2,-25.75),Math.toRadians(90)) // read to place specimen
				.endTrajectory();


		TrajectoryActionBuilder specimenPlace2_3 = scoreSpecimen3.fresh()
				.strafeTo(new Vector2d(-1, -38)) //  back up
				.endTrajectory();

		TrajectoryActionBuilder backUp2 = specimenPlace2_3.fresh()
				.strafeTo(new Vector2d(-1,-42)) // in to grab sample
				.endTrajectory();

		TrajectoryActionBuilder park = specimenPlace2_3.fresh()
				.strafeTo(new Vector2d(34, -58)) // should be up to -63? weird
				.endTrajectory();





		myBot.runAction(new SequentialAction(
				new InstantAction(() -> {
					//arm.armMotor.setPower(0.6);
					//arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					//wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				specimenPlace_1.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					//wrist.bringdown();
				}),
				specimenPlace2_1.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					//wrist.high_rung();
				}),
				new InstantAction(() -> {
					//claw.open();
				}),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.BASKET_TICKS);
				}),
				pushSample.build(),
				pushSample_2.build(),
				new InstantAction(() -> {
					//wrist.wall();
					//arm.armToTicks(Arm.WALL_TICKS);
				}),
				pushSample2.build(),
//				new SleepAction(0.5),
				forward.build(),
				new SleepAction(0.5),
				new InstantAction(() -> {
					//claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
					//arm.armToTicks(100);
				}),
//				new SleepAction(0.3),
				backAgain.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					//wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				scoreSpecimen2.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					//wrist.bringdown();
				}),
				specimenPlace2_2.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					//wrist.high_rung();
				}),
				new InstantAction(() -> {
					//claw.open();
				}),


				backUp.build(),
				new InstantAction(() -> {
					//wrist.wall();
					//arm.armToTicks(Arm.WALL_TICKS);
				}),
				moveTo3rd.build(),
//				new SleepAction(0.5),
				forward_2.build(),
				new SleepAction(0.5),
				new InstantAction(() -> {
					//claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
					//arm.armToTicks(100);
				}),
//				new SleepAction(0.3),
				backAgain2.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					//wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				scoreSpecimen3.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					//wrist.bringdown();
				}),
				specimenPlace2_3.build(),
				new InstantAction(() -> {
					//arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					//wrist.high_rung();
				}),
				new InstantAction(() -> {
					//claw.open();
				}),
				park.build(),
				new InstantAction(() -> {
					//wrist.wall();
					//arm.armToTicks(Arm.WALL_TICKS);
				}), new SleepAction(0.5)
		));

//		myBot.runAction(
//				drive.actionBuilder(new Pose2d(0, 0, 0))
//						.splineTo(new Vector2d(30, 30), Math.PI / 2)
//						.splineTo(new Vector2d(0, 60), Math.PI)
//						.setReversed(true)
//						.splineTo(new Vector2d(30, 30), -Math.PI / 2)
//						.splineTo(new Vector2d(0, 0), -Math.PI)
//
//
//
//						.build());


//		TrajectoryActionBuilder square = drive.actionBuilder(new Pose2d(0,0, Math.toRadians(90)))
//				.setTangent(90)
//				.splineToLinearHeading(new Pose2d(0, 24, Math.toRadians(90)), Math.toRadians(0), new TranslationalVelConstraint(20))
//				.splineToLinearHeading(new Pose2d(24, 24, Math.toRadians(90)), Math.toRadians(-90), new TranslationalVelConstraint(20))
//				.splineToLinearHeading(new Pose2d(24, 0, Math.toRadians(90)), Math.toRadians(-180), new TranslationalVelConstraint(20))
//				.splineToLinearHeading(new Pose2d(0, 0, Math.toRadians(90)), Math.toRadians(-270), new TranslationalVelConstraint(20))
//
//				.endTrajectory();
////
//
//		myBot.runAction(square.build());


//		myBot.runAction(specimenPlace.build());
//		myBot.runAction(backOut.build());
//		myBot.runAction(scoreSpecimen2.build());
//		myBot.runAction(park.build());

		TrajectoryActionBuilder square = drive.actionBuilder(new Pose2d(0,0, Math.toRadians(90)))
				.strafeToLinearHeading(new Vector2d(0, 24), Math.toRadians(0), new TranslationalVelConstraint(20))
				.strafeToLinearHeading(new Vector2d(24, 24), Math.toRadians(-90), new TranslationalVelConstraint(20))
				.strafeToLinearHeading(new Vector2d(24, 0), Math.toRadians(-180), new TranslationalVelConstraint(20))
				.strafeToLinearHeading(new Vector2d(0, 0), Math.toRadians(-270), new TranslationalVelConstraint(20))

				.endTrajectory();

//		myBot.runAction(square.build());


		meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
				.setDarkMode(false)
				.setBackgroundAlpha(0.95f)
				.addEntity(myBot)
				.start();
	}
}