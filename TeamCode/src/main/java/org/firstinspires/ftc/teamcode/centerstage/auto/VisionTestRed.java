package org.firstinspires.ftc.teamcode.centerstage.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.vision.RedPropThreshold;
import org.firstinspires.ftc.vision.VisionPortal;


@Autonomous(name="!Vision Test (Red)", group="Test")
public class VisionTestRed extends LinearOpMode {

	private VisionPortal portal;

	@Override
	public void runOpMode() {
		Robot robot = new Robot(hardwareMap);
		RedPropThreshold redPropDetector = robot.camera.initRed();

		sleep(1000);
		waitForStart();
		telemetry.addData("Red Prop Position", redPropDetector.getPropPosition());
		telemetry.addData("Red Prop Center", redPropDetector.getCenterValue());
		telemetry.addData("Red Prop Right", redPropDetector.getRightValue());
		telemetry.update();
		sleep(1000);

	}
}