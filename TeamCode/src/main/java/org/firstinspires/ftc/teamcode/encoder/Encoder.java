package org.firstinspires.ftc.teamcode.encoder;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * helper class for built-in motor encoders
 */
public class Encoder {

    private DcMotor motor;

    // How many ticks per motor revolution
    private double ticksPerRev;

    // wheel in inches
    private double wheelDiameterInches;

    public Encoder(HardwareMap hardwareMap, String motorName, double ticksPerRev, double wheelDiameterInches)
    {
        this.motor = hardwareMap.get(DcMotor.class, motorName);
        this.ticksPerRev = ticksPerRev;
        this.wheelDiameterInches = wheelDiameterInches;

        reset();
    }

    /**
     * Get the current encoder ticks.
     */
    public int getTicks()
    {
        return motor.getCurrentPosition();
    }

    /**
     * Convert current encoder position to inches.
     */
    public double getInches()
    {
        return ticksToInches(getTicks());
    }

    /**
     * Reset encoder to zero.
     */
    public void reset()
    {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Helper to convert ticks to inches.
     */
    public double ticksToInches(int ticks)
    {
        double wheelCircumference = Math.PI * wheelDiameterInches;
        return (ticks / ticksPerRev) * wheelCircumference;
    }

    public int correctPosition(int curentPostition, int targetPostion)
    {
        if (curentPostition == targetPostion)
        {
            return 0;
        }
        else if (curentPostition <= targetPostion)
        {
            return targetPostion-curentPostition;
        }
        else
        {
            return curentPostition - targetPostion;
        }
    }
}
