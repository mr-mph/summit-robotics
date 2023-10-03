package org.firstinspires.ftc.teamcode.centerstage.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Config
public class Robot {

	public Slide slide;
	public Arm arm;

	public Drive drive;

	public Claw claw;

	public ColorSensor colorsensor;

	public Robot(HardwareMap hardwareMap) {
		this.slide = new Slide(hardwareMap);
		this.arm = new Arm(hardwareMap);
		this.drive = new Drive(hardwareMap);
		this.claw = new Claw(hardwareMap);
	}

	public void sendTelemetry(Telemetry telemetry) {
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
		telemetry.addData("armpos", arm.armMotor.getCurrentPosition());
		telemetry.addData("speedState", drive.speedState);
		telemetry.update();
	}
}
