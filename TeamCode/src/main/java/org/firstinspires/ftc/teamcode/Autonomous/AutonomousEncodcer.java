package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutonomousEncodcer", group = "Autonomous")
public class AutonomousEncodcer extends LinearOpMode{

    Robot robot = new Robot();

    @Override
    public void runOpMode() throws InterruptedException{

        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();

        //rev HD Hex Motor 40:1 Spur Gearbox 1 Rotation = 150 rpm
        //rev HD Hex Motor 40:1 Spur Gearbox Stall Torque: (4.2 Nm)

        //rev Core hex 1 Rotation = 125 rpm
        //rev Core hex  Torque: 3.2 N-m

        //Smart Robot Servo  Default Angular Range = 270 , max = -280
        //Smart Robot Servo  speed =  0.14 s/60° (at 6V)


        while (opModeIsActive()){
            moveDistance(0.5, 150);
            sleep(100);
            armMove(0.5, 90);
            sleep(100);
            gripper(0.5, 90);
            sleep(100);
            gripper(0.5, -90);
            sleep(100);
            armMove(1, -90);
            moveDistance(0.5, -38);
            moveDistance(0.7, 150*2);
            armMove(0.5, 150*2);

        }

    }


    public void moveDistance(double poewr, int distance){

        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.right.setTargetPosition(distance);
        robot.back_right.setTargetPosition(distance);
        robot.left.setTargetPosition(distance);
        robot.back_left.setTargetPosition(distance);

        robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        moveForward(poewr);

        while (robot.right.isBusy()&& robot.back_right.isBusy()&& robot.left.isBusy()&& robot.back_left.isBusy()){

        }

        stopMotor(poewr);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.right.setTargetPosition(distance);
        robot.back_right.setTargetPosition(distance);
        robot.left.setTargetPosition(distance);
        robot.back_left.setTargetPosition(distance);

        robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        turnLeft(poewr);

        while (robot.right.isBusy()&& robot.back_right.isBusy()&& robot.left.isBusy()&& robot.back_left.isBusy()){

        }

        stopMotor(poewr);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.right.setTargetPosition(distance);
        robot.back_right.setTargetPosition(distance);
        robot.left.setTargetPosition(distance);
        robot.back_left.setTargetPosition(distance);

        robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        moveForward(poewr);

        while (robot.right.isBusy()&& robot.back_right.isBusy()&& robot.left.isBusy()&& robot.back_left.isBusy()){

        }

        stopMotor(poewr);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.right.setTargetPosition(distance);
        robot.back_right.setTargetPosition(distance);
        robot.left.setTargetPosition(distance);
        robot.back_left.setTargetPosition(distance);

        robot.right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        turnRight(poewr);

        while (robot.right.isBusy()&& robot.back_right.isBusy()&& robot.left.isBusy()&& robot.back_left.isBusy()){

        }

        stopMotor(poewr);
        robot.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }



    public void moveForward(double poewr){
        robot.right.setPower(poewr);
        robot.back_right.setPower(poewr);
        robot.left.setPower(poewr);
        robot.back_left.setPower(poewr);
    }

    public void turnLeft(double poewr){
        robot.right.setPower(-poewr);
        robot.back_right.setPower(-poewr);
        robot.left.setPower(poewr);
        robot.back_left.setPower(poewr);
    }

    public void turnRight(double poewr){
        robot.right.setPower(poewr);
        robot.back_right.setPower(poewr);
        robot.left.setPower(-poewr);
        robot.back_left.setPower(-poewr);
    }

    public void stopMotor(double power) {
        robot.right.setPower(power);
        robot.back_right.setPower(power);
        robot.left.setPower(power);
        robot.back_left.setPower(power);
    }


    public void armMove(double poewr, int distance){

        robot.armLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.armRight.setDirection(DcMotor.Direction.REVERSE);

        robot.armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.armLeft.setTargetPosition(distance);
        robot.armRight.setTargetPosition(distance);

        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Intake(poewr);

        while (robot.armLeft.isBusy()&& robot.armRight.isBusy()){

        }

        stopMotor();
        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        robot.armLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.armRight.setDirection(DcMotor.Direction.REVERSE);

        robot.armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.armLeft.setTargetPosition(distance);
        robot.armRight.setTargetPosition(distance);

        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Home(poewr);

        while (robot.armLeft.isBusy()&& robot.armRight.isBusy()){

        }

        stopMotor();
        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        robot.armLeft.setDirection(DcMotor.Direction.FORWARD);
        robot.armRight.setDirection(DcMotor.Direction.REVERSE);

        robot.armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.armLeft.setTargetPosition(distance);
        robot.armRight.setTargetPosition(distance);

        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Score(poewr);

        while (robot.armLeft.isBusy()&& robot.armRight.isBusy()){

        }

        stopMotor();
        robot.armLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }



    public void Home(double poewr){
        robot.armLeft.setTargetPosition(robot.armHomePosition);
        robot.armRight.setTargetPosition(robot.armHomePosition);
        robot.armLeft.setPower(1 * poewr);
        robot.armRight.setPower(1 * poewr);
        robot.armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.wrist.setPosition(robot.wristSetpointUpPosition);
    }

    public void Intake(double poewr){
        robot.armLeft.setTargetPosition(robot.armIntakePosition);
        robot.armRight.setTargetPosition(robot.armIntakePosition);
        robot.armLeft.setPower(1 * poewr);
        robot.armRight.setPower(1 * poewr);
        robot.armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot. armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.wrist.setPosition(robot.wristSetpointDownPosition);
    }

    public void Score(double poewr){
        robot.armLeft.setTargetPosition(robot.armScorePosition);
        robot.armRight.setTargetPosition(robot.armScorePosition);
        robot.armLeft.setPower(1 * poewr);
        robot.armRight.setPower(1 * poewr);
        robot.armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.wrist.setPosition(robot.wristSetpointUpPosition);
    }

    public void stopMotor(){
        robot.armLeft.setPower(0);
        robot.armRight.setPower(0);

    }

    public void gripper(double poewr, int distance){

        robot.gripper.setPosition(robot.armSetpointOpenPosition(distance));
        robot.gripper.setPosition(robot.armSetpointClosedPosition(distance));


        Open(poewr);


        Close(poewr);

    }

    public void Open(double poewr){
        robot.gripper.setPosition(poewr);
    }

    public void Close(double poewr){
        robot.gripper.setPosition(poewr);
    }

}


