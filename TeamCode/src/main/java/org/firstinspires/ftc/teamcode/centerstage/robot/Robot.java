package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Robot {

	public Slide slide;
	public Arm arm;
	public Drive drive;
	public Claw claw;
	public Camera camera;


	public ColorSensor colorsensor;

	public Robot(HardwareMap hardwareMap) {
		this.slide = new Slide(hardwareMap);
		this.arm = new Arm(hardwareMap);
		this.drive = new Drive(hardwareMap);
		this.claw = new Claw(hardwareMap);
		this.camera = new Camera(hardwareMap);
	}

	public void sendTelemetry(Telemetry telemetry) {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//		telemetry.addData("armpos", arm.armMotor.getCurrentPosition());
		telemetry.addData("speedState", drive.speedState);
		telemetry.update();
	}
}
