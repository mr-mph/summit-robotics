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
@Autonomous(name = "!! Far Auto", group = "Auto")
public class FarAuto extends LinearOpMode

{

	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);

		Pose2d startPose = new Pose2d(-6,-63, Math.toRadians(90));

		robot.drive.init(startPose);
		MecanumDrive drive = robot.drive.mecanumDrive;

		robot.claw.init();

		robot.arm.init();
		robot.wrist.init(true);


		waitForStart();

		TrajectoryActionBuilder specimenPlace = drive.actionBuilder(startPose)
				.strafeTo(new Vector2d(-6, -30));

		TrajectoryActionBuilder backOut = drive.actionBuilder(new Pose2d(new Vector2d(-6, -30), Math.toRadians(90)))
				.strafeTo(new Vector2d(-6,-45));

		TrajectoryActionBuilder auto = drive.actionBuilder(new Pose2d(new Vector2d(-6,-45), Math.toRadians(90)))
				.strafeTo(new Vector2d(36,-39))
				.turn(Math.toRadians(-90))
				.strafeTo(new Vector2d(34,-9))

				.strafeTo(new Vector2d(42,-12))
				.strafeTo(new Vector2d(51-3,-13))
				.strafeTo(new Vector2d(51-3,-62))
				.strafeTo(new Vector2d(51-3,-13))
				.strafeTo(new Vector2d(62-5,-13))
				.strafeTo(new Vector2d(62-5,-62))
				.strafeTo(new Vector2d(68-2,-13))
				.strafeTo(new Vector2d(68-2,-13))
				.strafeTo(new Vector2d(68-2,-62))
				.strafeTo(new Vector2d(42,-51))
				.turn(Math.toRadians(90));


		TrajectoryActionBuilder park = drive.actionBuilder(new Pose2d(new Vector2d(42,-51), Math.toRadians(90)))
				.strafeTo(new Vector2d(34, -63));


		Actions.runBlocking(new SequentialAction(
				new InstantAction(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
				}),
				new SleepAction(0.5),
				specimenPlace.build(),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_TICKS-50);
				}),
				new SleepAction(0.2),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_BRINGDOWN);
				}),
				new SleepAction(0.2),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.HIGH_RUNG_BRINGUP_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_HIGH_RUNG);
					robot.claw.open();
				}), new SleepAction(0.5),
				backOut.build(), new SleepAction(0.5),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.BASKET_TICKS);
				}), new SleepAction(0.5),
				auto.build(),
				new SleepAction(0.5),
				new InstantAction(() -> {
					robot.arm.armToTicks(Arm.WALL_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_WALL);
				}), new SleepAction(0.5),
				park.build()
		));

//		sleep(2000);

	}


}