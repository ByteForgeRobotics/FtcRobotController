package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Commands {

    public static void move(String direction, String distance, Telemetry telemetry) {
        telemetry.addLine("Moving " + direction + " for " + distance + " units");
    }

    public static void turn(String direction, String angle, Telemetry telemetry) {
        telemetry.addLine("Turning " + direction + " by " + angle + " degrees");
    }

    public static void strafe(String direction, String distance, Telemetry telemetry) {
        telemetry.addLine("Strafing " + direction + " for " + distance + " units");
    }

    public static void servo(String name, String position, Telemetry telemetry) {
        telemetry.addLine("Setting servo " + name + " to " + position);
    }

    public static void waitMillis(String ms, Telemetry telemetry) {
        long waitTime = Long.parseLong(ms);
        telemetry.addLine("Waiting " + ms + " ms");
        ElapsedTime timer = new ElapsedTime();
        while (timer.milliseconds() < waitTime) {
            // Busy wait (OnBot Java doesn't allow Thread.sleep() in OpMode loop)
        }
    }

    public static void log(String message, Telemetry telemetry) {
        telemetry.addLine("[LOG] " + message);
    }
}
