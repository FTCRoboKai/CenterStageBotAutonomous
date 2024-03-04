package org.firstinspires.ftc.teamcode.Drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Motors.SlidesArmV2;
import org.firstinspires.ftc.teamcode.Motors.SlidesV2;
import org.firstinspires.ftc.teamcode.Other.DriversHubAndFTCDashboard;
import org.firstinspires.ftc.teamcode.Servos.GrippersV2;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@TeleOp
public class JackAndEliRobotV3 extends OpMode {
    private ElapsedTime buttonTimer= new ElapsedTime();
    private org.firstinspires.ftc.teamcode.Drive.MecDriveFunctions drive = new org.firstinspires.ftc.teamcode.Drive.MecDriveFunctions();
    private DriversHubAndFTCDashboard hub_and_dashboard = new DriversHubAndFTCDashboard();
    private SlidesV2 slides = new SlidesV2();
    private GrippersV2 grippers = new GrippersV2();
    private SlidesArmV2 arm = new SlidesArmV2();

    enum RobotState {
        DRIVE,
        WAIT_FOR_OVER_PIXEL,
        GRIPPERS_CLOSE,
        ARM_UP_AND_SLIDES_OUT,
        ERROR
    }
    public int DEV_MODE = 0;
    private RobotState robotState = RobotState.DRIVE;

    @Override
    public void init() {
        drive.init(hardwareMap, telemetry);
        hub_and_dashboard.init(telemetry);
        slides.init(hardwareMap, telemetry);
        grippers.init(hardwareMap, telemetry);
        arm.init(hardwareMap, telemetry);
        grippers.open_grippers();
        slides.slides_in();
        arm.arm_hover(true);
    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = -gamepad1.left_stick_x; // Counteract imperfect strafing, reversed because of the position of the back motors
        double rx = -gamepad1.right_stick_x; //This is reversed for our turning
        drive.drive(y, x, rx);
        if (gamepad1.x && buttonTimer.seconds() > 0.3) {
            switch (robotState) {
                case DRIVE:
                    grippers.open_grippers();
                    arm.arm_down(false);
                    buttonTimer.reset();
                    setRobotState(RobotState.WAIT_FOR_OVER_PIXEL);
                    break;
                case WAIT_FOR_OVER_PIXEL:
                    grippers.close_grippers();
                    sleep(1.5);
                    arm.arm_hover(false);
                    buttonTimer.reset();
                    setRobotState(RobotState.GRIPPERS_CLOSE);
                    break;
                case GRIPPERS_CLOSE:
                    arm.arm_up(false);
                    slides.slides_out();
                    buttonTimer.reset();
                    setRobotState(RobotState.ARM_UP_AND_SLIDES_OUT);
                    break;
                case ARM_UP_AND_SLIDES_OUT:
                    grippers.open_grippers();
                    drive.moveBackward(25, 0.4);
                    slides.slides_in();
                    arm.arm_hover(true);
                    buttonTimer.reset();
                    setRobotState(RobotState.DRIVE);
                    break;
            }
        }
        else if(gamepad1.options){
            toggle_dev_mode();
        }
        grippers.hold();
        arm.arm_hold();
        logState();
        dev_mode_if_activated();
        if(gamepad1.left_bumper && gamepad1.right_bumper){
            //Stop program
            killProgram();
        }
    }
    public void stop(){
        grippers.open_grippers();
        slides.end_reset();
        arm.end_reset();
    }
    public void setRobotState(RobotState robotState){
        this.robotState = robotState;
    }
    public RobotState getRobotState(){
        return this.robotState;
    }
    public void toggle_dev_mode(){
        switch (this.DEV_MODE){
            case 0:
                this.DEV_MODE = 1;
                hub_and_dashboard.clear();
                hub_and_dashboard.add_line("Dev Mode Activated");
                hub_and_dashboard.update_all();
                break;
            case 1:
                this.DEV_MODE = 0;
                hub_and_dashboard.clear();
                hub_and_dashboard.add_line("Dev Mode Deactivated");
                hub_and_dashboard.update_all();
                break;
        }
    }
    public void dev_mode_if_activated(){
        if(this.DEV_MODE == 1){
            drive.log_drive_motor_positions();
            slides.log_slides_position();
            arm.log_arm_position();
            grippers.log_gripper_positions();
            hub_and_dashboard.add_line("Current State: " + robotState.toString());
            hub_and_dashboard.update_all();
        }
    }
    public void sleep(double seconds) {
        try {
            Thread.sleep((long) seconds * 1000);
        } catch (InterruptedException e) {
            hub_and_dashboard.add_line("Sleep failed.");
            return;
        }
    }
    public void killProgram(){
        requestOpModeStop();
    }
    public void logState(){
        telemetry.addLine("Current State: " + getRobotState());
    }
}


