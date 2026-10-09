
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp
public class BioBuzzTeleopField extends LinearOpMode {

    // ==========================================
    // CONTROL HUB - DRIVE MOTORS
    // ==========================================

    private DcMotorEx fL; // Motor 0
    private DcMotorEx fR; // Motor 1
    private DcMotorEx bL; // Motor 2
    private DcMotorEx bR; // Motor 3

    // ==========================================
    // EXPANSION HUB - MOTORS
    // ==========================================

    private DcMotorEx iF; // Motor 0
    private DcMotorEx iB; // Motor 1
    private DcMotorEx oM; // Motor 2

    // ==========================================
    // CONTROL HUB - SERVOS
    // ==========================================

    private Servo mk2bD; // Servo 0
    private Servo mk2fD; // Servo 1

    // ==========================================
    // EXPANSION HUB - SERVOS
    // ==========================================

    private Servo mk2fL; // Servo 0
    private Servo mk2fR; // Servo 1
    private Servo mk2bL; // Servo 2
    private Servo mk2bR; // Servo 3
    private Servo mk1mL; // Servo 4
    private Servo mk1mR; // Servo 5

    // ==========================================
    // IMU
    // ==========================================

    private IMU imu;

    // IMPORTANT:
    // Assumes logo starts facing up,
    // USB starts facing forward,
    // and USB end tilts downward 65 degrees.
    //
    // Change sign if the mounting is opposite.
    // Verify orientation before driving.

    private static final double HUB_X_TILT = -65.0;

    // ==========================================
    // DRIVE SETTINGS
    // ==========================================

    private static final double DRIVE_SPEED = 1.0;
    private static final double SLOW_SPEED = 0.40;

    // Mecanum strafe compensation
    private static final double STRAFE_MULTIPLIER = 1.0;

    // Joystick deadzone
    private static final double DEADZONE = 0.05;

    // ==========================================
    // MECHANISM SETTINGS
    // ==========================================

    private static final double INTAKE_SPEED = 1.0;
    private static final double OUTTAKE_SPEED = 1.0;

    // Placeholder servo positions.
    // MUST calibrate for your mechanisms.

    private static final double SERVO_IN = 0.35;
    private static final double SERVO_OUT = 0.65;

    // ==========================================
    // STATE VARIABLES
    // ==========================================

    private boolean intakeOn = false;
    private boolean middleActive = false;
    private boolean transferActive = false;

    private boolean lastCross = false;
    private boolean lastSquare = false;
    private boolean lastTriangle = false;
    private boolean lastOptions = false;

    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private boolean lastDpadLeft = false;
    private boolean lastDpadRight = false;

    // ==========================================
    // HELPER METHODS
    // ==========================================

    private void setServoPosition(
            Servo servo,
            double position) {

        servo.setPosition(
                Range.clip(position, 0.0, 1.0)
        );
    }

    private double applyDeadzone(double value) {
        if (Math.abs(value) < DEADZONE) {
            return 0.0;
        }
        return value;
    }

    // ==========================================
    // MAIN OPMODE
    // ==========================================

    @Override
    public void runOpMode() {

        // ======================================
        // INITIALIZE MOTORS
        // ======================================

        fL = hardwareMap.get(DcMotorEx.class, "fL");
        fR = hardwareMap.get(DcMotorEx.class, "fR");
        bL = hardwareMap.get(DcMotorEx.class, "bL");
        bR = hardwareMap.get(DcMotorEx.class, "bR");

        iF = hardwareMap.get(DcMotorEx.class, "iF");
        iB = hardwareMap.get(DcMotorEx.class, "iB");
        oM = hardwareMap.get(DcMotorEx.class, "oM");

        // ======================================
        // INITIALIZE SERVOS
        // ======================================

        mk2bD = hardwareMap.get(Servo.class, "mk2bD");
        mk2fD = hardwareMap.get(Servo.class, "mk2fD");

        mk2fL = hardwareMap.get(Servo.class, "mk2fL");
        mk2fR = hardwareMap.get(Servo.class, "mk2fR");
        mk2bL = hardwareMap.get(Servo.class, "mk2bL");
        mk2bR = hardwareMap.get(Servo.class, "mk2bR");

        mk1mL = hardwareMap.get(Servo.class, "mk1mL");
        mk1mR = hardwareMap.get(Servo.class, "mk1mR");

        // ======================================
        // MOTOR DIRECTIONS
        // ======================================

        // Starting directions.
        // Verify with wheels lifted.

        fL.setDirection(DcMotor.Direction.REVERSE);
        bL.setDirection(DcMotor.Direction.REVERSE);

        fR.setDirection(DcMotor.Direction.FORWARD);
        bR.setDirection(DcMotor.Direction.FORWARD);

        iF.setDirection(DcMotor.Direction.FORWARD);
        iB.setDirection(DcMotor.Direction.FORWARD);
        oM.setDirection(DcMotor.Direction.FORWARD);

        // ======================================
        // MOTOR ZERO POWER SETTINGS
        // ======================================

        fL.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        iF.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);
        iB.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);
        oM.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);

        iF.setPower(0);
        iB.setPower(0);
        oM.setPower(0);

        // ======================================
        // INITIALIZE FIELD-CENTRIC IMU
        // ======================================

        imu = hardwareMap.get(IMU.class, "imu");

        // Non-orthogonal Control Hub mounting
        Orientation hubRotation = new Orientation(
                AxesReference.INTRINSIC,
                AxesOrder.ZYX,
                AngleUnit.DEGREES,
                0,
                0,
                (float) HUB_X_TILT,
                0
        );

        RevHubOrientationOnRobot hubOrientation =
                new RevHubOrientationOnRobot(
                        hubRotation
                );

        imu.initialize(
                new IMU.Parameters(hubOrientation)
        );

        // ======================================
        // READY
        // ======================================

        telemetry.addLine("BIOBUZZ FIELD TELEOP");
        telemetry.addLine("7 Motors Initialized");
        telemetry.addLine("8 Servos Initialized");
        telemetry.addLine("IMU Initialized");
        telemetry.addData("Hub X Tilt", HUB_X_TILT);
        telemetry.addLine(
                "Confirm IMU orientation before driving"
        );
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        // The initial robot heading becomes zero.
        imu.resetYaw();

        // ======================================
        // MAIN TELEOP LOOP
        // ======================================

        while (opModeIsActive()) {

            // ==================================
            // PS5 CONTROLLER 1 - DRIVE
            // ==================================

            double y = applyDeadzone(
                    -gamepad1.left_stick_y
            );

            double x = applyDeadzone(
                    gamepad1.left_stick_x
            );

            double rx = applyDeadzone(
                    gamepad1.right_stick_x
            );

            // L1 precision mode
            double speed =
                    gamepad1.left_bumper
                            ? SLOW_SPEED
                            : DRIVE_SPEED;

            // ==================================
            // RESET FIELD HEADING
            // ==================================

            // PS5 Options button is mapped to
            // the FTC gamepad start button.

            boolean options = gamepad1.start;

            if (options && !lastOptions) {
                imu.resetYaw();
            }

            lastOptions = options;

            // ==================================
            // READ ROBOT HEADING
            // ==================================

            double heading =
                    imu.getRobotYawPitchRollAngles()
                            .getYaw(AngleUnit.RADIANS);

            // ==================================
            // FIELD-CENTRIC TRANSFORMATION
            // ==================================

            // Convert field-relative stick
            // movement into robot-relative
            // movement using IMU heading.

            double rotX =
                    x * Math.cos(-heading)
                            - y * Math.sin(-heading);

            double rotY =
                    x * Math.sin(-heading)
                            + y * Math.cos(-heading);

            rotX *= STRAFE_MULTIPLIER;

            // ==================================
            // MECANUM DRIVE CALCULATION
            // ==================================

            double denominator = Math.max(
                    Math.abs(rotY)
                            + Math.abs(rotX)
                            + Math.abs(rx),
                    1.0
            );

            double flPower =
                    (rotY + rotX + rx)
                            / denominator;

            double frPower =
                    (rotY - rotX - rx)
                            / denominator;

            double blPower =
                    (rotY - rotX + rx)
                            / denominator;

            double brPower =
                    (rotY + rotX - rx)
                            / denominator;

            // Apply normalized motor powers
            fL.setPower(flPower * speed);
            fR.setPower(frPower * speed);
            bL.setPower(blPower * speed);
            bR.setPower(brPower * speed);

            // ==================================
            // PS5 CONTROLLER 2 - INTAKE
            // ==================================

            // Cross toggles both intake motors.

            boolean cross = gamepad2.a;

            if (cross && !lastCross) {
                intakeOn = !intakeOn;
            }

            lastCross = cross;

            double intakePower =
                    intakeOn ? INTAKE_SPEED : 0.0;

            iF.setPower(intakePower);
            iB.setPower(intakePower);

            // ==================================
            // OUTTAKE MOTOR
            // ==================================

            // R2 forward, L2 reverse

            double outtakePower =
                    gamepad2.right_trigger
                            - gamepad2.left_trigger;

            oM.setPower(
                    Range.clip(
                            outtakePower * OUTTAKE_SPEED,
                            -1.0,
                            1.0
                    )
            );

            // ==================================
            // MIDDLE TRANSFER SERVOS
            // ==================================

            // Square toggles middle servos.

            boolean square = gamepad2.x;

            if (square && !lastSquare) {

                middleActive = !middleActive;

                double position =
                        middleActive
                                ? SERVO_OUT
                                : SERVO_IN;

                setServoPosition(mk1mL, position);
                setServoPosition(mk1mR, position);
            }

            lastSquare = square;

            // ==================================
            // FRONT AND BACK TRANSFER SERVOS
            // ==================================

            // Triangle toggles four servos.

            boolean triangle = gamepad2.y;

            if (triangle && !lastTriangle) {

                transferActive = !transferActive;

                double position =
                        transferActive
                                ? SERVO_OUT
                                : SERVO_IN;

                setServoPosition(mk2fL, position);
                setServoPosition(mk2fR, position);
                setServoPosition(mk2bL, position);
                setServoPosition(mk2bR, position);
            }

            lastTriangle = triangle;

            // ==================================
            // FRONT DROP SERVO
            // ==================================

            boolean up = gamepad2.dpad_up;
            boolean down = gamepad2.dpad_down;

            if (up && !lastDpadUp) {
                setServoPosition(
                        mk2fD, SERVO_OUT);
            }

            if (down && !lastDpadDown) {
                setServoPosition(
                        mk2fD, SERVO_IN);
            }

            lastDpadUp = up;
            lastDpadDown = down;

            // ==================================
            // BACK DROP SERVO
            // ==================================

            boolean right = gamepad2.dpad_right;
            boolean left = gamepad2.dpad_left;

            if (right && !lastDpadRight) {
                setServoPosition(
                        mk2bD, SERVO_OUT);
            }

            if (left && !lastDpadLeft) {
                setServoPosition(
                        mk2bD, SERVO_IN);
            }

            lastDpadRight = right;
            lastDpadLeft = left;

            // ==================================
            // TELEMETRY
            // ==================================

            telemetry.addLine(
                    "BIOBUZZ FIELD-CENTRIC TELEOP"
            );

            telemetry.addData(
                    "Heading (degrees)",
                    Math.toDegrees(heading)
            );

            telemetry.addData(
                    "Drive Speed", speed
            );

            telemetry.addData(
                    "Field X", x
            );

            telemetry.addData(
                    "Field Y", y
            );

            telemetry.addData(
                    "Robot X", rotX
            );

            telemetry.addData(
                    "Robot Y", rotY
            );

            telemetry.addData(
                    "Front Left", flPower
            );

            telemetry.addData(
                    "Front Right", frPower
            );

            telemetry.addData(
                    "Back Left", blPower
            );

            telemetry.addData(
                    "Back Right", brPower
            );

            telemetry.addData(
                    "Intake Enabled", intakeOn
            );

            telemetry.addData(
                    "Outtake Power", outtakePower
            );

            telemetry.addData(
                    "Middle Active", middleActive
            );

            telemetry.addData(
                    "Transfer Active", transferActive
            );

            telemetry.addLine(
                    "Options = Reset Field Heading"
            );

            telemetry.update();
        }

        // ======================================
        // STOP ALL MOTORS
        // ======================================

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);

        iF.setPower(0);
        iB.setPower(0);
        oM.setPower(0);
    }
}
