package org.firstinspires.ftc.teamcode.intothedeep.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.intothedeep.robot.Arm;
import org.firstinspires.ftc.teamcode.roadrunner.Drawing;

public class Robot {

	public Arm arm;
	public Wrist wrist;
	public Drive drive;
	public Claw claw;


	public Robot(HardwareMap hardwareMap) {
		this.arm = new Arm(hardwareMap);
		this.drive = new Drive(hardwareMap);
		this.claw = new Claw(hardwareMap);
		this.wrist = new Wrist(hardwareMap);
	}

	public void sendTelemetry(Telemetry telemetry) {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		if (drive.initialized) {
			telemetry.addData("speedState", drive.speedState);
			telemetry.addData("speed", Drive.SPEED);
			telemetry.addData("x", drive.mecanumDrive.pose.position.x);
			telemetry.addData("y", drive.mecanumDrive.pose.position.y);
			telemetry.addData("heading (deg)", Math.toDegrees(drive.mecanumDrive.pose.heading.toDouble()));
			telemetry.addData("wrist adjustment", wrist.wristAdjustment);


			TelemetryPacket packet = new TelemetryPacket();
			packet.fieldOverlay().setStroke("#3F51B5");
			Drawing.drawRobot(packet.fieldOverlay(), drive.mecanumDrive.pose);
			FtcDashboard.getInstance().sendTelemetryPacket(packet);
		}

		telemetry.update();

	}



	public class beginAuto implements Action {
		@Override
		public boolean run(@NonNull TelemetryPacket packet) {
			arm.armMotor.setPower(1);
			arm.armToTicks(Arm.HIGH_RUNG_TICKS);
			wrist.high_rung();
			return true;
		}
	}

	public class bringDown implements Action {
		@Override
		public boolean run(@NonNull TelemetryPacket packet) {
			arm.armToTicks(Arm.HIGH_RUNG_BRINGDOWN_TICKS);
			wrist.bringdown();
			return true;
		}
	}




	public Action bringDown() {
		return new bringDown();
	}

	public Action beginAuto() {
		return new beginAuto();
	}
}
