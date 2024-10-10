package org.firstinspires.ftc.teamcode.intothedeep.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.ArrayList;

@TeleOp(name = "Sample Detection Demo Teleop")
public class SampleDetection extends LinearOpMode {
	OpenCvCamera webcam;
	SampleDetectionPipelinePNP pipeline;

	@Override
	public void runOpMode() {
		// Get the camera monitor view ID
		int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
				"cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

		webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);

		pipeline = new SampleDetectionPipelinePNP();
		webcam.setPipeline(pipeline);

		webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
			@Override
			public void onOpened() {
				webcam.startStreaming(640, 480); // Set your preferred resolution
			}

			@Override
			public void onError(int errorCode) {
				telemetry.addData("Error", "Camera failed to open");
				telemetry.update();
			}
		});

		telemetry.addData("Status", "Initialized");
		telemetry.update();

		waitForStart();

		while (opModeIsActive()) {
			ArrayList<SampleDetectionPipelinePNP.AnalyzedStone> objectCount = pipeline.getDetectedStones();
			telemetry.addData("Objects Detected", objectCount);

			telemetry.update();
		}
		webcam.stopStreaming();
	}
}