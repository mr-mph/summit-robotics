package org.firstinspires.ftc.teamcode.intothedeep.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.Drawing;

public class Robot {

	public Drive drive;

	public Robot(HardwareMap hardwareMap) {
		this.drive = new Drive(hardwareMap);
	}

	public void sendTelemetry(Telemetry telemetry) {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		if (drive.initialized) {
			telemetry.addData("speedState", drive.speedState);
			telemetry.addData("speed", Drive.SPEED);
			telemetry.addData("x", drive.mecanumDrive.pose.position.x);
			telemetry.addData("y", drive.mecanumDrive.pose.position.y);
			telemetry.addData("heading (deg)", Math.toDegrees(drive.mecanumDrive.pose.heading.toDouble()));


			TelemetryPacket packet = new TelemetryPacket();
			packet.fieldOverlay().setStroke("#3F51B5");
			Drawing.drawRobot(packet.fieldOverlay(), drive.mecanumDrive.pose);
			FtcDashboard.getInstance().sendTelemetryPacket(packet);
		}
		telemetry.update();

	}
}
