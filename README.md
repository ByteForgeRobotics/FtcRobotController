---

## 📂 **Package:** `org.firstinspires.ftc.teamcode.Auto`

---

## 📄 **Class: `AutoScript`**

### **Purpose**

Holds the predefined autonomous commands as an array of strings.
Acts as a basic script source for the robot to read and execute.

### **Key Field**

| Field   | Type       | Description                                                                     |
| ------- | ---------- | ------------------------------------------------------------------------------- |
| `LINES` | `String[]` | An array of command lines that define an autonomous path or reusable functions. |

### **Example Script**

```java
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
    "call square",
    "stop"
};
```

**What it does:**

* Defines a `square` function that makes the robot move in a square.
* Calls the `square` function once.
* Stops the OpMode after execution.

---

## 📄 **Class: `AutoScriptor`**

### **Purpose**

Runs the autonomous commands defined in `AutoScript`.
Parses commands, handles reusable functions, and executes supported robot actions.

### **Key Components**

| Member      | Type                        | Description                               |
| ----------- | --------------------------- | ----------------------------------------- |
| `functions` | `Map<String, List<String>>` | Stores parsed custom functions for reuse. |

**Main Method:**

| Method                   | Description                                                                                                                                         |
| ------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------- |
| `runOpMode()`            | Entry point — runs once when the OpMode starts. Parses each line from `AutoScript.LINES`, stores functions, and calls `path()` to execute commands. |
| `path(String[] command)` | Parses and executes each supported command (`move`, `turn`, `strafe`, `servo`, `wait`, `log`, `call`, `stop`). Handles function calls recursively.  |

**Supported Commands:**

* `move [direction] [distance]`
* `turn [direction] [angle]`
* `strafe [direction] [distance]`
* `servo [name] [position]`
* `wait [ms]`
* `log [message]`
* `call [functionName]`
* `stop`

---

## 📄 **Class: `Commands`**

### **Purpose**

Implements low-level actions that can be called by `AutoScriptor`.

### **Methods**

| Method                                                           | Description                                                                     |
| ---------------------------------------------------------------- | ------------------------------------------------------------------------------- |
| `move(String direction, String distance, Telemetry telemetry)`   | Displays a log for moving in the specified direction for a distance.            |
| `turn(String direction, String angle, Telemetry telemetry)`      | Displays a log for turning in the specified direction by an angle.              |
| `strafe(String direction, String distance, Telemetry telemetry)` | Displays a log for strafing sideways in the specified direction for a distance. |
| `servo(String name, String position, Telemetry telemetry)`       | Displays a log for setting a servo to a specific position.                      |
| `waitMillis(String ms, Telemetry telemetry)`                     | Busy-waits for the specified number of milliseconds.                            |
| `log(String message, Telemetry telemetry)`                       | Logs a custom message to the telemetry.                                         |

---

## ⚙️ **How It Works**

1. `AutoScript` holds the sequence of instructions.
2. `AutoScriptor` runs each line:

   * Stores reusable functions (`func` ... `endfunc`).
   * Runs immediate commands.
   * Supports `call` to reuse functions.
3. `Commands` defines the primitive robot actions — you can expand these to actually move hardware instead of just logging.

---

