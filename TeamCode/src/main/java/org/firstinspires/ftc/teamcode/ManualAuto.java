package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "!Manual Auto (Powerplay)")
public class ManualAuto extends LinearOpMode {
	private final FtcDashboard dashboard = FtcDashboard.getInstance();

	@Override
	public void runOpMode() {

		Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());
		Robot robot = new Robot(hardwareMap);
		robot.initializeDrivetrain();


		waitForStart();
		robot.driveStraight(-0.6);
		sleep(1000);
		ElapsedTime timer = new ElapsedTime();

		while (!isStopRequested()) {
			int color = ((NormalizedColorSensor) robot.colorsensor).getNormalizedColors().toColor();
			float hue = JavaUtil.colorToHue(color);
			float val = JavaUtil.colorToValue(color);

			if (val > 0.05){
				sleep(500);
				if (hue < 30) {
					telemetry.addData("Color", "Red");
					robot.driveStrafe(0.6); // location 1 left
					sleep(3000);
				} else if (hue < 180 && hue > 100) {
					telemetry.addData("Color", "Green");
					 // location 2 stop
				} else if (hue < 225) {
					telemetry.addData("Color", "Blue");
					robot.driveStrafe(-0.6); // location 3 right
					sleep(3000);

				}
				requestOpModeStop();
			} else if (timer.seconds() > 5) {
				requestOpModeStop();
			}
			telemetry.update();
		}
	}
}
