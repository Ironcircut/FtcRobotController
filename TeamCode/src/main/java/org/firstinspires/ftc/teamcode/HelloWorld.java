package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@Autonomous
public class HelloWorld extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Hello", "Jordan Torres"); // My first Comment
    }

    @Override
    public void loop() {
    }

    // Single Line Comment
    /*
    1.Hello: world, Change the telemetary data to display "hello: Your Name"
    2.Run this code in the Autonomus section of your DS
     */
}
