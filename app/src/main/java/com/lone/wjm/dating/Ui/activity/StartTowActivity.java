package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lone.wjm.dating.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class StartTowActivity extends Activity {
    private ViewPager startPager;
    private List<View>imgList;
    private boolean autoScroll = true;
    private int curentItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tow_start);
        startPager = (ViewPager) findViewById(R.id.startPager);

        LayoutInflater inflater = LayoutInflater.from(this);
        imgList = new ArrayList<View>();
        imgList.add(inflater.inflate(R.layout.splash1,null));
        imgList.add(inflater.inflate(R.layout.splash2,null));
        imgList.add(inflater.inflate(R.layout.splash3,null));
        imgList.add(inflater.inflate(R.layout.splash4,null));
        startPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgList.size();
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imgList.get(position));
                return imgList.get(position);
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
               container.removeView(imgList.get(position));
            }
        });
        startPager.setCurrentItem(curentItem);
        startPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                curentItem = position;
                if(position == imgList.size()){
                    startActivity(new Intent(StartTowActivity.this,MainActivity.class));
                    finish();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 1:
                        autoScroll = false;
                        break;
                    case 2:
                        autoScroll = true;
                        break;
                    case 3:
                        autoScroll = true;
                        break;
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    try {
                        Thread.sleep(3000);
                        if (autoScroll) {
                            Message msg = new Message();
                            msg.what = 1;
                            myHandler.sendMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);

            }
        }).start();
    }

    private Handler myHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg == null) {
                return;
            }
            switch (msg.what) {
                case 1:
                    if (++curentItem == imgList.size()) {
                        startActivity(new Intent(StartTowActivity.this,MainActivity.class));
                        finish();
                    }
                    startPager.setCurrentItem(curentItem);
                    break;

            }
        };
    };
}
