package org.firstinspires.ftc.teamcode.centerstage.robot;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.centerstage.vision.BluePropThreshold;
import org.firstinspires.ftc.teamcode.centerstage.vision.RedPropThreshold;
import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

public class Camera {
	HardwareMap hardwareMap;



	private VisionPortal portal;
	public boolean initialized = false;

	public Camera(HardwareMap hardwareMap) {
		this.hardwareMap = hardwareMap;
	}

	public RedPropThreshold initRed() {

		RedPropThreshold redPropThreshold = new RedPropThreshold();
		portal = new VisionPortal.Builder()
				.setCamera(hardwareMap.get(WebcamName.class, "webcam"))
				.setCameraResolution(new Size(640, 480))
				.setCamera(BuiltinCameraDirection.BACK)
				.addProcessor(redPropThreshold)
				.build();

		portal.saveNextFrameRaw("CameraFrameCapture-Red.jpg");
		initialized = true;
		return redPropThreshold;
	}

	public BluePropThreshold initBlue() {
		BluePropThreshold bluePropThreshold = new BluePropThreshold();
		portal = new VisionPortal.Builder()
				.setCamera(hardwareMap.get(WebcamName.class, "webcam"))
				.setCameraResolution(new Size(640, 480))
				.setCamera(BuiltinCameraDirection.BACK)
				.addProcessor(bluePropThreshold)
				.build();

		portal.saveNextFrameRaw("CameraFrameCapture-Blue.jpg");
		initialized = true;
		return bluePropThreshold;
	}


}
