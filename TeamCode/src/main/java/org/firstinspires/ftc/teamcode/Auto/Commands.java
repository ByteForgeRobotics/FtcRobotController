package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Implements individual robot commands invoked by the AutoScriptor.
 * Commands operate on the DriveBase, MotorController, servos, and provide utility functions.
 */
public class Commands {

    /**
     * Moves the robot forward or backward a specified distance in inches.
     *
     * @param direction "forward" or "backward"
     * @param distance String representing inches to move
     * @param driveBase DriveBase object controlling motors
     * @param autoScriptor OpMode instance for timing
     * @param telemetry Telemetry for logging
     */
    public static void move(String direction, String distance, DriveBase driveBase, AutoScriptor autoScriptor, Telemetry telemetry) {
        double inches = Double.parseDouble(distance);
        if (direction.equalsIgnoreCase("forward")) {
            driveBase.driveInches(inches, 0.6);
        } else if (direction.equalsIgnoreCase("backward")) {
            driveBase.driveInches(-inches, 0.6);
        }
    }

    /**
     * Turns the robot left or right by a specified angle in degrees.
     *
     * @param direction "left" or "right"
     * @param angle String degrees to turn
     * @param driveBase DriveBase object controlling motors
     * @param autoScriptor OpMode instance for timing
     * @param telemetry Telemetry for logging
     */
    public static void turn(String direction, String angle, DriveBase driveBase, AutoScriptor autoScriptor, Telemetry telemetry) {
        double degrees = Double.parseDouble(angle);
        if (direction.equalsIgnoreCase("right")) {
            driveBase.turnDegrees(degrees, 0.5);
        } else if (direction.equalsIgnoreCase("left")) {
            driveBase.turnDegrees(-degrees, 0.5);
        }
    }

    /**
     * Strafes the robot left or right a specified distance in inches.
     *
     * @param direction "left" or "right"
     * @param distance String inches to strafe
     * @param driveBase DriveBase object
     * @param autoScriptor OpMode instance
     * @param telemetry Telemetry
     */
    public static void strafe(String direction, String distance, DriveBase driveBase, AutoScriptor autoScriptor, Telemetry telemetry) {
        double inches = Double.parseDouble(distance);
        if (direction.equalsIgnoreCase("right")) {
            driveBase.strafeInches(inches, 0.6);
        } else if (direction.equalsIgnoreCase("left")) {
            driveBase.strafeInches(-inches, 0.6);
        }
    }

    /**
     * Moves the robot diagonally in a specified direction a certain distance.
     *
     * @param direction One of "right_forward", "left_forward", "right_backward", "left_backward"
     * @param distance String inches to move diagonally
     * @param driveBase DriveBase object
     * @param autoScriptor OpMode instance
     * @param telemetry Telemetry
     */
    public static void diagonal(String direction, String distance, DriveBase driveBase, AutoScriptor autoScriptor, Telemetry telemetry) {
        double inches = Double.parseDouble(distance);
        if (direction.equalsIgnoreCase("right_forward")) {
            driveBase.diagonalInches(inches, "right_forward", 0.5);
        } else if (direction.equalsIgnoreCase("left_forward")) {
            driveBase.diagonalInches(inches, "left_forward", 0.5);
        } else if (direction.equalsIgnoreCase("right_backward")) {
            driveBase.diagonalInches(-inches, "right_forward", 0.5);
        } else if (direction.equalsIgnoreCase("left_backward")) {
            driveBase.diagonalInches(-inches, "left_forward", 0.5);
        }
    }

    /**
     * Sets the position of a servo.
     *
     * @param name Servo hardware name
     * @param position Servo position as a string parseable to double [0.0 - 1.0]
     * @param opMode OpMode for hardwareMap
     * @param telemetry Telemetry for logging
     */
    public static void servo(String name, String position, LinearOpMode opMode, Telemetry telemetry) {
        Servo servo = opMode.hardwareMap.get(Servo.class, name);
        servo.setPosition(Double.parseDouble(position));
    }

    /**
     * Detects the dominant color seen by a ColorSensor.
     *
     * @param colorsensor ColorSensor hardware instance
     * @return String name of detected color ("Red", "Blue", "Green", or "Unknown")
     */
    public static String detectColor(ColorSensor colorsensor) {
        int argb = colorsensor.argb();
        int red = (argb >> 16) & 0xFF;
        int green = (argb >> 8) & 0xFF;
        int blue = argb & 0xFF;

        if (red > blue && red > green) return "Red";
        if (blue > red && blue > green) return "Blue";
        if (green > red && green > blue) return "Green";
        return "Unknown";
    }

    /**
     * Controls a motor with specific commands: move by inches, set power, or stop.
     *
     * @param name Motor hardware name
     * @param action "move", "power", or "stop"
     * @param value Value related to action (distance for move, power level for power)
     * @param opMode OpMode for hardwareMap
     * @param telemetry Telemetry for logging
     */
    public static void motorMove(String name, String action, String value, LinearOpMode opMode, Telemetry telemetry) {
        MotorController motor = new MotorController(opMode.hardwareMap, name, 100); // Example ticks per inch
        if (action.equalsIgnoreCase("move")) {
            motor.moveInches(Double.parseDouble(value), 0.5);
        } else if (action.equalsIgnoreCase("power")) {
            motor.setPower(Double.parseDouble(value));
        } else if (action.equalsIgnoreCase("stop")) {
            motor.stop();
        }
    }

    /**
     * Waits (sleeps) for the specified number of milliseconds.
     *
     * @param ms String milliseconds to wait
     * @param opMode OpMode instance to call sleep()
     */
    public static void waitMillis(String ms, LinearOpMode opMode) {
        opMode.sleep(Long.parseLong(ms));
    }

    /**
     * Logs a message to telemetry.
     *
     * @param message The message to log
     * @param telemetry Telemetry instance
     */
    public static void log(String message, Telemetry telemetry) {
        telemetry.addLine("[LOG] " + message);
        telemetry.update();
    }
}
