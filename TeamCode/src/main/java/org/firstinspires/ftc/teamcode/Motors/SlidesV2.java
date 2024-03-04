package org.firstinspires.ftc.teamcode.Motors;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Other.DriversHubAndFTCDashboard;

public class SlidesV2 {
    public int IN_POSITION = 20;
    public int OUT_POSITION = -1800;
    public double POWER = 0.5;

    private HardwareMap hwMap;
    private Telemetry telem;
    private DcMotor slides_motor;
    private DriversHubAndFTCDashboard hub_and_dash = new DriversHubAndFTCDashboard();
    public void init(HardwareMap hardwareMap, Telemetry telemetry){
        this.hwMap = hardwareMap;
        this.telem = telemetry;
        hub_and_dash.init(this.telem);
        slides_motor = hardwareMap.get(DcMotor.class, "slides");
    }


    public void run_slides_to(int position){
        double current_position = slides_motor.getCurrentPosition();
        if (current_position > position){
            slides_motor.setPower(-POWER);
            slides_motor.setTargetPosition(position);
            slides_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (current_position < position){
            slides_motor.setPower(POWER);
            slides_motor.setTargetPosition(position);
            slides_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public void slides_out(){
        run_slides_to(OUT_POSITION);
    }
    public void slides_in() {
        run_slides_to(IN_POSITION);
    }

    public boolean are_slides_busy(){
        return slides_motor.isBusy();
    }
    public void end_reset(){
        run_slides_to(IN_POSITION);
        while (are_slides_busy()){
            pass();
        }
    }
    public void pass(){
        return;
    }
    public void log_slides_position(){
        telem.addLine("Slides Position: " + slides_motor.getCurrentPosition());
        telem.update();
    }
}
