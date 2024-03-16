package org.firstinspires.ftc.teamcode.TeleOp.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="vidoe", group="Iterative Opmode")

public class vidoe extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor right = null;
    private DcMotor back_right = null;
    private DcMotor left = null;
    private DcMotor back_left = null;
    private DcMotor armLeft = null;
    private DcMotor armRight = null;
    private DcMotor Middel = null;
    private Servo gripper = null;
    private Servo wrist = null;
    private Servo plane = null;

    private boolean manualMode = false;
    private double armSetpoint = 0.0;

    private final double armManualDeadband = 0.03;

    private final double gripperClosedPosition = 1.0;
    private final double gripperOpenPosition = 0.5;
    private final double wristUpPosition = 1.0;
    private final double wristDownPosition = 0.0;

    private final int armHomePosition = 0;
    private final int armIntakePosition = 10;
    private final int armScorePosition = 600;
    private final int armShutdownThreshold = 5;

    float pivot;
    float vertical;
    float horizontal;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        right = hardwareMap.get(DcMotor.class, "right");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        left = hardwareMap.get(DcMotor.class, "left");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        armLeft  = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");
        Middel  = hardwareMap.get(DcMotor.class, "Middel");
        gripper = hardwareMap.get(Servo.class, "gripper");
        wrist = hardwareMap.get(Servo.class, "wrist");
        plane = hardwareMap.get(Servo.class, "plane");


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

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();

        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setTargetPosition(armHomePosition);
        armRight.setTargetPosition(armHomePosition);
        armLeft.setPower(0.7);
        armRight.setPower(0.7);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double manualArmPower;

        //DRIVE
        vertical = gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        pivot = gamepad1.right_stick_x;
        right.setPower(-pivot + (vertical - horizontal));
        back_right.setPower(-pivot + vertical + horizontal);
        left.setPower(pivot + vertical + horizontal);
        back_left.setPower(pivot + (vertical - horizontal));
        telemetry.update();

        if (gamepad1.dpad_up){
            plane.setPosition(1);
        }
        else {
            plane.setPosition(0);
        }

        if (gamepad1.dpad_left){
            Middel.setPower(1);
        }
        else {
            Middel.setPower(0);
        }

        if (gamepad1.dpad_right){
            Middel.setPower(1);
        }
        else {
            Middel.setPower(0);
        }

        //ARM & WRIST
        manualArmPower = gamepad1.right_trigger - gamepad1.left_trigger;
        if (Math.abs(manualArmPower) > armManualDeadband) {
            if (!manualMode) {
                armLeft.setPower(0.0);
                armRight.setPower(0.0);
                armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                manualMode = true;
            }
            armLeft.setPower(manualArmPower);
            armRight.setPower(manualArmPower);
        }
        else {
            if (manualMode) {
                armLeft.setTargetPosition(armLeft.getCurrentPosition());
                armRight.setTargetPosition(armRight.getCurrentPosition());
                armLeft.setPower(0.7);
                armRight.setPower(0.7);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                manualMode = false;
            }

            //preset buttons
            if (gamepad1.a) {
                armLeft.setTargetPosition(armHomePosition);
                armRight.setTargetPosition(armHomePosition);
                armLeft.setPower(0.4);
                armRight.setPower(0.4);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristUpPosition);
            }
            else if (gamepad1.b) {
                armLeft.setTargetPosition(armIntakePosition);
                armRight.setTargetPosition(armIntakePosition);
                armLeft.setPower(0.7);
                armRight.setPower(0.7);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristDownPosition);
            }
            else if (gamepad1.y) {
                armLeft.setTargetPosition(armScorePosition);
                armRight.setTargetPosition(armScorePosition);
                armLeft.setPower(0.5);
                armRight.setPower(0.5);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristUpPosition);
            }
        }

        //Re-zero encoder button
        if (gamepad1.x) {
            armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLeft.setPower(0.0);
            armRight.setPower(0.0);
            manualMode = false;
        }

        //Watchdog to shut down motor once the arm reaches the home position
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

        //GRIPPER
        if (gamepad1.left_bumper) {
            gripper.setPosition(gripperOpenPosition);
        }
        if (gamepad1.right_bumper) {
            gripper.setPosition(gripperClosedPosition);
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Manual Power", manualArmPower);
        telemetry.addData("Arm Pos:",

                "left = " +
                        ((Integer)armLeft.getCurrentPosition()).toString() +
                        ", right = " +
                        ((Integer)armRight.getCurrentPosition()).toString());
        telemetry.addData("Arm Pos:",
                "left = " +
                        ((Integer)armLeft.getTargetPosition()).toString() +
                        ", right = " +
                        ((Integer)armRight.getTargetPosition()).toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}