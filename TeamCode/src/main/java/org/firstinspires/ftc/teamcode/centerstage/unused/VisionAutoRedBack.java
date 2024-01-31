//package org.firstinspires.ftc.teamcode.centerstage.auto;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
//import org.firstinspires.ftc.teamcode.centerstage.robot.Drive;
//import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
//import org.firstinspires.ftc.teamcode.centerstage.robot.Timings;
//import org.firstinspires.ftc.teamcode.centerstage.vision.RedPropThreshold;
//
//@Config
//@Autonomous(name = "!Vision Red Back Auto", group = "Test")
//public class VisionAutoRedBack extends LinearOpMode
//
//{
//	public static int BACKDROP_ALIGN_LEFT = 1500;
//	public static int BACKDROP_ALIGN_CENTER = 1100;
//	public static int BACKDROP_ALIGN_RIGHT = 950;
//
//	@Override
//	public void runOpMode()
//	{
//		Robot robot = new Robot(hardwareMap);
//		Drive drive = robot.drive;
//
//		RedPropThreshold redPropDetector = robot.camera.initRed();
//
//		robot.drive.init();
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
//
//		drive.driveStraight(1);
//		sleep(Timings.FIRST_FORWARD);
//
//		robot.arm.armToTicks(Arm.FLOOR_TICKS);
//
//		drive.driveStop();
//
//		String teamPropPosition = redPropDetector.getPropPosition();
//		sleep(1500);
//		telemetry.addData("Red Prop Position", teamPropPosition);
//		telemetry.update();
//
//		drive.driveTurn(-1);
//		sleep(Timings.TURN);
//
//		drive.driveStraight(-1);
//		sleep(Timings.BACKWARD_FROM_BACK);
//
//		drive.driveStrafe(1);
//		if (teamPropPosition.equals("LEFT")) {
//			sleep(BACKDROP_ALIGN_LEFT);
//		}  else if (teamPropPosition.equals("RIGHT")) {
//			sleep(BACKDROP_ALIGN_RIGHT);
//		} else {
//			sleep(BACKDROP_ALIGN_CENTER);
//		}
//
//		drive.driveStop();
//		robot.raiseArm();
//		sleep(1000);
//
//		drive.driveStraight(-1);
//		sleep(Timings.BACKDROP_BACKWARD);
//		drive.driveStop();
//
//		sleep(1000);
//		robot.openClaw();
//		sleep(1000);
//
//		drive.driveStraight(1);
//		sleep(Timings.BACKDROP_FORWARD);
//
//		robot.closeClaw();
//		robot.lowerArm();
//
//		drive.driveStrafe(-1);
//		sleep(Timings.PARK_CORNER);
//
//		drive.driveStraight(-1);
//		sleep(Timings.PARK_BACKWARD);
//
//		drive.driveStop();
//	}
//}