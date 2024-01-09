package org.firstinspires.ftc.teamcode.centerstage.unused;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.vision.AprilTagDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayList;

@Disabled
@Autonomous(name = "!April Tag Detection", group = "Auto")
public class AprilTagDetection extends LinearOpMode
{
	OpenCvCamera camera;
	AprilTagDetectionPipeline aprilTagDetectionPipeline;

	static final double FEET_PER_METER = 3.28084;

	int numFramesWithoutDetection = 0;

	final float DECIMATION_HIGH = 3;
	final float DECIMATION_LOW = 2;
	final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
	final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

	@Override
	public void runOpMode()
	{

		Robot robot = new Robot(hardwareMap);
		OldCamera oldCamera = new OldCamera(hardwareMap);

		oldCamera.init();
		robot.arm.init();
		robot.claw.init();
		robot.drive.init();
		telemetry.setMsTransmissionInterval(50);
		waitForStart();


		while (opModeIsActive())
		{

			// Calling getDetectionsUpdate() will only return an object if there was a new frame
			// processed since the last time we called it. Otherwise, it will return null. This
			// enables us to only run logic when there has been a new frame, as opposed to the
			// getLatestDetections() method which will always return an object.
			ArrayList<org.openftc.apriltag.AprilTagDetection> detections = aprilTagDetectionPipeline.getDetectionsUpdate();

			// If there's been a new frame...
			if(detections != null)
			{
				telemetry.addData("FPS", camera.getFps());
				telemetry.addData("Overhead ms", camera.getOverheadTimeMs());
				telemetry.addData("Pipeline ms", camera.getPipelineTimeMs());

				// If we don't see any tags
				if(detections.size() == 0)
				{
					numFramesWithoutDetection++;

					// If we haven't seen a tag for a few frames, lower the decimation
					// so we can hopefully pick one up if we're e.g. far back
					if(numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION)
					{
						aprilTagDetectionPipeline.setDecimation(DECIMATION_LOW);
					}
				}
				// We do see tags!
				else
				{
					numFramesWithoutDetection = 0;

					// If the target is within 1 meter, turn on high decimation to
					// increase the frame rate
					if(detections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS)
					{
						aprilTagDetectionPipeline.setDecimation(DECIMATION_HIGH);
					}

					for(org.openftc.apriltag.AprilTagDetection detection : detections)
					{
						Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

						telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
						telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
						telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
						telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
						telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", rot.firstAngle));
						telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", rot.secondAngle));
						telemetry.addLine(String.format("Rotation Roll: %.2f degrees", rot.thirdAngle));
					}
				}

				telemetry.update();
			}

			sleep(20);
		}
	}
}