package org.firstinspires.ftc.teamcode.powerplay;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.robot.Robot;
import org.firstinspires.ftc.teamcode.centerstage.robot.Slide;


@Disabled
@Autonomous(name = "!Robot Test Movement")
public class RobotTest extends LinearOpMode {
	@Override
	public void runOpMode() {

		Robot robot = new Robot(hardwareMap);

		waitForStart();
		robot.drive.init();
		robot.claw.init();
		robot.slide.init();



		robot.sendTelemetry(telemetry);
		robot.slide.slideToTicks(Slide.MEDIUM_JUNCTION_TICKS);
		sleep(2000);
		robot.slide.slideToTicks(Slide.BASE_TICKS);
		sleep(2000);


//		robot.clawOpen();
//		sleep(1000);
//		robot.clawClose();
//		robot.driveStrafe(0.5);
//		sleep(500);
//		robot.driveStop();

	}
}