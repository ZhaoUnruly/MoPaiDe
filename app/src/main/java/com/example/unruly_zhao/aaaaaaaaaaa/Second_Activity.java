package com.example.unruly_zhao.aaaaaaaaaaa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Unruly_Zhao on 2017/9/13.
 */

public class Second_Activity extends Activity {

    private BackMain backMain, backMain2;
    private boolean flags;
    public int state;
    private boolean mFlag;
    private Handler mHandler = new Handler();
    private boolean mFlags;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaa);

        init();

    }

    private void init() {

        // TODO: 2017/8/29 五分钟无人操作回主页面
        //backMain = new BackMain(1000 * 60 * 5, 1000, this);

        backMain = new BackMain(1000 * 2, 1000, this);

        backMain2 = new BackMain(1000 * 10, 1000, this);
    }

    @Override
    protected void onResume() {

        timeStart();
        super.onResume();

    }

    //region 无操作 返回主页

    private void timeStart() {

        new Handler(getMainLooper()).post(new Runnable() {

            @Override
            public void run() {

                backMain.start();

            }
        });
    }

    /**
     * 主要的方法，重写dispatchTouchEvent
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //获取触摸动作，如果ACTION_UP，计时开始。
            case MotionEvent.ACTION_UP:
                backMain.start();
                break;
            //否则其他动作计时取消
            default:
                backMain.cancel();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onPause() {
        super.onPause();
        backMain.cancel();
    }

    class BackMain extends CountDownTimer {

        private Context context;

        /**
         * 参数 millisInFuture       倒计时总时间（如60S，120s等）
         * 参数 countDownInterval    渐变时间（每次倒计1s）
         */
        public BackMain(long millisInFuture, long countDownInterval, Context context) {
            super(millisInFuture, countDownInterval);
            this.context = context;
        }

        // 计时完毕时触发
        @Override
        public void onFinish() {


            if (!mFlag) {

                init_dialog();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!mFlags) {

                            startActivity(new Intent(Second_Activity.this, MainActivity.class));

                        }

                    }
                }, 2000);

            } else {

                startActivity(new Intent(Second_Activity.this, MainActivity.class));

            }
            Toast.makeText(context, "喜欢你都那么久那么久了", Toast.LENGTH_LONG).show();

       /*


        //context.startActivity(new Intent(context, MainActivity.class));*/
        }

        // 计时过程显示
        @Override
        public void onTick(long millisUntilFinished) {

        }


    }

    private void init_dialog() {

        mFlag = true;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Second_Activity.this);
        alertDialogBuilder.setTitle("喜欢你都那么久那么久了");//设置标题
        alertDialogBuilder.setMessage("喜欢你的原因，并不是你长的好不好看的原因。而是在特殊的时间给我不一样的感觉"); //设置内容
        alertDialogBuilder.setIcon(R.mipmap.ic_launcher);   //自定义图标
        alertDialogBuilder.setCancelable(false);           //设置是否能点击，对话框的其他区域取消

        alertDialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {     //设置其确认按钮和监听事件
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler(getMainLooper()).post(new Runnable() {


                    @Override
                    public void run() {

                        backMain2.start();
                        mFlags = true;
                        // 这句话该何去何从

                    }
                });

            }
        });

        alertDialogBuilder.create();       //创建对话框
        alertDialogBuilder.show();         //显示对话框


    }


}
