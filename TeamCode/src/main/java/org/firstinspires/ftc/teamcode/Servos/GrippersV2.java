package org.firstinspires.ftc.teamcode.Servos;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Other.DriversHubAndFTCDashboard;

public class GrippersV2 {
    private HardwareMap hwMap;
    private Telemetry telem;
    private DriversHubAndFTCDashboard hub_and_dash = new DriversHubAndFTCDashboard();
    private Servo left;
    private Servo right;
    public double LEFT_OPEN = 0.60;
    public double LEFT_CLOSED = 0.45;
    public double RIGHT_OPEN = 0.6;
    public double RIGHT_CLOSED = 0.75;
    public int STATE = -1;
    public void init(HardwareMap hardwareMap, Telemetry telemetry){
        this.hwMap = hardwareMap;
        this.telem = telemetry;
        hub_and_dash.init(this.telem);
        left = hwMap.get(Servo.class,"left_gripper");
        right = hwMap.get(Servo.class, "right_gripper");
    }
    public void open_grippers(){
        left.setPosition(LEFT_OPEN);
        right.setPosition(RIGHT_OPEN);
        this.STATE = -1;
    }
    public void close_grippers(){
        left.setPosition(LEFT_CLOSED);
        right.setPosition(RIGHT_CLOSED);
        this.STATE = 1;
    }
    public void setLeftPosition(double position){
        left.setPosition(position);
    }
    public void setRightPosition(double position){
        right.setPosition(position);
    }
    public void hold(){
        setLeftPosition(get_needed_left_pos());
        setRightPosition(get_needed_right_pos());
    }
    private double get_needed_left_pos(){
        if(STATE == 1){
            return LEFT_CLOSED;
        }
        else {
            return LEFT_OPEN;
        }
    }
    private double get_needed_right_pos(){
        if(STATE == 1){
            return RIGHT_CLOSED;
        }
        else {
            return RIGHT_OPEN;
        }
    }
    public void toggle_grippers_state(){
        if (this.STATE == 1){
            open_grippers();
        }
        else {
            close_grippers();
        }
    }
    public void log_gripper_positions(){
        hub_and_dash.add_line("Left Gripper");
        hub_and_dash.update_all();
    }
}
