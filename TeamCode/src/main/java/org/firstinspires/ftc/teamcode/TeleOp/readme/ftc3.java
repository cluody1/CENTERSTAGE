package org.firstinspires.ftc.teamcode.TeleOp.readme;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ftc3")
public class ftc3 extends LinearOpMode {

    private DcMotor right;
    private DcMotor back_right;
    private DcMotor left;
    private DcMotor back_left;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        float pivot;
        float vertical;
        float horizontal;

        right = hardwareMap.get(DcMotor.class, "right");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        left = hardwareMap.get(DcMotor.class, "left");
        back_left = hardwareMap.get(DcMotor.class, "back_left");

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        right.setDirection(DcMotor.Direction.REVERSE);
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.
        back_right.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // Put loop blocks here.
                vertical = -gamepad1.right_stick_y;
                horizontal = gamepad1.right_stick_x;
                pivot = gamepad1.left_stick_x;
                right.setPower(-pivot + (vertical - horizontal));
                back_right.setPower(-pivot + vertical + horizontal);
                left.setPower(pivot + vertical + horizontal);
                back_left.setPower(pivot + (vertical - horizontal));
                telemetry.update();
            }
        }
    }
}