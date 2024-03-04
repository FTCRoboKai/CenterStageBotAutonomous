package org.firstinspires.ftc.teamcode.Other;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriversHubAndFTCDashboard {
    Telemetry telem;
    Telemetry dashboard_telem;
    Telemetry multi;
    Boolean created_multi = false;
    public void init(Telemetry telemetry){
        this.telem = telemetry;
        this.dashboard_telem = returnDashboardTelemetry();
    }
    public FtcDashboard returnDashboard(){
        return FtcDashboard.getInstance();
    }
    public Telemetry returnDashboardTelemetry(){
        return returnDashboard().getTelemetry();
    }
    public void add_data(String caption, Object value){
        telem.addData(caption, value);
        dashboard_telem.addData(caption, value);
    }
    public void add_line(String lineCaption){
        telem.addLine(lineCaption);
        dashboard_telem.addLine(lineCaption);
    }
    public void update_main(){
        telem.update();
    }
    public void update_dashboard(){
        dashboard_telem.update();
    }

    public void update_multi(){
        multi.update();
    }
    public void update_all(){
        update_main();
        update_dashboard();
        if(this.created_multi) {
            update_multi();
        }
    }
    public MultipleTelemetry createMultipleTelemetry(){
        MultipleTelemetry mult = new MultipleTelemetry(telem, dashboard_telem);
        this.multi = mult;
        this.created_multi = true;
        return mult;
    }
    public void multi_print(String caption){
        MultipleTelemetry multi = createMultipleTelemetry();
        multi.addLine(caption);
    }
    public void clear(){
        telem.clearAll();
        dashboard_telem.clearAll();
        if(this.created_multi){
            multi.clearAll();
        }
    }
}

