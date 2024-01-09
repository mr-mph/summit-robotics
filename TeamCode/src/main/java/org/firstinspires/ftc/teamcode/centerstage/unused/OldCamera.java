package org.firstinspires.ftc.teamcode.centerstage.unused;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.centerstage.vision.AprilTagDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

public class OldCamera {
	HardwareMap hardwareMap;
	OpenCvPipeline aprilTagDetectionPipeline;

	OpenCvWebcam webcam;

	// UNITS ARE PIXELS
	double fx = 578.272;
	double fy = 578.272;
	double cx = 402.145;
	double cy = 221.506;

	// UNITS ARE METERS
	double tagsize = 0.166;

	public boolean initialized = false;

	public OldCamera(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public void init() {
		int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
		webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "webcam"), cameraMonitorViewId);
		FtcDashboard.getInstance().startCameraStream(webcam, 0);

		aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
		webcam.setPipeline(aprilTagDetectionPipeline);

		webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
		{
			@Override
			public void onOpened()
			{
				webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
			}

			@Override
			public void onError(int errorCode)
			{

			}
		});
		initialized = true;

	}

}
