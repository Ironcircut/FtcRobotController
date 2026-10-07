package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ifPractice3 extends OpMode {

    @Override
    public void init() {

        }

    @Override
    public void loop() {
        double leftY=gamepad1.left_stick_y;
        double motorSpeed=gamepad1.left_stick_y;

        if (!gamepad1.a) {
            motorSpeed*=0.5;

        }

        if (leftY < 0.1 && leftY> -0.1) {
            telemetry.addData("Left Stick","In Dead Zone");
        }
            telemetry.addData("Left Stick Value", leftY);
    }
}

/*
1.Make a Turbo button. If the "a" button is NOT pressed, multiplay teh motor speed by 0.5, otherwise ise the standard speed

 */
/*

AND - && if (leftY < 0.5 && leftY) {
OR - || if (leftY < 0 || rightY <0) {
NOT - ! if (!clawClosed) {
 */
