package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class VariableExamples {
    // data types
    int robotHeight;
    // use int only when using whole numbers
    double motorSpeed;
    //only use when using decimal numbers example:0.2123123123123
    boolean clawClose;
    //True or false value example:(claw is either closed or open)

    public void setupVariables() {
        robotHeight = 10;
        motorSpeed = 0.5;
        clawClose = true;
    }
    // Note: 8 + x = 25 is algebra, not valid Java assignment.
    // When variable is first created it doesn't have a value in order to give it a value you give it an equal sign


}
