package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class UseRobotLocationOpMode extends OpMode {
    RobotLocationPractice robotLocationPractice = new RobotLocationPractice(0);

    @Override
    public void init() {
        robotLocationPractice.setAngle(0);
        robotLocationPractice.setX(0);
        robotLocationPractice.setY(0);

    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            robotLocationPractice.changeY(-0.1);
        }
        else if (gamepad1.dpad_down) {
            robotLocationPractice.changeY(0.1);

        }

        if (gamepad1.a) {
            robotLocationPractice.turnRobot(0.1);
        }
        else if (gamepad1.b) {
            robotLocationPractice.turnRobot(-0.1);
            
        }

        if (gamepad1.dpad_left) {
            robotLocationPractice.changeX(0.1);
        } else if (gamepad1.dpad_right) {
            robotLocationPractice.changeX(-0.1);

        }

        telemetry.addData("heading", robotLocationPractice.getHeading());
        telemetry.addData("Angle",robotLocationPractice.getAngle());
        telemetry.addData("X value", robotLocationPractice.getX());
        telemetry.addData("Y Value",robotLocationPractice.getY());
    }
}

/*
1.add a double getAngle to your robotLocationPractice, and display this in your OpMode
2.Inside your RobotLocationPractic class,
-create a double x
-double getX()
-void changeX(double change amount)
 -setX(double X)
 3. inside of your op mode
 -when left dpad pressed + 0.1 to x
 -When right dpad press - 0.1 to x
 -telemetry display your x value
 3. Add in support for y as well
 */