package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
@Autonomous(name = "!Seconds Measure")
public class TestAutoLength extends LinearOpMode {
	private final FtcDashboard dashboard = FtcDashboard.getInstance();

	@Override
	public void runOpMode() {
		Robot robot = new Robot(hardwareMap);
		robot.initializeDrivetrain();

		waitForStart();
		robot.driveStraight(-0.6);
		sleep(2000);
		requestOpModeStop();
		}
	}
