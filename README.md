# Byte Forge FTC Robot Controller

This repository contains the FTC robot code for Byte Forge Robotics. It includes:

- Core drivetrain and encoder helper classes  
- Support for 4-motor drive systems (mecanum or omniwheels)  
- Utilities to work with motor encoders for precise movement and corrections  
- Placeholders for autonomous and teleop OpModes

---

## Project Structure

```

TeamCode/
src/main/java/org/firstinspires/ftc/teamcode/
encoder/
Encoder.java           # Helper class to manage motor encoders
drive/
DriveBase.java         # Wrapper for 4 motor drivetrain with encoders
opmodes/
(Your autonomous and teleop OpModes go here)
build.gradle              # Gradle build configuration

````

---

## Getting Started

1. **Set up your hardware** in the `DriveBase` constructor:

   Adjust `ticksPerRev` and `wheelDiameterInches` according to your motors and wheels.

2. **Create and reset your drive system** in your OpMode:

   DriveBase drive = new DriveBase(hardwareMap);
   drive.resetEncoders();


3. **Drive and control the robot**:

   Use `drive.setPower()` to control motors. Use encoder readings from `drive.getAverageInches()` or individual encoders for corrections.

4. **Implement your movement logic** with optional correction loops based on encoder feedback.

---

## Example Usage Snippet

```java
DriveBase drive = new DriveBase(hardwareMap);
drive.resetEncoders();

double targetInches = 24;

while (opModeIsActive() && drive.getAverageInches() < targetInches) {
    drive.setPower(0.5, 0.5, 0.5, 0.5);

    // Optional: Add code here to correct drift by adjusting individual motor powers
}

drive.setPower(0, 0, 0, 0);
```

---

## Notes & Tips

* The project **does not include fully automated correction algorithms** — it provides utilities to build those.
* You are encouraged to **implement PID or proportional control loops** for precise autonomous control.
* Keep your motor and encoder configurations consistent with your robot’s hardware configuration.
* Remember to **test your code thoroughly in the driver station and (if possible) simulator** before competitions.

---

## Contributing

* Fork this repo, make changes in branches, and submit pull requests.
* Follow FTC code style and naming conventions.
* Document your additions clearly.

---

## Team Contacts

Byte Forge Robotics
[GitHub Repository](https://github.com/ByteForgeRobotics/FtcRobotController)
Contact your team lead for access and questions.
