//package org.firstinspires.ftc.teamcode.centerstage.robot;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//public class Robot {
//
//	public Arm arm;
//	public Wrist wrist;
//	public Drive drive;
//	public Claw claw;
//	public Camera camera;
//	public Drone drone;
//
//	public Robot(HardwareMap hardwareMap) {
//		this.arm = new Arm(hardwareMap);
//		this.drive = new Drive(hardwareMap);
//		this.claw = new Claw(hardwareMap);
//		this.camera = new Camera(hardwareMap);
//		this.drone = new Drone(hardwareMap);
//		this.wrist = new Wrist(hardwareMap);
//	}
//
//	public void sendTelemetry(Telemetry telemetry) {
//		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//		if (drive.initialized) telemetry.addData("speedState", drive.speedState);
//		if (drive.initialized) telemetry.addData("speed", Drive.SPEED);
//		if (drive.initialized) telemetry.addData("poseEstimate", drive.mecanumDrive.getPoseEstimate());
//		if (claw.initialized) telemetry.addData("leftClawClosed", claw.leftClawClosed);
//		if (claw.initialized) telemetry.addData("rightClawClosed", claw.rightClawClosed);
//		if (wrist.initialized) telemetry.addData("wristState", wrist.wristState);
//		if (arm.initialized) telemetry.addData("armPos", arm.armMotor.getCurrentPosition());
//		if (drone.initialized) telemetry.addData("droneReleased?", drone.droneReleased);
//
//		telemetry.update();
//	}
//
//	public void raiseArm() {
//		wrist.lift();
//		arm.armMotor.setPower(1);
//		arm.armToTicks(Arm.BACKDROP_TICKS);
//	}
//
//	public void lowerArm() {
//		wrist.lower();
//		arm.armMotor.setPower(1);
//		arm.armToTicks(Arm.BASE_TICKS);
//	}
//
//	public void hang() {
//		wrist.hang();
//		arm.armMotor.setPower(1);
//		arm.armToTicks(Arm.HANG_TICKS);
//	}
//
//	public void openClaw() {
//		claw.open("left");
//		claw.open("right");
//	}
//
//	public void closeClaw() {
//		claw.close("left");
//		claw.close("right");
//	}
//}
