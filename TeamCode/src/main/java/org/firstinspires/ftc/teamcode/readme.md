---

# 📜 **FTC AutoScriptor System**

This project lets you create **text-based autonomous routines** for your FTC robot.
The `AutoScriptor` loads and runs scripts written in a **simple line-by-line command language**.

---

## ✅ **How it works**

1. **Scripts**

    * All scripts are stored in `AutoScript.java` as `public static final String`.
    * Each script is a plain-text block of **commands**, one per line.

   Example:

   ```java
   public static final String SQUARE =
       "move forward 10\n" +
       "turn right 90\n" +
       "stop\n";
   ```

2. **Selector**

    * When you run the `AutoScriptor` OpMode, you select your script on the driver station using **D-Pad UP/DOWN** before pressing PLAY.
    * Telemetry shows the current selection.

3. **Executor**

    * The selected script runs line-by-line.
    * Each line is split by spaces: the first word is the command, the rest are arguments.

---

## 📜 **Supported Commands**

| Command    | Format                      | What it does                              |                              |                              |                        |
| ---------- | --------------------------- | ----------------------------------------- | ---------------------------- | ---------------------------- | ---------------------- |
| `move`     | \`move \[forward            | backward] \[inches]\`                     | Moves robot forward/backward |                              |                        |
| `turn`     | \`turn \[left               | right] \[degrees]\`                       | Turns robot by degrees       |                              |                        |
| `strafe`   | \`strafe \[left             | right] \[inches]\`                        | Strafes robot left/right     |                              |                        |
| `diagonal` | \`diagonal \[right\_forward | left\_forward                             | right\_backward              | left\_backward] \[inches]\`  | Moves robot diagonally |
| `servo`    | `servo [name] [position]`   | Sets a servo to a position `0.0`–`1.0`    |                              |                              |                        |
| `motor`    | \`motor \[name] \[move      | power                                     | stop] \[value]\`             | Controls an individual motor |                        |
| `wait`     | `wait [ms]`                 | Waits for X milliseconds                  |                              |                              |                        |
| `log`      | `log [message]`             | Prints a message to telemetry             |                              |                              |                        |
| `stop`     | `stop`                      | Ends the auto run immediately             |                              |                              |                        |
| `func`     | `func [name]`               | Marks a function start (handled manually) |                              |                              |                        |
| `call`     | `call [name]`               | Calls a function block                    |                              |                              |                        |
| `func end` | `func end`                  | Ends a function definition                |                              |                              |                        |

---

## 🔡 **Scripting Tips**

* Commands are **case-insensitive**.
* You can add **comments** by starting a line with `//`. These lines are ignored.
* Empty lines are ignored.
* You can build reusable blocks with `func` and `call`.

**Example:**

```plaintext
// This script drives a square
move forward 10
turn right 90
move forward 10
turn right 90
move forward 10
turn right 90
move forward 10
turn right 90
stop
```

---

## 🛠️ **Adding Scripts**

Use `build_autoscript_java.py` to generate `AutoScript.java` from a plain text file.

1. Write your scripts in `scripts.txt`:

   ```plaintext
   #Square
   move forward 10
   turn right 90
   stop

   #SlideUp
   func code
   slide up 20
   wait 2000
   slide down 20
   func end
   call code
   stop
   ```

2. Run:

   ```bash
   python build_autoscript_java.py
   ```

3. `AutoScript.java` is regenerated with all your scripts.

---

## ⚙️ **Troubleshooting**

* 🚫 **Unknown command?** Check spelling — the parser matches only known commands.
* ⚙️ **Motors not moving?** Make sure your `DriveBase` hardware names match your configuration.
* ✅ **TeleOp stuck on selector?** Use `D-Pad` to select, then press **PLAY**.

---

## 📌 **How to Extend**

* Add new commands in `Commands.java` — follow the same pattern.
* Use `path()` in `AutoScriptor` to hook in your new command.
* Add test scripts and regenerate!

---

## 🏆 **Good Practices**

* Keep scripts short and clear.
* Test new scripts with caution — always lift your robot when testing movements!
* Use telemetry logs for debugging.

---

## 📚 **Files**

| File                       | Purpose                                        |
| -------------------------- | ---------------------------------------------- |
| `AutoScript.java`          | Stores scripts as Java constants               |
| `AutoScriptor.java`        | Loads, selects, and runs scripts               |
| `Commands.java`            | Defines each robot command                     |
| `DriveBase.java`           | Drives the mecanum drivetrain                  |
| `MotorController.java`     | Controls individual motors                     |
| `build_autoscript_java.py` | Generates `AutoScript.java` from `scripts.txt` |

---

## 🔑 **License**

This template is open — adapt it for your team’s FTC codebase!

---
