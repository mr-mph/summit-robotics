//package org.firstinspires.ftc.teamcode.centerstage.auto;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
//import org.firstinspires.ftc.teamcode.centerstage.vision.BluePropThreshold;
//import org.firstinspires.ftc.teamcode.centerstage.vision.RedPropThreshold;
//import org.firstinspires.ftc.vision.VisionPortal;
//
//
//@Autonomous(name="!Vision Test (Blue)", group="Test")
//public class VisionTestBlue extends LinearOpMode {
//
//	private VisionPortal portal;
//
//	@Override
//	public void runOpMode() {
//		Robot robot = new Robot(hardwareMap);
//		BluePropThreshold bluePropDetector = robot.camera.initBlue();
//
//		sleep(1000);
//		waitForStart();
//		telemetry.addData("Blue Prop Position", bluePropDetector.getPropPosition());
//		telemetry.addData("Blue Prop Center", bluePropDetector.getCenterValue());
//		telemetry.addData("Blue Prop Right", bluePropDetector.getRightValue());
//		telemetry.update();
//		sleep(1000);
//
//	}
//}