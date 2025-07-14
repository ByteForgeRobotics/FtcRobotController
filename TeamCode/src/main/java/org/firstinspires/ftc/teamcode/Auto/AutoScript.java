package org.firstinspires.ftc.teamcode.Auto;

/**
 * Defines the raw auto script lines used by the AutoScriptor to parse and execute autonomous functions.
 *
 * <p>The script lines are a simplified scripting language with commands like move, turn, slide, wait, call, etc.
 * Functions are declared with "func <name>" and ended with "endfunc".</p>
 *
 * Example:
 * <pre>
 * func square
 * move forward 10
 * turn right 90
 * ...
 * endfunc
 *
 * call square
 * </pre>
 */
public class AutoScript {
    /**
     * The array of script lines for autonomous routines.
     */
    public static final String[] LINES = {
            "// Example Auto Script",
            "func square",
            "move forward 10",
            "turn right 90",
            "move forward 10",
            "turn right 90",
            "move forward 10",
            "turn right 90",
            "move forward 10",
            "turn right 90",
            "endfunc",

            "func slideUp",
            "slide up 20",
            "wait 2000",
            "slide down 20",
            "endfunc",

            "call square",
            "call slideUp",
            "stop"
    };
}
