// تعريف المكتبات اللازمة
package org.firstinspires.ftc.teamcode.transolate;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

// تحديد أن هذا الكود لوضع التحكم التليفزيوني
@TeleOp(name="Starter Bot 2024", group="Iterative Opmode")

// بداية تعريف الفصل الرئيسي
public class transolate extends OpMode
{
    // تعريف المتغيرات اللازمة

    // متغير لتتبع الوقت المنقضي منذ بدء تشغيل الروبوت
    private ElapsedTime runtime = new ElapsedTime();

    // متغيرات لتخزين مراجع المحركات والخدمات
    private DcMotor right = null; // محرك الجانب الأيمن الخلفي
    private DcMotor back_right = null; // محرك الجانب الأيمن الأمامي
    private DcMotor left = null; // محرك الجانب الأيسر الخلفي
    private DcMotor back_left = null; // محرك الجانب الأيسر الأمامي
    private DcMotor armLeft = null; // محرك الذراع الأيسر
    private DcMotor armRight = null; // محرك الذراع الأيمن
    private Servo gripper = null; // المحرك الخدمي لفتحة الإمساك
    private Servo wrist = null; // المحرك الخدمي للمعصم

    // متغير يحدد ما إذا كانت الذراع تعمل بالوضع اليدوي أو التلقائي
    private boolean manualMode = false;

    // قيمة الهدف لموضع الذراع
    private double armSetpoint = 0.0;

    // نطاق التحكم اليدوي للذراع
    private final double armManualDeadband = 0.03;

    // وضعيات الفتحة والمعصم
    private final double gripperClosedPosition = 0.5; // موقف الفتحة عند الإغلاق
    private final double gripperOpenPosition = 0.5; // موقف الفتحة عند الفتح
    private final double wristUpPosition = 0.5; // موقف المعصم عند الرفع
    private final double wristDownPosition = 0.0; // موقف المعصم عند الخفض

    // مواقع محركات الذراع المحددة مسبقًا
    private final int armHomePosition = 0; // الوضع الافتراضي للذراع
    private final int armIntakePosition = 10; // موضع الذراع لالتقاط الكرة
    private final int armScorePosition = 600; // موضع الذراع لإطلاق الكرة
    private final int armShutdownThreshold = 5; // الحد الأدنى لإيقاف تشغيل المحرك

    // قيم للتحكم في الحركة
    float pivot; // قيمة الدوران
    float vertical; // القيمة الرأسية للحركة
    float horizontal; // القيمة الأفقية للحركة

    // كود يتم تنفيذه مرة واحدة عندما يتم تشغيل الروبوت
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // تعريف وتهيئة المحركات والخدمات
        right = hardwareMap.get(DcMotor.class, "right");
        back_right = hardwareMap.get(DcMotor.class, "back_right");
        left = hardwareMap.get(DcMotor.class, "left");
        back_left = hardwareMap.get(DcMotor.class, "back_left");
        armLeft  = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");
        gripper = hardwareMap.get(Servo.class, "gripper");
        wrist = hardwareMap.get(Servo.class, "wrist");

        // تعيين اتجاهات الحركة للمحركات (تم تغيير العلامات)
        right.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.FORWARD);
        left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);
        armLeft.setDirection(DcMotor.Direction.FORWARD);
        armRight.setDirection(DcMotor.Direction.REVERSE);

        // إعادة ضبط مواقع الذراع وتهيئتها
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setTargetPosition(armHomePosition);
        armRight.setTargetPosition(armHomePosition);
        armLeft.setPower(0.5);
        armRight.setPower(0.5);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Status", "Initialized");
    }

    // كود يتم تنفيذه مرة واحدة عندما يتم الضغط على زر التشغيل
    @Override
    public void start() {
        runtime.reset();

        // إعادة ضبط مواقع الذراع وتهيئتها للوضع الافتراضي
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setTargetPosition(armHomePosition);
        armRight.setTargetPosition(armHomePosition);
        armLeft.setPower(0.5);
        armRight.setPower(0.5);
        armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // كود يتم تنفيذه بشكل متكرر أثناء تشغيل الروبوت
    @Override
    public void loop() {

        double leftPower;
        double rightPower;
        double manualArmPower;

        // التحكم في حركة الروبوت
        vertical = -gamepad1.left_stick_y;
        horizontal = gamepad1.left_stick_x;
        pivot = gamepad1.right_stick_x;
        // تحكم الحركة الأمامية والخلفية باستخدام الذراع الأيسر (right_stick_y)
        right.setPower(pivot + (vertical - horizontal));
        back_right.setPower(pivot + (vertical + horizontal));
        left.setPower(-pivot + (vertical + horizontal));
        back_left.setPower(-pivot + (vertical - horizontal));
        // تحكم الدوران والانعطاف باستخدام الذراع الأيمن (left_stick_x)
        armLeft.setPower(-horizontal);
        armRight.setPower(horizontal);

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
                armLeft.setPower(0.5);
                armRight.setPower(0.5);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                manualMode = false;
            }

            //preset buttons
            if (gamepad1.a) {
                armLeft.setTargetPosition(armHomePosition);
                armRight.setTargetPosition(armHomePosition);
                armLeft.setPower(0.5);
                armRight.setPower(0.5);
                armLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                wrist.setPosition(wristUpPosition);
            }
            else if (gamepad1.b) {
                armLeft.setTargetPosition(armIntakePosition);
                armRight.setTargetPosition(armIntakePosition);
                armLeft.setPower(0.5);
                armRight.setPower(0.5);
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
        if (gamepad1.start) {
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
        if (gamepad1.left_bumper || gamepad1.right_bumper) {
            gripper.setPosition(gripperOpenPosition);
        }
        else {
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
}