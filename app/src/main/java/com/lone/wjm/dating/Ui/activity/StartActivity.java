package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.R;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class StartActivity extends Activity {
    private ImageView img;
    Animation animation;
    private Handler mHandler;
    private SharedPreferences sp;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_satrt);
        img = (ImageView) findViewById(R.id.startimg);

        sp = ((MyApplication)getApplication()).getSp();
        mEditor = ((MyApplication)getApplication()).getEditor();

        //透明度变化 0.0是透明 1.0是最清晰  float单位
        animation = new AlphaAnimation(0.0f, 1.0f);
        //是否处于停留状态
        animation.setFillAfter(true);
        //设定时间
        animation.setDuration(5000);
        //开始动画
        img.startAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
                if (msg.what==1){
                    if(!sp.getBoolean("isfirst",false)){
                    startActivity(new Intent(StartActivity.this,StartTowActivity.class));
                        mEditor.putBoolean("isfirst",true);
                        mEditor.commit();
                    finish();
                    }else{
                        startActivity(new Intent(StartActivity.this,MainActivity.class));
                        finish();
                    }
                }
            }
        };
    }
    }

