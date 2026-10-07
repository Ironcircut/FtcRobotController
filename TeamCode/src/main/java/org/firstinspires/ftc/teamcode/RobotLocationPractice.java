package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
public class RobotLocationPractice {
    double angle;
    double x;
    double y;

    //constructor method
    public RobotLocationPractice(double angle) {
        this.angle = angle;
    }
    public double getX(){
        return this.x;


    }

    public void changeX(double changeAmount) {
        x+=changeAmount;

    }
    public void setX(double x) {
        this.x=x;

    }

    public double getY() {
        return this.y;
    }
    public void setY(double y) {
        this. y=y;
    }
    public void changeY(double changeAmount) {
        y+=changeAmount;
    }
    public double getHeading() {
        // this method normalizes robot heading between -360 and 180
        //his is useful for calculating turn angles, especially when crossing the 0, 360 boundery
        double angle = this.angle; //copy the angle of the imu
        while (angle > 180) {
            angle-=360;// subtract until in target range
        }
        while (angle<= -180) {
            angle +=360;//add until in target range
        }
        return angle;//return normalize value
    }

    public void turnRobot(double angleChange) {
        angle += angleChange;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
    public double getAngle() {
        return this.angle;
    }
}
