package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.R;

import java.util.Calendar;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public class Filter_Activity extends Activity implements View.OnClickListener {

    public static TextView city;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private RadioButton rb_mode1;
    private RadioButton rb_mode2;
    private RadioButton rb_mode3;
    private EditText et_filter_time;
    private Calendar c = null;
    private Dialog dialog = null;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        sp = ((MyApplication)getApplication()).getSp();
        initView();
    }

    private void initView() {
        city = (TextView) findViewById(R.id.city);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rb_mode1 = (RadioButton) findViewById(R.id.rb_mode1);
        rb_mode2 = (RadioButton) findViewById(R.id.rb_mode2);
        rb_mode3 = (RadioButton) findViewById(R.id.rb_mode3);
        et_filter_time = (EditText) findViewById(R.id.et_filter_time);

        city.setText(getIntent().getStringExtra("city"));
        city.setOnClickListener(this);
        et_filter_time.setOnClickListener(this);
    }
    public Dialog showDialog(){
        c = Calendar.getInstance();
        dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        et_filter_time.setText(year + "年" + (month+1) + "月" + dayOfMonth + "日");
                    }
                },
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH)// 传入天数
    );
        return dialog;
    }
    @Override
    public void onClick(View v) {
        //TODO筛选界面 事件处理
        switch (v.getId()) {
            case R.id.et_filter_time:
                showDialog().show();
                break;

        }
    }
public void back(View v){
    finish();
}
    public void confirm(View v){
        //TODO 确定进行筛选
    }
}
