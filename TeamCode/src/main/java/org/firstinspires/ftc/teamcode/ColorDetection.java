package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled
@Autonomous(name = "!Color Detector")
public class ColorDetection extends LinearOpMode {
	private final FtcDashboard dashboard = FtcDashboard.getInstance();

	@Override
	public void runOpMode() {

		Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());
		Robot robot = new Robot(hardwareMap);
		robot.initializeDrivetrain();


		waitForStart();

		while (!isStopRequested()) {
			int color = ((NormalizedColorSensor) robot.colorsensor).getNormalizedColors().toColor();
			float hue = JavaUtil.colorToHue(color);
			float sat = JavaUtil.colorToSaturation(color);
			float val = JavaUtil.colorToValue(color);

			if (hue < 30) { // technically red
				telemetry.addData("Color", "Red");

			} else if (hue < 180 && hue > 100) { // technically green
				telemetry.addData("Color", "Green");

			} else if (hue < 225) { // technically blue
				telemetry.addData("Color", "Blue");
			}
			telemetry.addData("Hue", hue);
			telemetry.addData("Value", val);
			telemetry.addData("Saturation", sat);
			telemetry.update();
		}
	}
}
