package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.lone.wjm.dating.R;

/**
 * Created by: Lone on 2016/6/2.
 * Contact: 4951048@qq.com
 */
public class MyOrderActivity extends Activity {
    private ListView lv_our_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_order);
        initView();

    }

    private void initView() {
        lv_our_order = (ListView) findViewById(R.id.lv_our_order);
    }

    public void back(View v){
        finish();
    }
}
