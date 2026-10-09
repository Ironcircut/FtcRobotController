package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class GamePadPractice extends OpMode {
    @Override
    public void init() {

    }

    @Override
    public void loop() {
        //*runs 50 times a second
        double speedForward = gamepad1.left_stick_y / 2.0;
        double xStickDifference = gamepad1.left_stick_x-gamepad1.right_stick_x;
        double sumOfTrigger = gamepad1.left_trigger + gamepad1.right_trigger;

        telemetry.addData("left x", gamepad1.left_stick_x);
        telemetry.addData("left y", speedForward);
        telemetry.addData("right x",gamepad1.right_stick_x);
        telemetry.addData("right y",gamepad1.right_stick_y);
        telemetry.addData("b",gamepad1.b);
        telemetry.addData("a button",gamepad1.a);
        telemetry.addData("Right trigger",gamepad1.right_trigger);
        telemetry.addData("Left Trigger", gamepad1.left_trigger);
        telemetry.addData("Sum of Triggers", sumOfTrigger);
        telemetry.addData("Difference Between x sticks", xStickDifference);


    }
    /*
    1.add telemetry for the right joystick
    2.add telemetry for the b button
    3.add telemetry data to report the DIFFERENCE between x left and x right joy sticks
    4.Add telemetry data to report the sum of both triggers
     */
}
