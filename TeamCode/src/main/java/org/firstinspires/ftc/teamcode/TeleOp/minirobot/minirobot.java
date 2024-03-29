package org.firstinspires.ftc.teamcode.TeleOp.minirobot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="minirobot", group="Iterative Opmode")

public class minirobot extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor right = null;
    private DcMotor back_right = null;
    private DcMotor left = null;
    private DcMotor back_left = null;
    private DcMotor arm = null;
    private Servo armLeft = null;
    private Servo armRight = null;
    private Servo wrist = null;

    private boolean manualMode = false;
    private double armSetpoint = 0.0;

    private final double armManualDeadband = 0.03;

    private final double gripperClosedPosition = 1.0;
    private final double gripperOpenPosition = 0.5;
    private final double wristUpPosition = 0.5;
    private final double wristDownPosition = -0.3;

    private final int armHomePosition = 1;
    private final int armIntakePosition = 20;
    private final int armScorePosition = 600;
    private final int armShutdownThreshold = 5;


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
        arm  = hardwareMap.get(DcMotor.class, "arm");
        armLeft = hardwareMap.get(Servo.class, "armLeft");
        armRight = hardwareMap.get(Servo.class, "armRight");
        wrist = hardwareMap.get(Servo.class, "wrist");

        right.setDirection(DcMotor.Direction.REVERSE);
        back_right.setDirection(DcMotor.Direction.REVERSE);


        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPower(0.0);

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

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(armHomePosition);
        arm.setPower(0.5);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double manualArmPower;

        //DRIVE
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        right.setPower(frontLeftPower);
        left.setPower(backLeftPower);
        back_right.setPower(frontRightPower);
        back_left.setPower(backRightPower);

        //ARM & WRIST
        manualArmPower = gamepad1.left_trigger - gamepad1.right_trigger;
        if (Math.abs(manualArmPower) > armManualDeadband) {
            if (!manualMode) {
                arm.setPower(0.0);
                arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                manualMode = true;
            }
            arm.setPower(manualArmPower);
        }
        else {
            if (manualMode) {
                arm.setTargetPosition(arm.getCurrentPosition());
                arm.setPower(0.7);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                manualMode = false;
            }

            //preset buttons
            if (gamepad1.a) {
                arm.setTargetPosition(-armHomePosition);
                arm.setPower(0.4);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristUpPosition);
            }
            else if (gamepad1.b) {
                arm.setTargetPosition(-armIntakePosition);
                arm.setPower(0.7);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristDownPosition);
            }

        }

        //Re-zero encoder button
        if (gamepad1.x) {
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setPower(0.0);
            manualMode = false;
        }

        //Watchdog to shut down motor once the arm reaches the home position
        if (!manualMode &&
                arm.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
                arm.getTargetPosition() <= armShutdownThreshold &&
                arm.getCurrentPosition() <= armShutdownThreshold
        ) {
            arm.setPower(0.0);
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        //GRIPPER
        if (gamepad1.left_bumper) {
            armLeft.setPosition(gripperOpenPosition);
        }

        else {
            armLeft.setPosition(gripperClosedPosition);
        }

        if (gamepad1.right_bumper) {
            armRight.setPosition(gripperOpenPosition);
        }

        else {
            armRight.setPosition(gripperClosedPosition);
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Manual Power", manualArmPower);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}