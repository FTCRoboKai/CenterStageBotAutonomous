package org.firstinspires.ftc.teamcode.Motors;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Other.DriversHubAndFTCDashboard;
import org.firstinspires.ftc.teamcode.Motors.SlidesV2;

public class SlidesArmV2 {
    public int UP_POSITION = -900;
    public int DOWN_POSITION = 0;
    public int HOVER_POSITION = -155;
    public double POWER = 0.9;
    public int CURRENT_POSITION = 0;

    private HardwareMap hwMap;
    private Telemetry telem;
    private DcMotor arm_motor;
    private SlidesV2 slides = new SlidesV2();
    private DriversHubAndFTCDashboard hub_and_dash = new DriversHubAndFTCDashboard();
    public void init(HardwareMap hardwareMap, Telemetry telemetry){
        this.hwMap = hardwareMap;
        this.telem = telemetry;
        hub_and_dash.init(this.telem);
        slides.init(hardwareMap, telemetry);
        arm_motor = hwMap.get(DcMotor.class, "arm");
    }


    public void run_arm_to(int position){
        double current_position = arm_motor.getCurrentPosition();
        if (current_position > position){
            arm_motor.setPower(-POWER);
            arm_motor.setTargetPosition(position);
            arm_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (current_position < position){
            arm_motor.setPower(POWER);
            arm_motor.setTargetPosition(position);
            arm_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        CURRENT_POSITION = position;
    }
    public void arm_down(boolean wait){
        if (wait == true) {
            while (slides.are_slides_busy()){
                pass();
            }
        }
        run_arm_to(DOWN_POSITION);
    }
    public void arm_up(boolean wait) {
        if (wait == true) {
            while (slides.are_slides_busy()){
                pass();
            }
        }
        run_arm_to(UP_POSITION);
        CURRENT_POSITION = UP_POSITION;
    }
    public void arm_hover(boolean wait){
        if (wait == true) {
            while (slides.are_slides_busy()){
                pass();
            }
        }
        run_arm_to(HOVER_POSITION);
        CURRENT_POSITION = HOVER_POSITION;
    }

    public boolean is_arm_busy(){
        return arm_motor.isBusy();
    }
    public void end_reset(){
        arm_down(true);
    }
    public void log_arm_position(){
        hub_and_dash.add_line("Arm Position: " + arm_motor.getCurrentPosition());
        hub_and_dash.update_all();
    }
    public void pass(){
        return;
    }
    public void arm_hold(){
        run_arm_to(CURRENT_POSITION);
    }
}