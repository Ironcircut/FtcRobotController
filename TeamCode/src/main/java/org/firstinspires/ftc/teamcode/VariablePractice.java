package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class VariablePractice extends OpMode {
    @Override
    public void init() {
        int teamNumber = 4243;
        double motorSpeed = 0.75;
        boolean clawClosed = true;
        int motorAngle = 130;
        String teamName = "IronDevils";
        telemetry.addData("Team Number", teamNumber);
        telemetry.addData("motor Speed", motorSpeed);
        telemetry.addData("claw closed", clawClosed);
        telemetry.addData("name", teamName);
        telemetry.addData("motor angle",motorAngle);
    }

    @Override
    public void loop() {
        /*
        1.change the string variable "name" to your team name
        2.Create an int called "motorAngle" and store an angle between 0-180. display this in your int method.
         */

    }
}
