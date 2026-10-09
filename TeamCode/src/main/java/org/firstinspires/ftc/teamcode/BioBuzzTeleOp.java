
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class BioBuzzTeleOp extends LinearOpMode {

    // ========================================
    // CONTROL HUB - MOTORS
    // ========================================

    private DcMotorEx fL; // M0 - Front Left
    private DcMotorEx fR; // M1 - Front Right
    private DcMotorEx bL; // M2 - Back Left
    private DcMotorEx bR; // M3 - Back Right

    // ========================================
    // EXPANSION HUB - MOTORS
    // ========================================

    private DcMotorEx iF; // M0 - Front Intake
    private DcMotorEx iB; // M1 - Back Intake
    private DcMotorEx oM; // M2 - Outtake

    // ========================================
    // CONTROL HUB - SERVOS
    // ========================================

    private Servo mk2bD; // S0 - Back Drop
    private Servo mk2fD; // S1 - Front Drop

    // ========================================
    // EXPANSION HUB - SERVOS
    // ========================================

    private Servo mk2fL; // S0 - Front Left
    private Servo mk2fR; // S1 - Front Right
    private Servo mk2bL; // S2 - Back Left
    private Servo mk2bR; // S3 - Back Right
    private Servo mk1mL; // S4 - Middle Left
    private Servo mk1mR; // S5 - Middle Right

    // ========================================
    // SETTINGS
    // ========================================

    private static final double DRIVE_SPEED = 1.0;
    private static final double SLOW_SPEED = 0.40;

    private static final double INTAKE_SPEED = 1.0;
    private static final double OUTTAKE_SPEED = 1.0;

    // Placeholder servo positions.
    // Calibrate before operating mechanisms.
    private static final double SERVO_IN = 0.35;
    private static final double SERVO_OUT = 0.65;

    // ========================================
    // TOGGLE STATES
    // ========================================

    private boolean intakeOn = false;
    private boolean middleActive = false;
    private boolean transferActive = false;

    private boolean lastCross = false;
    private boolean lastSquare = false;
    private boolean lastTriangle = false;

    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private boolean lastDpadLeft = false;
    private boolean lastDpadRight = false;

    private void setServoPosition(
            Servo servo, double position) {

        servo.setPosition(Range.clip(position, 0.0, 1.0));
    }

    @Override
    public void runOpMode() {

        // ====================================
        // HARDWARE INITIALIZATION
        // ====================================

        // Control Hub motors
        fL = hardwareMap.get(DcMotorEx.class, "fL");
        fR = hardwareMap.get(DcMotorEx.class, "fR");
        bL = hardwareMap.get(DcMotorEx.class, "bL");
        bR = hardwareMap.get(DcMotorEx.class, "bR");

        // Expansion Hub motors
        iF = hardwareMap.get(DcMotorEx.class, "iF");
        iB = hardwareMap.get(DcMotorEx.class, "iB");
        oM = hardwareMap.get(DcMotorEx.class, "oM");

        // Control Hub servos
        mk2bD = hardwareMap.get(Servo.class, "mk2bD");
        mk2fD = hardwareMap.get(Servo.class, "mk2fD");

        // Expansion Hub servos
        mk2fL = hardwareMap.get(Servo.class, "mk2fL");
        mk2fR = hardwareMap.get(Servo.class, "mk2fR");
        mk2bL = hardwareMap.get(Servo.class, "mk2bL");
        mk2bR = hardwareMap.get(Servo.class, "mk2bR");
        mk1mL = hardwareMap.get(Servo.class, "mk1mL");
        mk1mR = hardwareMap.get(Servo.class, "mk1mR");

        // ====================================
        // MECANUM MOTOR DIRECTIONS
        // ====================================

        // Standard starting configuration.
        // Verify with goBILDA wheel diagram.

        fL.setDirection(DcMotor.Direction.REVERSE);
        bL.setDirection(DcMotor.Direction.REVERSE);

        fR.setDirection(DcMotor.Direction.FORWARD);
        bR.setDirection(DcMotor.Direction.FORWARD);

        // ====================================
        // MECHANISM MOTOR DIRECTIONS
        // ====================================

        iF.setDirection(DcMotor.Direction.FORWARD);
        iB.setDirection(DcMotor.Direction.FORWARD);
        oM.setDirection(DcMotor.Direction.FORWARD);

        // ====================================
        // ZERO POWER BEHAVIOR
        // ====================================

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

        // Reset motors to zero power.
        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);

        iF.setPower(0);
        iB.setPower(0);
        oM.setPower(0);

        // Do not move servos automatically
        // during initialization.

        telemetry.addLine("BIOBUZZ INITIALIZED");
        telemetry.addLine("7 Motors Ready");
        telemetry.addLine("8 Servos Ready");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        // ====================================
        // MAIN TELEOP LOOP
        // ====================================

        while (opModeIsActive()) {

            // =================================
            // MECANUM DRIVE - PS5 CONTROLLER 1
            // =================================

            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // L1 = precision mode.
            double speed = gamepad1.left_bumper
                    ? SLOW_SPEED : DRIVE_SPEED;

            // Normalize motor powers.
            double denominator = Math.max(
                    Math.abs(y) +
                            Math.abs(x) +
                            Math.abs(rx),
                    1.0
            );

            // goBILDA mecanum movement.
            double flPower =
                    (y + x + rx) / denominator;

            double frPower =
                    (y - x - rx) / denominator;

            double blPower =
                    (y - x + rx) / denominator;

            double brPower =
                    (y + x - rx) / denominator;

            // Apply speed multiplier.
            fL.setPower(flPower * speed);
            fR.setPower(frPower * speed);
            bL.setPower(blPower * speed);
            bR.setPower(brPower * speed);

            // =================================
            // INTAKES - PS5 CONTROLLER 2
            // =================================

            // Cross toggles both intakes.

            boolean cross = gamepad2.a;

            if (cross && !lastCross) {
                intakeOn = !intakeOn;
            }

            lastCross = cross;

            double intakePower =
                    intakeOn ? INTAKE_SPEED : 0.0;

            iF.setPower(intakePower);
            iB.setPower(intakePower);

            // =================================
            // OUTTAKE - PS5 CONTROLLER 2
            // =================================

            // R2 = forward
            // L2 = reverse

            double outtakePower =
                    gamepad2.right_trigger -
                            gamepad2.left_trigger;

            oM.setPower(
                    Range.clip(
                            outtakePower * OUTTAKE_SPEED,
                            -1.0,
                            1.0
                    )
            );

            // =================================
            // MIDDLE SERVOS - PS5 SQUARE
            // =================================

            boolean square = gamepad2.x;

            if (square && !lastSquare) {

                middleActive = !middleActive;

                double middlePosition =
                        middleActive
                                ? SERVO_OUT
                                : SERVO_IN;

                setServoPosition(
                        mk1mL, middlePosition);

                setServoPosition(
                        mk1mR, middlePosition);
            }

            lastSquare = square;

            // =================================
            // TRANSFER SERVOS - PS5 TRIANGLE
            // =================================

            boolean triangle = gamepad2.y;

            if (triangle && !lastTriangle) {

                transferActive = !transferActive;

                double transferPosition =
                        transferActive
                                ? SERVO_OUT
                                : SERVO_IN;

                setServoPosition(
                        mk2fL, transferPosition);

                setServoPosition(
                        mk2fR, transferPosition);

                setServoPosition(
                        mk2bL, transferPosition);

                setServoPosition(
                        mk2bR, transferPosition);
            }

            lastTriangle = triangle;

            // =================================
            // FRONT DROP - D-PAD UP/DOWN
            // =================================

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

            // =================================
            // BACK DROP - D-PAD LEFT/RIGHT
            // =================================

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

            // =================================
            // TELEMETRY
            // =================================

            telemetry.addLine("BIOBUZZ TELEOP");

            telemetry.addData(
                    "Drive Speed", speed);

            telemetry.addData(
                    "Front Left", flPower);

            telemetry.addData(
                    "Front Right", frPower);

            telemetry.addData(
                    "Back Left", blPower);

            telemetry.addData(
                    "Back Right", brPower);

            telemetry.addData(
                    "Intakes", intakeOn);

            telemetry.addData(
                    "Outtake Power", outtakePower);

            telemetry.addData(
                    "Middle Servos", middleActive);

            telemetry.addData(
                    "Transfer Servos", transferActive);

            telemetry.update();
        }

        // ====================================
        // STOP ALL MOTORS
        // ====================================

        fL.setPower(0);
        fR.setPower(0);
        bL.setPower(0);
        bR.setPower(0);

        iF.setPower(0);
        iB.setPower(0);
        oM.setPower(0);
    }
}
