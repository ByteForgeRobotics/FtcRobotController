package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Controls a mecanum drivetrain with four motors.
 * Supports driving forward/backward, strafing, turning, and diagonal movement in inches.
 */
public class DriveBase {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final int TICKS_PER_REV = 560;
    public static final double GEAR_REDUCTION = 1.0;
    public static final double TICKS_PER_INCH = (TICKS_PER_REV * GEAR_REDUCTION) / (Math.PI * WHEEL_DIAMETER_INCHES);
    public static final double ROBOT_TRACK_WIDTH = 15.0;

    /**
     * Constructs the drivetrain and initializes motors.
     *
     * @param hardwareMap HardwareMap for motors
     */
    public DriveBase(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
    }

    /**
     * Resets encoders and sets motors to run using encoder mode.
     */
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setRunToPosition() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Drives forward/backward specified inches with speed ramping.
     *
     * @param inches Distance to drive (positive forward, negative backward)
     * @param maxSpeed Maximum motor power (0.0 to 1.0)
     */
    public void driveInches(double inches, double maxSpeed) {
        int ticks = (int)(inches * TICKS_PER_INCH);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        setRunToPosition();

        while (isBusy()) {
            double remaining = getRemainingInches();
            double speed = getSpeedBasedOnDistance(remaining, maxSpeed);
            setMotorPowers(speed);
        }

        stop();
    }

    /**
     * Strafes left or right specified inches.
     *
     * @param inches Distance to strafe (positive right, negative left)
     * @param maxSpeed Max motor power
     */
    public void strafeInches(double inches, double maxSpeed) {
        int ticks = (int)(inches * TICKS_PER_INCH);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() - ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);

        setRunToPosition();

        while (isBusy()) {
            double remaining = getRemainingInches();
            double speed = getSpeedBasedOnDistance(remaining, maxSpeed);
            setMotorPowers(speed);
        }

        stop();
    }

    /**
     * Turns robot by degrees. Positive degrees turn right; negative left.
     *
     * @param degrees Angle to turn
     * @param maxSpeed Max motor power
     */
    public void turnDegrees(double degrees, double maxSpeed) {
        double turnCircumference = Math.PI * ROBOT_TRACK_WIDTH;
        double inches = (degrees / 360.0) * turnCircumference;
        int ticks = (int)(inches * TICKS_PER_INCH);

        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() - ticks);
        backRight.setTargetPosition(backRight.getCurrentPosition() - ticks);

        setRunToPosition();

        while (isBusy()) {
            double remaining = getRemainingInches();
            double speed = getSpeedBasedOnDistance(remaining, maxSpeed);
            setMotorPowers(speed);
        }

        stop();
    }

    /**
     * Moves robot diagonally by inches in the given direction.
     *
     * @param inches Distance to move diagonally
     * @param direction "right_forward" or "left_forward"
     * @param maxSpeed Max motor power
     */
    public void diagonalInches(double inches, String direction, double maxSpeed) {
        int ticks = (int)(inches * TICKS_PER_INCH);

        if (direction.equalsIgnoreCase("right_forward")) {
            frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + ticks);
            backRight.setTargetPosition(backRight.getCurrentPosition() + ticks);
        } else if (direction.equalsIgnoreCase("left_forward")) {
            frontRight.setTargetPosition(frontRight.getCurrentPosition() + ticks);
            backLeft.setTargetPosition(backLeft.getCurrentPosition() + ticks);
        } else {
            return; // Invalid direction
        }

        setRunToPosition();

        while (isBusy()) {
            double remaining = getRemainingInches();
            double speed = getSpeedBasedOnDistance(remaining, maxSpeed);
            setMotorPowers(speed);
        }

        stop();
    }

    /**
     * Returns average encoder error ticks across all four motors.
     *
     * @return average error in ticks
     */
    public double getAverageErrorTicks() {
        int flError = Math.abs(frontLeft.getTargetPosition() - frontLeft.getCurrentPosition());
        int frError = Math.abs(frontRight.getTargetPosition() - frontRight.getCurrentPosition());
        int blError = Math.abs(backLeft.getTargetPosition() - backLeft.getCurrentPosition());
        int brError = Math.abs(backRight.getTargetPosition() - backRight.getCurrentPosition());
        return (flError + frError + blError + brError) / 4.0;
    }

    /**
     * Returns average remaining distance in inches to target position.
     *
     * @return remaining distance in inches
     */
    public double getRemainingInches() {
        return getAverageErrorTicks() / TICKS_PER_INCH;
    }

    /**
     * Calculates motor speed based on distance remaining for smooth deceleration.
     *
     * @param remainingInches Distance left to move
     * @param maxSpeed Maximum speed allowed
     * @return speed value clamped between minSpeed and maxSpeed
     */
    public double getSpeedBasedOnDistance(double remainingInches, double maxSpeed) {
        double minSpeed = 0.2;
        double rampDownStart = 12.0;

        if (remainingInches > rampDownStart) {
            return maxSpeed;
        } else {
            double speed = minSpeed + (remainingInches / rampDownStart) * (maxSpeed - minSpeed);
            return Math.max(minSpeed, Math.min(speed, maxSpeed));
        }
    }

    /**
     * Sets power to all motors with sign matching their target direction.
     *
     * @param speed Desired motor speed (magnitude)
     */
    public void setMotorPowers(double speed) {
        frontLeft.setPower(speed * Math.signum(frontLeft.getTargetPosition() - frontLeft.getCurrentPosition()));
        frontRight.setPower(speed * Math.signum(frontRight.getTargetPosition() - frontRight.getCurrentPosition()));
        backLeft.setPower(speed * Math.signum(backLeft.getTargetPosition() - backLeft.getCurrentPosition()));
        backRight.setPower(speed * Math.signum(backRight.getTargetPosition() - backRight.getCurrentPosition()));
    }

    /**
     * Checks if any motor is still busy moving to target.
     *
     * @return true if any motor is busy
     */
    public boolean isBusy() {
        return frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy();
    }

    /**
     * Stops all motors immediately.
     */
    public void stop() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
