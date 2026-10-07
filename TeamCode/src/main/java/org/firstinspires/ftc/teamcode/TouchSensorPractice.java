package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.TestBench;

@TeleOp
public class TouchSensorPractice extends OpMode {
    TestBench bench = new TestBench();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        String touchSensorState = "Not Pressed";
        if (bench.isTouchSensorPresssed()) {
            touchSensorState = "pressed!";
        }
        telemetry.addData("touch sensor state",bench.isTouchSensorPresssed());
        telemetry.addData("touch sensor state",touchSensorState);
    }

    /*
    1.creata a new getter method in your testBench class called "isTouchSensorReleased" return true if the touch sensor is not being pressed.
    2.In your telemetry opmode have telemetry state "pressed!" and nto pressed instead of true or false
     */
}
