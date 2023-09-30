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

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Disabled
@Autonomous(name = "!Roadrunner Auto (Broken)")
public class RoadrunnerAuto extends LinearOpMode {
	private final FtcDashboard dashboard = FtcDashboard.getInstance();
	@Override
	public void runOpMode() {


		Telemetry telemetry = new MultipleTelemetry(this.telemetry, dashboard.getTelemetry());
		Robot robot = new Robot(hardwareMap);
		robot.initializeDrivetrain();

		Pose2d startPos = new Pose2d(-36,-60, Math.toRadians(-90));

		Trajectory toColorsensor = robot.drive.trajectoryBuilder(startPos)
				.lineTo(new Vector2d(-36,-36))
				.build();

		Trajectory location1 = robot.drive.trajectoryBuilder(toColorsensor.end())
				.lineTo(new Vector2d(-60,-36))
				.build();

		Trajectory location3 = robot.drive.trajectoryBuilder(toColorsensor.end())
				.lineTo(new Vector2d(-12,-36))
				.build();

		waitForStart();
		robot.drive.followTrajectory(toColorsensor);

		while (!isStopRequested()) {
			int color = ((NormalizedColorSensor) robot.colorsensor).getNormalizedColors().toColor();
			float hue = JavaUtil.colorToHue(color);
			if (hue < 30) {
				telemetry.addData("Color", "Red"); // location 1 left
				robot.drive.followTrajectory(location1);
				requestOpModeStop();
			} else if (hue < 150) {
				telemetry.addData("Color", "Green"); // location 2 stop
				requestOpModeStop();
			} else if (hue < 225) {
				telemetry.addData("Color", "Blue"); // location 3 right
				robot.drive.followTrajectory(location3);
				requestOpModeStop();
			}
			telemetry.update();
		}
	}
}
