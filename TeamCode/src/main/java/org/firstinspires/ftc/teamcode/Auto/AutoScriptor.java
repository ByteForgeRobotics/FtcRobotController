package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.*;

@Autonomous(name = "AutoScriptor")
public class AutoScriptor extends LinearOpMode {

    private Map<String, String> scripts = new HashMap<>();
    private String selectedScriptName = "";
    private DriveBase driveBase;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Loading Auto Scripts...");
        telemetry.update();

        driveBase = new DriveBase(hardwareMap);

        // Add your scripts here
        scripts.put("Square", AutoScript.SQUARE);
        scripts.put("SlideUp", AutoScript.SLIDE_UP);

        List<String> availableScripts = new ArrayList<>(scripts.keySet());
        if (availableScripts.isEmpty()) {
            telemetry.addLine("No Auto Scripts found!");
            telemetry.update();
            waitForStart();
            return;
        }

        int index = 0;
        selectedScriptName = availableScripts.get(index);

        // Selector loop
        while (!isStarted() && !isStopRequested()) {
            boolean updated = false;

            if (gamepad1.dpad_up) {
                index = (index - 1 + availableScripts.size()) % availableScripts.size();
                selectedScriptName = availableScripts.get(index);
                updated = true;
                sleep(300);
            }

            if (gamepad1.dpad_down) {
                index = (index + 1) % availableScripts.size();
                selectedScriptName = availableScripts.get(index);
                updated = true;
                sleep(300);
            }

            if (updated) {
                telemetry.clear();
                telemetry.addLine("=== SELECT AUTO SCRIPT ===");
                telemetry.addData("Selected:", selectedScriptName);
                telemetry.addLine("Use D-Pad UP/DOWN to change");
                telemetry.update();
            }

            idle();
        }

        waitForStart();

        if (selectedScriptName != null && !selectedScriptName.isEmpty()) {
            telemetry.clear();
            telemetry.addLine("Running Auto Script: " + selectedScriptName);
            telemetry.update();

            String script = scripts.get(selectedScriptName);
            String[] lines = script.split("\n");

            for (String line : lines) {
                if (!opModeIsActive()) break;
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("//")) continue;
                path(trimmed.split(" "));
            }
        } else {
            telemetry.addLine("No Auto Script selected!");
            telemetry.update();
        }

        telemetry.addLine("Auto Complete");
        telemetry.update();
    }

    public void path(String[] command) throws InterruptedException {
        switch (command[0].toLowerCase()) {
            case "move":
                Commands.move(command[1], command[2], driveBase, this, telemetry);
                break;
            case "turn":
                Commands.turn(command[1], command[2], driveBase, this, telemetry);
                break;
            case "strafe":
                Commands.strafe(command[1], command[2], driveBase, this, telemetry);
                break;
            case "diagonal":
                Commands.diagonal(command[1], command[2], driveBase, this, telemetry);
                break;
            case "servo":
                Commands.servo(command[1], command[2], this, telemetry);
                break;
            case "wait":
                Commands.waitMillis(command[1], this);
                break;
            case "log":
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < command.length; i++) sb.append(command[i]).append(" ");
                Commands.log(sb.toString(), telemetry);
                break;
            case "motor":
                Commands.motorMove(command[1], command[2], command[3], this, telemetry);
                break;
            case "stop":
                requestOpModeStop();
                break;
            default:
                telemetry.addLine("Unknown command: " + command[0]);
        }
        telemetry.update();
    }
}
