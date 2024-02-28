package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    public HardwareMap hardwareMap;

    public DcMotor right = null;
    public DcMotor back_right = null;
    public DcMotor left = null;
    public DcMotor back_left = null;
    public DcMotor armLeft = null;
    public DcMotor armRight = null;
    public Servo gripper = null;
    public Servo wrist = null;

    public final double armSetpointClosedPosition = 1.0;
    public final double armSetpointOpenPosition = 0.5;
    public final double wristSetpointUpPosition = 1.0;
    public final double wristSetpointDownPosition = 0.3;
    public final int armHomePosition = 0;
    public final int armIntakePosition = 5;
    public final int armScorePosition = 600;

    public Robot(){

        right = hardwareMap.get(DcMotor.class, "right");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        left = hardwareMap.get(DcMotor.class, "left");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        armLeft  = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");
        gripper = hardwareMap.get(Servo.class, "gripper");
        wrist = hardwareMap.get(Servo.class, "wrist");

    }

    public double armSetpointOpenPosition(int distance) {
        gripper.setPosition(armSetpointOpenPosition);
        return 0;
    }
    public double armSetpointClosedPosition(int distance) {
        gripper.setPosition(armSetpointClosedPosition);
        return 0;
    }
}
