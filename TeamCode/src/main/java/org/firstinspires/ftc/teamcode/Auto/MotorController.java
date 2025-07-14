package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Encapsulates control of a single DC motor with encoder support.
 * Supports moving by inches (encoder ticks), setting power, and stopping.
 */
public class MotorController {

    public DcMotor motor;
    private final double TICKS_PER_INCH;

    /**
     * Creates a MotorController for a specific motor.
     *
     * @param hardwareMap HardwareMap for hardware access
     * @param name Motor hardware name
     * @param ticksPerInch Encoder ticks per inch of movement (calibration constant)
     */
    public MotorController(HardwareMap hardwareMap, String name, double ticksPerInch) {
        motor = hardwareMap.get(DcMotor.class, name);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        TICKS_PER_INCH = ticksPerInch;
    }

    /**
     * Moves the motor a specific distance in inches at a given power.
     * This method blocks until the motor reaches the target position.
     *
     * @param inches Distance to move (positive or negative)
     * @param power Motor power (0.0 to 1.0)
     */
    public void moveInches(double inches, double power) {
        int ticks = (int)(inches * TICKS_PER_INCH);
        motor.setTargetPosition(motor.getCurrentPosition() + ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(Math.abs(power));

        while (motor.isBusy()) {
            // Optional: add idle() or telemetry update here if used in a loop
        }

        stop();
    }

    /**
     * Sets the motor power directly (no encoder).
     *
     * @param power Power level (-1.0 to 1.0)
     */
    public void setPower(double power) {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setPower(power);
    }

    /**
     * Stops the motor by setting power to zero.
     */
    public void stop() {
        motor.setPower(0);
    }
}
