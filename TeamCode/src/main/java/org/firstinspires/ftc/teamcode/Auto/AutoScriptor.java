package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.*;

@Autonomous(name = "AutoScriptor")
public class AutoScriptor extends LinearOpMode {

    static Map<String, List<String>> functions = new HashMap<>();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addLine("Waiting for start...");
        telemetry.update();

        waitForStart();

        List<String> lines = Arrays.asList(AutoScript.LINES);

        Iterator<String> iterator = lines.iterator();

        while (opModeIsActive() && iterator.hasNext()) {
            String line = iterator.next().trim();
            if (line.length() == 0 || line.startsWith("//")) continue;

            String[] parts = line.split(" ");
            if (parts[0].equals("func")) {
                String funcName = parts[1];
                List<String> funcLines = new ArrayList<>();
                while (iterator.hasNext()) {
                    String funcLine = iterator.next().trim();
                    if (funcLine.equals("endfunc")) break;
                    if (funcLine.length() != 0 && !funcLine.startsWith("//")) {
                        funcLines.add(funcLine);
                    }
                }
                functions.put(funcName, funcLines);
                telemetry.addLine("Saved function: " + funcName);
            } else {
                path(parts);
            }

            telemetry.update();
        }

        telemetry.addLine("Auto Done");
        telemetry.update();
    }

    public void path(String[] command) throws InterruptedException {
        switch (command[0]) {
            case "move":
                Commands.move(command[1], command[2], telemetry);
                break;

            case "turn":
                Commands.turn(command[1], command[2], telemetry);
                break;

            case "strafe":
                Commands.strafe(command[1], command[2], telemetry);
                break;

            case "servo":
                Commands.servo(command[1], command[2], telemetry);
                break;

            case "wait":
                Commands.waitMillis(command[1], telemetry);
                break;

            case "log":
                // Manual join instead of Arrays.copyOfRange()
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < command.length; i++) {
                    sb.append(command[i]);
                    if (i < command.length - 1) sb.append(" ");
                }
                Commands.log(sb.toString(), telemetry);
                break;

            case "call":
                String funcName = command[1];
                if (functions.containsKey(funcName)) {
                    for (String line : functions.get(funcName)) {
                        path(line.split(" "));
                    }
                } else {
                    telemetry.addLine("Function not found: " + funcName);
                }
                break;

            case "stop":
                requestOpModeStop();
                break;

            default:
                telemetry.addLine("Unknown command: " + command[0]);
        }
    }
}
