package org.firstinspires.ftc.teamcode.TeleOp.minirobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Servo", group="Iterative Opmode")

public class SERVO extends OpMode

{

    private Servo wrist = null;
    private DcMotor arm = null;

    private final double wristUpPosition = 0.1;
    private final double wristDownPosition = 0;

    private final int armHomePosition = 1;
    private final int armIntakePosition = 20;
    private final double armManualDeadband = 0.03;

    private boolean manualMode = false;

    private final int armShutdownThreshold = 5;

    private ElapsedTime runtime = new ElapsedTime();


    public void init() {

        telemetry.addData("Status", "Initialized");


        wrist = hardwareMap.get(Servo.class, "wrist");
        arm = hardwareMap.get(DcMotor.class, "arm");

        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPower(0.0);

        telemetry.addData("Status", "Initialized");

    }

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

    @Override
    public void loop() {

        double manualArmPower;

        //ARM & WRIST
        manualArmPower = gamepad1.left_trigger - gamepad1.right_trigger;
        if (Math.abs(manualArmPower) > armManualDeadband) {
            if (!manualMode) {
                arm.setPower(0.0);
                arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                manualMode = true;
            }
            arm.setPower(manualArmPower);
        } else {
            if (manualMode) {
                arm.setTargetPosition(arm.getCurrentPosition());
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                manualMode = false;
            }

            //preset buttons
            if (gamepad1.a) {
                arm.setTargetPosition(-armHomePosition);
                arm.setPower(0.5);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristUpPosition);
            }

            else if (gamepad1.b) {
                arm.setTargetPosition(-armIntakePosition);
                arm.setPower(0.5);
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

        if (!manualMode &&
                arm.getMode() == DcMotor.RunMode.RUN_TO_POSITION &&
                arm.getTargetPosition() <= armShutdownThreshold &&
                arm.getCurrentPosition() <= armShutdownThreshold
        ) {
            arm.setPower(0.0);
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Manual Power", manualArmPower);
        telemetry.addData("wrist dowm", wristDownPosition);
        telemetry.addData("wrist up", wristUpPosition);
        telemetry.addData("Arm Pos:",
                ", arm = " +
                        ((Integer)arm.getTargetPosition()).toString());
        telemetry.addData("Arm Pos:",
                ", arm = " +
                        ((Integer)arm.getCurrentPosition()).toString());

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */

    @Override
    public void stop() {
    }
}
