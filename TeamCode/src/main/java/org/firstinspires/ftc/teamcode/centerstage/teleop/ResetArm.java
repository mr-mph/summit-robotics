//package org.firstinspires.ftc.teamcode.centerstage.teleop;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.centerstage.robot.Arm;
//import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
//import org.firstinspires.ftc.teamcode.centerstage.vision.BluePropThreshold;
//import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.vision.VisionPortal;
//
//
//@TeleOp(name="Reset Arm", group="Test")
//public class ResetArm extends LinearOpMode {
//
//	@Override
//	public void runOpMode() {
//		Robot robot = new Robot(hardwareMap);
//
//		robot.drive.init();
//		robot.drone.init();
//		robot.claw.init();
//		robot.wrist.init(true);
//		robot.arm.init();
//
//		waitForStart();
//		sleep(1000);
//		robot.wrist.init(false);
//		robot.arm.armToTicks(Arm.FLOOR_TICKS);
//		robot.drive.driveStraight(-0.3);
//		sleep(1000);
//		robot.drive.driveStop();
//	}
//}