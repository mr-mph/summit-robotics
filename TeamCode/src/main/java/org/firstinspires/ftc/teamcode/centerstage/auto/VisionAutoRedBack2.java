package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.robot.Wrist;
import org.firstinspires.ftc.teamcode.centerstage.vision.RedPropThreshold;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name = "!!Red Back Auto (new)", group = "Auto")
public class VisionAutoRedBack2 extends LinearOpMode

{

	@Override
	public void runOpMode()
	{
		Robot robot = new Robot(hardwareMap);

		RedPropThreshold redPropDetector = robot.camera.initRed();

		robot.drive.init();
		SampleMecanumDrive drive = robot.drive.mecanumDrive;
		robot.drone.init();
		robot.claw.init();

		sleep(1000);
		robot.arm.init();
		robot.wrist.init(true);
		sleep(2000);

		robot.arm.armToTicks(Arm.FLOOR_TICKS);

		waitForStart();
		robot.arm.armToTicks(Arm.BASE_TICKS);
		robot.wrist.lower();

		String teamPropPosition = redPropDetector.getPropPosition();
		sleep(1500);
		telemetry.addData("Red Prop Position", teamPropPosition);
		telemetry.update();

		Pose2d startPose = new Pose2d(-36.23,-64.6, Math.toRadians(90));
		drive.setPoseEstimate(startPose);

		TrajectorySequence rightSpike = drive.trajectorySequenceBuilder(startPose)
				.lineTo(new Vector2d(-28.67,-38))
				.turn(Math.toRadians(-90))
				.addTemporalMarker(() -> {
					robot.claw.open("left");
				})
				.waitSeconds(0.5)
				.addTemporalMarker(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.PRECISE_BACKDROP_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_PRECISE_BACKDROP);
					robot.claw.close("left");
				})
				.lineTo(new Vector2d(-36,-60))
				.lineTo(new Vector2d(12,-60))
				.lineTo(new Vector2d(51.7,-33))
				.addTemporalMarker(() -> {
					robot.claw.preciseOpenRight();
				})
				.waitSeconds(0.5)
				.back(6)
				.lineTo(new Vector2d(48,-12.24))
				.turn(Math.toRadians(-180))
				.addTemporalMarker(() -> {
					robot.claw.close("right");
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.BASE_TICKS);
					robot.wrist.lower();
				})
				.lineTo(new Vector2d(64.12,-12.29))
				.addTemporalMarker(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.PICKUP_TICKS);
				})
				.build();

		TrajectorySequence centerSpike = drive.trajectorySequenceBuilder(startPose)
				.lineTo(new Vector2d(19.67,-36.3))
				.addTemporalMarker(() -> {
					robot.claw.open("left");
				})
				.waitSeconds(0.5)
				.addTemporalMarker(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.PRECISE_BACKDROP_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_PRECISE_BACKDROP);
					robot.claw.close("left");
				})
				.turn(Math.toRadians(-90))
				.lineTo(new Vector2d(51.7,-33))
				.addTemporalMarker(() -> {
					robot.claw.preciseOpenRight();
				})
				.waitSeconds(0.5)
				.back(6)
				.lineTo(new Vector2d(48,-12.24))
				.turn(Math.toRadians(-180))
				.addTemporalMarker(() -> {
					robot.claw.close("right");
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.BASE_TICKS);
					robot.wrist.lower();
				})
				.lineTo(new Vector2d(64.12,-12.29))
				.addTemporalMarker(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.PICKUP_TICKS);
				})
				.build();

		TrajectorySequence leftSpike = drive.trajectorySequenceBuilder(startPose)
				.lineTo(new Vector2d(-44.5,-46.1))
				.addTemporalMarker(() -> {
					robot.claw.open("left");
				})
				.waitSeconds(0.5)
				.addTemporalMarker(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.PRECISE_BACKDROP_TICKS);
					robot.wrist.wrist.setPower(Wrist.WRIST_PRECISE_BACKDROP);
					robot.claw.close("left");
				})
				.lineTo(new Vector2d(-36,-60))
				.turn(Math.toRadians(-90))
				.lineTo(new Vector2d(12,-60))
				.lineTo(new Vector2d(51.7,-26.5))
				.addTemporalMarker(() -> {
					robot.claw.preciseOpenRight();
				})
				.waitSeconds(0.5)
				.back(6)
				.lineTo(new Vector2d(48,-12.24))
				.turn(Math.toRadians(180))
				.addTemporalMarker(() -> {
					robot.claw.close("right");
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.BASE_TICKS);
					robot.wrist.lower();
				})
				.lineTo(new Vector2d(64.12,-12.29))
				.addTemporalMarker(() -> {
					robot.arm.armMotor.setPower(1);
					robot.arm.armToTicks(Arm.PICKUP_TICKS);
				})
				.build();

		if (teamPropPosition.equals("LEFT")) {
			drive.followTrajectorySequence(leftSpike);
		}  else if (teamPropPosition.equals("RIGHT")) {
			drive.followTrajectorySequence(rightSpike);
		} else {
			drive.followTrajectorySequence(centerSpike);
		}
	}
}