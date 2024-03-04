package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Other.DriversHubAndFTCDashboard;

public class MecDriveFunctions {
    private DriversHubAndFTCDashboard hub_and_dash = new DriversHubAndFTCDashboard();
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    public void init(HardwareMap hwMap, Telemetry telem){
        frontLeftMotor = hwMap.get(DcMotor.class, "fl");
        frontRightMotor = hwMap.get(DcMotor.class, "fr");
        backLeftMotor = hwMap.get(DcMotor.class, "bl");
        backRightMotor = hwMap.get(DcMotor.class, "br");
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hub_and_dash.init(telem);
        hub_and_dash.add_line("Initialized Drive Motors.");
        hub_and_dash.update_all();
    }

    public void drive(double y, double x, double rx) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (-y + x + rx) / denominator;
        double backLeftPower = (-y - x + rx) / denominator;
        double frontRightPower = (y + x + rx) / denominator;
        double backRightPower = (y - x + rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
    public void log_drive_motor_positions(){
        hub_and_dash.add_line("Front Left Motor Position: " + frontLeftMotor.getCurrentPosition());
        hub_and_dash.add_line("Front Right Motor Position: " + frontRightMotor.getCurrentPosition());
        hub_and_dash.add_line("Back Left Motor Position: " + backLeftMotor.getCurrentPosition());
        hub_and_dash.add_line("Back Right Motor Position: " + backRightMotor.getCurrentPosition());
        hub_and_dash.update_all();
    }
    public void set_individual_power(DcMotor motor, double power) throws Exception{
        if(motor == this.frontLeftMotor){
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeftMotor.setPower(power);
        }
        else if(motor == this.frontRightMotor){
            frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightMotor.setPower(power);
        }
        else if(motor == this.backLeftMotor){
            backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftMotor.setPower(power);
        }
        else if(motor == this.backRightMotor){
            backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightMotor.setPower(power);
        }
        else {
            throw new Exception("Invalid motor.");
        }
    }
    public boolean driveIsInactive(){
        if (this.frontLeftMotor.isBusy() == false && this.frontRightMotor.isBusy() == false && this.backLeftMotor.isBusy() == false && this.backRightMotor.isBusy() == false){
            return true;
        }
        else {
            return false;
        }
    }
    public void moveForward(int distance, double power){
        int fl = frontLeftMotor.getCurrentPosition();
        int fr = frontRightMotor.getCurrentPosition();
        int bl = backLeftMotor.getCurrentPosition();
        int br = backRightMotor.getCurrentPosition();
        frontLeftMotor.setTargetPosition(fl - distance);
        frontRightMotor.setTargetPosition(fr + distance);
        backLeftMotor.setTargetPosition(bl - distance);
        backRightMotor.setTargetPosition(br + distance);
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(power);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (driveIsInactive() == false){
            //Wait for movement to complete
            pass();
        }
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void moveBackward(int distance, double power){
        int fl = frontLeftMotor.getCurrentPosition();
        int fr = frontRightMotor.getCurrentPosition();
        int bl = backLeftMotor.getCurrentPosition();
        int br = backRightMotor.getCurrentPosition();
        frontLeftMotor.setTargetPosition(fl + distance);
        frontRightMotor.setTargetPosition(fr - distance);
        backLeftMotor.setTargetPosition(bl + distance);
        backRightMotor.setTargetPosition(br - distance);
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(-power);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (driveIsInactive() == false){
            //Wait for movement to complete
            pass();
        }
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void pass(){
        return;
    }
}