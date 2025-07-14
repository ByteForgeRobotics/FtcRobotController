package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import java.util.*;

/**
 * Autonomous OpMode that parses and runs auto script functions defined in AutoScript.LINES.
 *
 * <p>Features a gamepad selector to choose which auto function to run before start.</p>
 *
 * Usage:
 * <ul>
 * <li>Deploy as an FTC autonomous OpMode.</li>
 * <li>On init, it parses functions from AutoScript.LINES.</li>
 * <li>User selects a function using gamepad D-pad up/down.</li>
 * <li>On start, executes the selected function line-by-line.</li>
 * </ul>
 */
@Autonomous(name = "AutoScriptor")
public class AutoScriptor extends LinearOpMode {

    /** Map of function names to list of command lines */
    static Map<String, List<String>> functions = new HashMap<>();

    private ColorSensor colorSensor;
    private String selectedFunc = "";
    private DriveBase driveBase;

    /**
     * Main OpMode execution method.
     * Initializes hardware, parses functions, allows user to select function, then executes it.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Loading Auto Scripts...");
        telemetry.update();

        driveBase = new DriveBase(hardwareMap);
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensorName");

        // Parse functions
        List<String> availableFuncs = new ArrayList<>();
        List<String> lines = Arrays.asList(AutoScript.LINES);
        Iterator<String> iterator = lines.iterator();

        while (iterator.hasNext()) {
            String line = iterator.next().trim();
            if (line.isEmpty() || line.startsWith("//")) continue;

            String[] parts = line.split(" ");
            if (parts[0].equalsIgnoreCase("func") && parts.length > 1) {
                String funcName = parts[1];
                List<String> funcLines = new ArrayList<>();
                while (iterator.hasNext()) {
                    String funcLine = iterator.next().trim();
                    if (funcLine.equalsIgnoreCase("endfunc")) break;
                    if (!funcLine.isEmpty() && !funcLine.startsWith("//")) {
                        funcLines.add(funcLine);
                    }
                }
                functions.put(funcName, funcLines);
                availableFuncs.add(funcName);
            }
        }

        if (availableFuncs.isEmpty()) {
            telemetry.addLine("No Auto Scripts found!");
            telemetry.update();
            waitForStart();
            return;
        }

        int index = 0;
        selectedFunc = availableFuncs.get(index);

        // Function selection loop before start
        while (!isStarted() && !isStopRequested()) {
            boolean updated = false;

            if (gamepad1.dpad_up) {
                index = (index - 1 + availableFuncs.size()) % availableFuncs.size();
                selectedFunc = availableFuncs.get(index);
                updated = true;
                sleep(300); // debounce
            }

            if (gamepad1.dpad_down) {
                index = (index + 1) % availableFuncs.size();
                selectedFunc = availableFuncs.get(index);
                updated = true;
                sleep(300); // debounce
            }

            if (updated) {
                telemetry.clear();
                telemetry.addLine("=== SELECT AUTO SCRIPT ===");
                telemetry.addData("Selected:", selectedFunc);
                telemetry.addLine("Use D-Pad UP/DOWN to change");
                telemetry.update();
            }

            idle();
        }

        waitForStart();

        // Run selected function commands
        if (selectedFunc != null && !selectedFunc.isEmpty()) {
            telemetry.clear();
            telemetry.addLine("Running Auto Script: " + selectedFunc);
            telemetry.update();

            for (String line : functions.get(selectedFunc)) {
                if (!opModeIsActive()) break;
                path(line.split(" "));
            }
        } else {
            telemetry.addLine("No Auto Script selected!");
            telemetry.update();
        }

        telemetry.addLine("Auto Complete");
        telemetry.update();
    }

    /**
     * Parses a command line and dispatches it to the corresponding Commands method.
     *
     * @param command The split command line as a string array.
     * @throws InterruptedException if sleep/wait is interrupted.
     */
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
