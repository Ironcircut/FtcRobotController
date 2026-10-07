package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ifPractice2 extends OpMode {

    @Override
    public void init() {

        }

    @Override
    public void loop() {
        double leftY=gamepad1.left_stick_y;

                if(leftY < 0) {
                    telemetry.addData("Left Stick", "Is Negative");

                }
                else if (leftY>0.5) {
                    telemetry.addData("Left Stick", "Greater then 50%");

                }
                else if (leftY > 0) {
                    telemetry.addData("Left Stick", "is greater then 0");
                }

                else {
                    telemetry.addData("Left Stick", "Is 0");
                }
                telemetry.addData("Left Stick", leftY);
    }
}

/*

AND - && if (leftY < 0.5 && leftY) {
OR - || if (leftY < 0 || rightY <0) {
NOT - ! if (!clawClosed) {
 */
