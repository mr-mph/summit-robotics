package org.firstinspires.ftc.teamcode.centerstage.vision;

import android.graphics.Canvas;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Config
public class RedPropThreshold implements VisionProcessor {
	public static double RED_THRESHOLD = 0.04;

	public static int CENTER_SPIKE_X1 = 0;
	public static int CENTER_SPIKE_Y1 = 0;

	public static int CENTER_SPIKE_X2 = 320;
	public static int CENTER_SPIKE_Y2 = 480;


	public static int RIGHT_SPIKE_X1 = 320;
	public static int RIGHT_SPIKE_Y1 = 0;

	public static int RIGHT_SPIKE_X2 = 640;
	public static int RIGHT_SPIKE_Y2 = 480;


	static final Scalar GREEN = new Scalar(0, 255, 0);

	double averagedCenterBox;
	double averagedRightBox;

	Mat testMat = new Mat();
	Mat highMat = new Mat();
	Mat lowMat = new Mat();
	Mat redMat = new Mat();

	String outStr;

	static final Rect CENTER_RECTANGLE = new Rect( // out of 640x480
			new Point(CENTER_SPIKE_X1, CENTER_SPIKE_Y1),
			new Point(CENTER_SPIKE_X2, CENTER_SPIKE_Y2)
	);

	static final Rect RIGHT_RECTANGLE = new Rect(
			new Point(RIGHT_SPIKE_X1, RIGHT_SPIKE_Y1),
			new Point(RIGHT_SPIKE_X2, RIGHT_SPIKE_Y2)
	);

	@Override
	public void init(int width, int height, CameraCalibration calibration) {

	}

	@Override
	public Object processFrame(Mat frame, long captureTimeNanos) {
		Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);
		Imgproc.blur(testMat, testMat, new org.opencv.core.Size(5, 5));


		Scalar lowHSVRedLower = new Scalar(0, 100, 20);  //Beginning of Color Wheel
		Scalar lowHSVRedUpper = new Scalar(10, 255, 255);

		Scalar redHSVRedLower = new Scalar(160, 100, 20); //Wraps around Color Wheel
		Scalar highHSVRedUpper = new Scalar(180, 255, 255);

		Core.inRange(testMat, lowHSVRedLower, lowHSVRedUpper, lowMat);
		Core.inRange(testMat, redHSVRedLower, highHSVRedUpper, highMat);

		testMat.release();

		Core.bitwise_or(lowMat, highMat, redMat);

		lowMat.release();
		highMat.release();

		double centerBox = Core.sumElems(redMat.submat(CENTER_RECTANGLE)).val[0];
		double rightBox = Core.sumElems(redMat.submat(RIGHT_RECTANGLE)).val[0];

		averagedCenterBox = centerBox / CENTER_RECTANGLE.area() / 255;
		averagedRightBox = rightBox / RIGHT_RECTANGLE.area() / 255; //Makes value [0,1]




		if (averagedCenterBox <  RED_THRESHOLD && averagedRightBox < RED_THRESHOLD) {
			outStr = "LEFT";
		} else if (averagedRightBox > averagedCenterBox){
			outStr = "RIGHT";
		} else {
			outStr = "CENTER";
		}


		redMat.copyTo(frame); /*This line should only be added in when you want to see your custom pipeline
                                  on the driver station stream, do not use this permanently in your code as
                                  you use the "frame" mat for all of your pipelines, such as April Tags*/

		Imgproc.rectangle(
				redMat, // Buffer to draw on
				new Point(CENTER_RECTANGLE.x, CENTER_RECTANGLE.y), // First point which defines the rectangle
				new Point(CENTER_RECTANGLE.x + CENTER_RECTANGLE.width, CENTER_RECTANGLE.y + CENTER_RECTANGLE.height), // Second point which defines the rectangle
				GREEN, // The color the rectangle is drawn in
				2); // Thickness of the rectangle lines

		Imgproc.rectangle(
				redMat, // Buffer to draw on
				new Point(RIGHT_RECTANGLE.x, RIGHT_RECTANGLE.y), // First point which defines the rectangle
				new Point(RIGHT_RECTANGLE.x + RIGHT_RECTANGLE.width, RIGHT_RECTANGLE.y + RIGHT_RECTANGLE.height), // Second point which defines the rectangle
				GREEN, // The color the rectangle is drawn in
				2); // Thickness of the rectangle lines

		redMat.copyTo(frame); /*This line should only be added in when you want to see your custom pipeline
                                  on the driver station stream, do not use this permanently in your code as
                                  you use the "frame" mat for all of your pipelines, such as April Tags*/

		return null;            //You do not return the original mat anymore, instead return null
	}


	@Override
	public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

	}

	public String getPropPosition(){  //Returns postion of the prop in a String
		return outStr;
	}

	public double getCenterValue(){
		return averagedCenterBox;
	}

	public double getRightValue(){
		return averagedRightBox;
	}
}