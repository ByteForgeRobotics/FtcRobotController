package org.firstinspires.ftc.teamcode.encoder;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Simple helper for using a motor encoder to track distance.
 */
public class Encoder {

    private DcMotor motor;

    private double ticksPerRev;
    private double wheelDiameterInches;

    public Encoder(HardwareMap hardwareMap, String motorName, double ticksPerRev, double wheelDiameterInches) {
        this.motor = hardwareMap.get(DcMotor.class, motorName);
        this.ticksPerRev = ticksPerRev;
        this.wheelDiameterInches = wheelDiameterInches;

        reset();
    }

    /**
     * Get current encoder ticks.
     */
    public int getTicks() {
        return motor.getCurrentPosition();
    }

    /**
     * Convert ticks to inches.
     */
    public double ticksToInches(int ticks) {
        double wheelCircumference = Math.PI * wheelDiameterInches;
        return (ticks / ticksPerRev) * wheelCircumference;
    }

    /**
     * Get current distance in inches.
     */
    public double getInches() {
        return ticksToInches(getTicks());
    }

    /**
     * Reset the encoder.
     */
    public void reset() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Expose the motor for manual control in your OpMode.
     */
    public DcMotor getMotor() {
        return motor;
    }

    /**
     * Checks if current ticks are within allowed error of target.
     */
    public boolean atTarget(int targetTicks, int tolerance) {
        return Math.abs(getTicks() - targetTicks) <= tolerance;
    }

    /**
     *  * Example usage:
     *  *
     *  * Encoder encoder = new Encoder(hardwareMap, "motorName", 537.7, 4.0);
     *  *
     *  * waitForStart();
     *  *
     *  * int targetTicks = 1000;
     *  *
     *  * while (opModeIsActive() && !encoder.atTarget(targetTicks, 10)) {
     *  *     int current = encoder.getTicks();
     *  *     double error = targetTicks - current;
     *  *     double power = 0.002 * error; // Example P-control, you tune this!
     *  *     encoder.getMotor().setPower(power);
     *  * }
     *  *
     *  * encoder.getMotor().setPower(0);
     *  */
}

