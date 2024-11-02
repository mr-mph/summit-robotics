//package org.firstinspires.ftc.teamcode.intothedeep.teleop;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.PoseVelocity2d;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
//import org.firstinspires.ftc.teamcode.intothedeep.robot.Drive;
//import org.firstinspires.ftc.teamcode.intothedeep.robot.Robot;
//import org.firstinspires.ftc.teamcode.intothedeep.robot.Wrist;
//import org.firstinspires.ftc.teamcode.intothedeep.robot.Claw;
//import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
//
//
//@TeleOp(name = "! Lock Test", group = "! Teleop")
//public class LockTest extends LinearOpMode {
//
//	Robot robot;
//
//
//	@Override
//	public void runOpMode() {
//
//		robot = new Robot(hardwareMap);
//
//		robot.drive.init(new Pose2d(6,-63, Math.toRadians(90)));
//		robot.claw.init();
//		robot.wrist.init(true);
//
//		sleep(1000);
//		robot.arm.init();
//		robot.wrist.wall();
//		robot.arm.armMotor.setPower(1);
//		robot.arm.armToTicks(Arm.WALL_TICKS);
//
//		MecanumDrive mecanumDrive = robot.drive.mecanumDrive
//
//		waitForStart();
//
//
//		while (!isStopRequested()) {
////			Pose2d targetPose = new Pose2d(6,-63, Math.toRadians(90));
////			Pose2d currentPos = mecanumDrive.getPoseEstimate();
////			Pose2d difference = targetPos.minus(currentPos);
////			Vector2d deltaVec = difference.vec().rotated(-currentPos.getHeading());
////
////			double deltaHeading = Angle.normDelta(targetPos.getHeading()) - Angle.normDelta(currentPos.getHeading());
////			mecanumDrive.setWeightedDrivePower(new Pose2d(deltaVec, deltaHeading));
////			mecanumDrive.update();
//			// can you rewrite for roadrunner 1.0?
//
//
//			robot.sendTelemetry(telemetry);
//
//		}
//	}
//
//
//}