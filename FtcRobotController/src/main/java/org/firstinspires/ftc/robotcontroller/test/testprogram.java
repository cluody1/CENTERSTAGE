package org.firstinspires.ftc.robotcontroller.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="testprogram", group="Iterative Opmode")
public class testprogram extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor right = null;
    private DcMotor back_right = null;
    private DcMotor left = null;
    private DcMotor back_left = null;
    private DcMotor armLeft = null;
    private DcMotor armRight = null;
    private Servo gripper = null;
    private Servo wrist = null;

    private boolean manualMode = false;
    private double armSetpoint = 0.0;
    private final double armManualDeadband = 0.03;
    private final double armSetpointClosedPosition = 1.0;
    private final double armSetpointOpenPosition = 0.5;
    private final double wristSetpointUpPosition = 1.0;
    private final double wristSetpointDownPosition = 0.3;
    private final int armHomePosition = 0;
    private final int armIntakePosition = 5;
    private final int armScorePosition = 600;
    private final int armShutdownThreshold = 5;

    float pivot;
    float vertical;
    float horizontal;

    double powerFactor = 0.25;
    double powerFactor1 = 0.5;
    double powerFactor2 = 0.75;


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        right = hardwareMap.get(DcMotor.class, "right");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        left = hardwareMap.get(DcMotor.class, "left");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        armLeft  = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");
        gripper = hardwareMap.get(Servo.class, "gripper");
        wrist = hardwareMap.get(Servo.class, "wrist");

        right.setDirection(DcMotor.Direction.REVERSE);
        back_right.setDirection(DcMotor.Direction.REVERSE);

        armLeft.setDirection(DcMotor.Direction.FORWARD);
        armRight.setDirection(DcMotor.Direction.REVERSE);
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armLeft.setPower(0.0);
        armRight.setPower(0.0);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setTargetPosition(armHomePosition);
        armRight.setTargetPosition(armHomePosition);
        armLeft.setPower(0.5 * powerFactor);
        armRight.setPower(0.5 * powerFactor1);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        pivot = gamepad1.right_stick_x;
        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;

        right.setPower((-pivot + (vertical - horizontal)) * powerFactor2);
        back_right.setPower((-pivot + vertical + horizontal) * powerFactor2);
        left.setPower((pivot + vertical + horizontal) * powerFactor2);
        back_left.setPower((pivot + (vertical - horizontal)) * powerFactor2);

        double manualArmPower = gamepad1.right_trigger - gamepad1.left_trigger;
        if (Math.abs(manualArmPower) > armManualDeadband) {
            if (!manualMode) {
                armLeft.setPower(0.0);
                armRight.setPower(0.0);
                armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                manualMode = true;
            }
            armLeft.setPower(manualArmPower * powerFactor);
            armRight.setPower(manualArmPower * powerFactor1);
        } else {
            if (manualMode) {
                armLeft.setTargetPosition(armLeft.getCurrentPosition());
                armRight.setTargetPosition(armRight.getCurrentPosition());
                armLeft.setPower(0.5 * powerFactor);
                armRight.setPower(0.5 * powerFactor1);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                manualMode = false;
            }
            if (gamepad1.a) {
                armLeft.setTargetPosition(armHomePosition);
                armRight.setTargetPosition(armHomePosition);
                armLeft.setPower(0.5 * powerFactor);
                armRight.setPower(0.5 * powerFactor1);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristSetpointUpPosition);
            } else if (gamepad1.b) {
                armLeft.setTargetPosition(armIntakePosition);
                armRight.setTargetPosition(armIntakePosition);
                armLeft.setPower(0.5 * powerFactor);
                armRight.setPower(0.5 * powerFactor1);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristSetpointDownPosition);
            } else if (gamepad1.y) {
                armLeft.setTargetPosition(armScorePosition);
                armRight.setTargetPosition(armScorePosition);
                armLeft.setPower(0.5 * powerFactor);
                armRight.setPower(0.5 * powerFactor1);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristSetpointUpPosition);
            }
        }

        if (gamepad1.start) {
            armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            manualMode = false;
        }

        if (!manualMode &&
                armLeft.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
                armLeft.getTargetPosition() <= armShutdownThreshold &&
                armLeft.getCurrentPosition() <= armShutdownThreshold
        ) {
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            gripper.setPosition(armSetpointOpenPosition);
        } else {
            gripper.setPosition(armSetpointClosedPosition);
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Manual Power", manualArmPower);
        telemetry.addData("Arm Pos:", "left = " + armLeft.getCurrentPosition() + ", right = " + armRight.getCurrentPosition());
        telemetry.addData("Arm Pos:", "left = " + armLeft.getTargetPosition() + ", right = " + armRight.getTargetPosition());
    }

    @Override
    public void stop(){
}
}