package org.firstinspires.ftc.teamcode.powerplay;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;


@Disabled
@Autonomous(name = "!Robot Test Movement")
public class RobotTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.initializeDrivetrain();
		robot.initializeClaw();
		robot.initializeSlide();



		robot.sendTelemetry(telemetry);
		robot.slideToTicks(Robot.MEDIUM_JUNCTION_TICKS);
		sleep(2000);
		robot.slideToTicks(Robot.BASE_TICKS);
		sleep(2000);


//		robot.clawOpen();
//		sleep(1000);
//		robot.clawClose();
//		robot.driveStrafe(0.5);
//		sleep(500);
//		robot.driveStop();

	}
}