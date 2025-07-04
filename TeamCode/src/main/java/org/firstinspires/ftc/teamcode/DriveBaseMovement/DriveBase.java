package org.firstinspires.ftc.teamcode.DriveBaseMovement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * basic drive base set up
 */
public class DriveBase {
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    /**
     * sets up the motors
     * @param hardwareMap
     */
    public DriveBase(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
    }

    /**
     * sets the power of the motors
     * @param fl
     * @param fr
     * @param bl
     * @param br
     */
    public void setPower(double fl, double fr, double bl, double br) {
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);
    }

    /**
     * resets the encoders
     */
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * get the positions based on the 4 wheels locations
     * @return
     */
    public int getAveragePosition() {
        return (frontLeft.getCurrentPosition() + frontRight.getCurrentPosition() +
                backLeft.getCurrentPosition() + backRight.getCurrentPosition()) / 4;
    }
}
