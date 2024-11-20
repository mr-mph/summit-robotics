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
				.strafeTo(new Vector2d(6, -30));


		TrajectoryActionBuilder backOut = drive.actionBuilder(new Pose2d(new Vector2d(6, -30), Math.toRadians(90)))
				.strafeTo(new Vector2d(6,-45));


		TrajectoryActionBuilder auto = drive.actionBuilder(new Pose2d(new Vector2d(6, -45), Math.toRadians(90)))
				.strafeToLinearHeading(new Vector2d(36,-39), Math.toRadians(0))
				.strafeTo(new Vector2d(36,-9)) // off to the side

				.strafeTo(new Vector2d(48,-13)) // 1st initial
				.strafeTo(new Vector2d(48,-62)) // 1st in
				.strafeToLinearHeading(new Vector2d(48,-40),Math.toRadians(-90)) // back out and turn
				.strafeTo(new Vector2d(48,-62)); // in to grab sample


		TrajectoryActionBuilder park = drive.actionBuilder(new Pose2d(new Vector2d(42,-51), Math.toRadians(90)))
				.strafeTo(new Vector2d(34, -63));

//		myBot.runAction(specimenPlace.build());
//		myBot.runAction(backOut.build());
		myBot.runAction(auto.build());
//		myBot.runAction(park.build());


		meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
				.setDarkMode(true)
				.setBackgroundAlpha(0.95f)
				.addEntity(myBot)
				.start();
	}
}