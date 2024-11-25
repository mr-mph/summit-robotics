package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
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

		TrajectoryActionBuilder allInOne = drive.actionBuilder(startPose)
				.strafeTo(new Vector2d(6, -34)) //  place on high chamber
				.strafeTo(new Vector2d(6, -38)) //  back up
				.strafeToLinearHeading(new Vector2d(36,-35), Math.toRadians(0))
				.strafeTo(new Vector2d(36,-13)) // off to the side
				.strafeTo(new Vector2d(46,-13)) // 1st initial
				.strafeTo(new Vector2d(46,-58)) // 1st in
				.strafeToLinearHeading(new Vector2d(46,-50),Math.toRadians(-90)) // back out and turn
				.strafeTo(new Vector2d(46,-54)) // in to grab sample
				.strafeTo(new Vector2d(46,-56)) // in to grab sample
				.strafeTo(new Vector2d(46,-44)) // in to grab sample
				.strafeToLinearHeading(new Vector2d(6,-33),Math.toRadians(90))
				.strafeTo(new Vector2d(34, -60));// read to place specimen




//		myBot.runAction(specimenPlace.build());
//		myBot.runAction(backOut.build());
		myBot.runAction(allInOne.build());
//		myBot.runAction(park.build());


		meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
				.setDarkMode(false)
				.setBackgroundAlpha(0.95f)
				.addEntity(myBot)
				.start();
	}
}