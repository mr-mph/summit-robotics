//package org.firstinspires.ftc.teamcode.centerstage.auto;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
//import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
//import org.firstinspires.ftc.teamcode.centerstage.robot.Wrist;
//import org.firstinspires.ftc.teamcode.centerstage.vision.BluePropThreshold;
//import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
//
//@Config
//@Autonomous(name = "!!Blue Back Auto (new)", group = "Auto")
//public class VisionAutoBlueBack2 extends LinearOpMode
//
//{
//
//	@Override
//	public void runOpMode()
//	{
//		Robot robot = new Robot(hardwareMap);
//
//		BluePropThreshold bluePropDetector = robot.camera.initBlue();
//
//		robot.drive.init();
//		SampleMecanumDrive drive = robot.drive.mecanumDrive;
//		robot.drone.init();
//		robot.claw.init();
//
//		sleep(1000);
//		robot.arm.init();
//		robot.wrist.init(true);
//		sleep(2000);
//
//		robot.arm.armToTicks(Arm.FLOOR_TICKS);
//
//		waitForStart();
//		robot.arm.armToTicks(Arm.BASE_TICKS);
//		sleep(500);
//		robot.wrist.lower();
//
//
//		String teamPropPosition = bluePropDetector.getPropPosition();
//		sleep(1500);
//		telemetry.addData("Blue Prop Position", teamPropPosition);
//		telemetry.update();
//
//		Pose2d startPose = new Pose2d(-36.23,64, Math.toRadians(-90));
//		drive.setPoseEstimate(startPose);
//
//		TrajectorySequence rightSpike = drive.trajectorySequenceBuilder(startPose)
//				.lineTo(new Vector2d(-45,46.1))
//				.waitSeconds(0.5)
//				.addTemporalMarker(() -> {
//					robot.claw.open("right");
//				})
//				.waitSeconds(0.5)
//				.lineTo(new Vector2d(-40 ,60))
//				.turn(Math.toRadians(90))
//				.lineTo(new Vector2d(12,60))
//				.addTemporalMarker(() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.PRECISE_BACKDROP_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_PRECISE_BACKDROP);
//					robot.claw.close("right");
//				})
//				.lineTo(new Vector2d(49.5,25))
//				.waitSeconds(0.5)
//				.addTemporalMarker(() -> {
//					robot.claw.preciseOpenLeft();
//				})
//				.waitSeconds(0.5)
//				.back(10)
//				.addTemporalMarker(() -> {
//					robot.claw.close("left");
//				})
//				.lineTo(new Vector2d(49.5,12.24))
//				.turn(Math.toRadians(-180))
//				.addTemporalMarker(() -> {
//					robot.claw.close("left");
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.BASE_TICKS);
//					robot.wrist.lower();
//				})
//				.lineTo(new Vector2d(64.12,13))
//				.addTemporalMarker(() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.PICKUP_TICKS);
//				})
//				.build();
//
//		TrajectorySequence centerSpike = drive.trajectorySequenceBuilder(startPose)
//				.lineTo(new Vector2d(-32.67,38.5))
//				.waitSeconds(0.5)
//				.addTemporalMarker(() -> {
//					robot.claw.open("right");
//				})
//				.waitSeconds(0.5)
//				.lineTo(new Vector2d(-43.5,38))
//				.turn(Math.toRadians(90))
//				.UNSTABLE_addTemporalMarkerOffset(2,() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.PRECISE_BACKDROP_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_PRECISE_BACKDROP);
//					robot.claw.close("right");
//				})
//				.lineTo(new Vector2d(51.7,34.5))
//				.addTemporalMarker(() -> {
//					robot.claw.preciseOpenLeft();
//				})
//				.waitSeconds(0.5)
//				.back(10)
//				.addTemporalMarker(() -> {
//					robot.claw.close("left");
//				})
//				.lineTo(new Vector2d(48,12.24))
//				.turn(Math.toRadians(-180))
//				.addTemporalMarker(() -> {
//					robot.claw.close("left");
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.BASE_TICKS);
//					robot.wrist.lower();
//				})
//				.lineTo(new Vector2d(64.12,12.29))
//				.addTemporalMarker(() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.PICKUP_TICKS);
//				})
//				.build();
//
//		TrajectorySequence leftSpike = drive.trajectorySequenceBuilder(startPose)
//				.lineTo(new Vector2d(-39,36.96))
//				.turn(Math.toRadians(90))
//				.waitSeconds(0.5)
//				.addTemporalMarker(() -> {
//					robot.claw.open("right");
//				})
//				.waitSeconds(0.5)
//				.lineTo(new Vector2d(-43.5,36.3))
//				.lineTo(new Vector2d(-45,60))
//				.lineTo(new Vector2d(12,60))
//				.addTemporalMarker(() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.PRECISE_BACKDROP_TICKS);
//					robot.wrist.wrist.setPower(Wrist.WRIST_PRECISE_BACKDROP);
//					robot.claw.close("right");
//				})
//				.lineTo(new Vector2d(51.7,42))
//				.addTemporalMarker(() -> {
//					robot.claw.preciseOpenLeft();
//				})
//				.waitSeconds(0.5)
//				.back(10)
//				.addTemporalMarker(() -> {
//					robot.claw.close("left");
//				})
//				.lineTo(new Vector2d(48,12.24))
//				.turn(Math.toRadians(180))
//				.addTemporalMarker(() -> {
//					robot.claw.close("left");
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.BASE_TICKS);
//					robot.wrist.lower();
//				})
//				.lineTo(new Vector2d(64.12,12.29))
//				.addTemporalMarker(() -> {
//					robot.arm.armMotor.setPower(1);
//					robot.arm.armToTicks(Arm.PICKUP_TICKS);
//				})
//				.build();
//
//		if (teamPropPosition.equals("RIGHT")) {
//			drive.followTrajectorySequence(rightSpike);
//		}  else if (teamPropPosition.equals("LEFT")) {
//			drive.followTrajectorySequence(leftSpike);
//		} else {
//			drive.followTrajectorySequence(centerSpike);
//		}
//		sleep(2000);
//	}
//}