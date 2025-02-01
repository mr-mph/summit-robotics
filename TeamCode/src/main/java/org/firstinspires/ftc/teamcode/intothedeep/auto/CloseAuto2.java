package org.firstinspires.ftc.teamcode.intothedeep.auto;

import com.acmerobotics.dashboard.config.Config;

import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Wrist;


@Config
@Autonomous(name = "!! 3 Specimen Close Auto ", group = "! Auto")
public class CloseAuto2 extends LinearOpMode

{

	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);

		Pose2d startPose = new Pose2d(6,-63, Math.toRadians(90));

		robot.drive.init(startPose);
		PinpointDrive drive = robot.drive.mecanumDrive;

		robot.claw.init();
		robot.extender.init();


		robot.arm.init();
		robot.wrist.init(true);


		waitForStart();

		TrajectoryActionBuilder specimenPlace_1 = drive.actionBuilder(startPose)
				.strafeTo(new Vector2d(6, -29)) //  place on high chamber
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
//				.strafeToLinearHeading(new Vector2d(28,-38), Math.toRadians(-90)) // 1st sample
//				.setTangent(Math.toRadians(0))
//				.splineToConstantHeading(new Vector2d(36,-13), Math.toRadians(90))
//				.splineToConstantHeading(new Vector2d(46,-13), Math.toRadians(-90))
//				.splineToConstantHeading(new Vector2d(46,-54), Math.toRadians(90))


				.strafeToLinearHeading(new Vector2d(36,-38), Math.toRadians(0)) // 1st sample
				.strafeTo(new Vector2d(36,-13)) // off to the side
				.strafeTo(new Vector2d(46,-13)) // 1st initial
				.strafeToLinearHeading(new Vector2d(46,-58), Math.toRadians(-30)) // 1st in

				.strafeToLinearHeading(new Vector2d(46,-49), Math.toRadians(-90)) // back out
				.endTrajectory();

		TrajectoryActionBuilder pushSample2 = pushSample.fresh()
//				.turnTo(Math.toRadians(-90))
				.endTrajectory();


		TrajectoryActionBuilder forward = pushSample2.fresh()
				.strafeTo(new Vector2d(46,-57), new TranslationalVelConstraint(8)) // in to grab sample
				.endTrajectory(); // should be -54

		TrajectoryActionBuilder forward_2 = pushSample2.fresh()
				.strafeTo(new Vector2d(46,-57.5), new TranslationalVelConstraint(8)) // in to grab sample
				.endTrajectory(); // should be -54 (was -52.5)

		TrajectoryActionBuilder backAgain = forward.fresh()
				.strafeTo(new Vector2d(46,-52)) // in to grab sample
				.endTrajectory();

		TrajectoryActionBuilder scoreSpecimen2 = backAgain.fresh()
				.strafeToLinearHeading(new Vector2d(3,-40),Math.toRadians(90)) // ready to place specimen
				.strafeTo(new Vector2d(3,-32)) // should be -34 was -30

//				.strafeToLinearHeading(new Vector2d(6,-28),Math.toRadians(90)) // ready to place specimen

				.endTrajectory();

		TrajectoryActionBuilder specimenPlace2_2 = scoreSpecimen2.fresh()
				.strafeTo(new Vector2d(3, -38)) //  back up
				.endTrajectory();

		TrajectoryActionBuilder backUp = specimenPlace2_2.fresh()
				.strafeTo(new Vector2d(6, -42)) //  back up
				.endTrajectory();

		TrajectoryActionBuilder moveTo3rd = backUp.fresh()
				.strafeToLinearHeading(new Vector2d(46,-49), Math.toRadians(-90)) // was -50
				.endTrajectory(); // maybe needs to be changed back to -50 or less?


		TrajectoryActionBuilder scoreSpecimen3 = backAgain.fresh()
				.strafeToLinearHeading(new Vector2d(1,-40),Math.toRadians(90)) // read to place specimen
				.strafeTo(new Vector2d(1,-31.75)) // should be -34 was 27.75

//				.strafeToLinearHeading(new Vector2d(2,-25.75),Math.toRadians(90)) // read to place specimen
				.endTrajectory();


		TrajectoryActionBuilder specimenPlace2_3 = scoreSpecimen3.fresh()
				.strafeTo(new Vector2d(1, -38)) //  back up
				.endTrajectory();


		TrajectoryActionBuilder park = specimenPlace2_3.fresh()
				.strafeTo(new Vector2d(34, -60)) // should be up to -63? weird
				.endTrajectory();



		Actions.runBlocking(new SequentialAction(
				new InstantAction(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				specimenPlace_1.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					robot.wrist.bringdown();
				}),
				specimenPlace2_1.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					robot.wrist.high_rung();
				}),
				new InstantAction(() -> {
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
//				pushSample2.build(),
				new SleepAction(0.3),
				forward.build(),
				new SleepAction(0.2),
				new InstantAction(() -> {
					robot.claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
					robot.arm.armToTicks(300);
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
				specimenPlace2_2.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					robot.wrist.high_rung();
				}),
				new InstantAction(() -> {
					robot.claw.open();
				}),


				backUp.build(),
				new InstantAction(() -> {
					robot.wrist.wall();
					robot.arm.armToTicks(Arm.WALL_TICKS);
				}),
				moveTo3rd.build(),
//				new SleepAction(0.5),
				forward_2.build(),
				new SleepAction(0.2),
				new InstantAction(() -> {
					robot.claw.close();
				}),
				new SleepAction(0.3),
				new InstantAction(() -> {
					robot.arm.armToTicks(300);
				}),
				new SleepAction(0.3),
				backAgain.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				scoreSpecimen3.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					robot.wrist.bringdown();
				}),
				specimenPlace2_3.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					robot.wrist.high_rung();
				}),
				new InstantAction(() -> {
					robot.claw.open();
				}),
//				new SleepAction(0.2),
				park.build(),
				new InstantAction(() -> {
					robot.wrist.wall();
					robot.arm.armToTicks(Arm.WALL_TICKS+10 );
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