package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ifPractice extends OpMode {

    @Override
    public void init() {

        }

    @Override
    public void loop() {
        boolean aButton=gamepad1.a;// press TRUE, depress FALSE
        // true or false

        if (aButton==true) {
            telemetry.addData("A Button", "Pressed!");
        }
            else {
                telemetry.addData("A button", "Not Pressed!");
        }
        telemetry.addData("A Button State", aButton);
    }
}
