package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Robot {

	public Arm arm;
	public Drive drive;
	public Claw claw;
	public Camera camera;
	public Drone drone;

	public Robot(HardwareMap hardwareMap) {
		this.arm = new Arm(hardwareMap);
		this.drive = new Drive(hardwareMap);
		this.claw = new Claw(hardwareMap);
		this.camera = new Camera(hardwareMap);
		this.drone = new Drone(hardwareMap);
	}

	public void sendTelemetry(Telemetry telemetry) {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		if (drive.initialized) telemetry.addData("speedState", drive.speedState);
		if (drive.initialized) telemetry.addData("speed", Drive.SPEED);
		if (claw.initialized) telemetry.addData("topClawClosed", claw.topClawClosed);
//		telemetry.addData("bottomClawClosed", claw.bottomClawClosed);
		if (arm.initialized) telemetry.addData("armPos", arm.armMotor.getCurrentPosition());
		if (drone.initialized) telemetry.addData("droneReleased?", drone.droneReleased);

		telemetry.update();
	}
}
