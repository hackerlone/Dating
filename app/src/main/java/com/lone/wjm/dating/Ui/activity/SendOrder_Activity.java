package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Prosenter.SendOrderProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.ISendOrderView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/2.
 * Contact: 4951048@qq.com
 */
public class SendOrder_Activity extends Activity implements View.OnClickListener,ISendOrderView {
    private RelativeLayout rl_login_title;
    private EditText et_order_title;

    private RadioGroup rg_date_sex;
    private EditText et_order_amount;
    private EditText et_order_tip;
    private RadioGroup rg_date_type;
    private EditText et_filter_time;
    private EditText et_date_location;
    private EditText et_date_remark;
    private Button btn_confirm;
    private Calendar c = null;
    private Dialog dialog = null;
    private SharedPreferences sp;
    private String sex;
    private String fangshi;
    private SendOrderProsenter mSendProsenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order);
        initView();
        sp = ((MyApplication)getApplication()).getSp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rg_date_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_date_sex1) {
                    sex = "男";
                } else if (checkedId == R.id.rb_date_sex2) {
                    sex = "女";
                } else if(checkedId == R.id.rb_date_sex3){
                    sex = "不限";
                }else{
                    sex = "null";
                }
            }
        });
        rg_date_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_date_type1) {
                    fangshi = "我请客";
                } else if (checkedId == R.id.rb_date_type2) {
                    fangshi = "对方请客";
                } else if(checkedId == R.id.rb_date_type3){
                    fangshi = "AA制";
                } else{
                    fangshi = "null";
                }
            }
        });
    }

    //TODO  提交生成约单
    public void confirm(View v) {
        String ysex = sex;
        String yfangshi = fangshi;
        String ymiaoshu = et_order_title.getText().toString();
        String yxiaofei = et_order_amount.getText().toString();
        String ychefei = et_order_tip.getText().toString();
        String yshijian = et_filter_time.getText().toString();
        String ydidian = et_filter_time.getText().toString();
        String ybeizhu = et_date_remark.getText().toString();
        String userObjectId = sp.getString("objectId","null");
        Map<String,String> yueDanmap = new HashMap<String, String>();
        yueDanmap.put("ymiaoshu",ymiaoshu);
        yueDanmap.put("ysex",ysex);
        yueDanmap.put("yfangshi",yfangshi);
        yueDanmap.put("yxiaofei",yxiaofei);
        yueDanmap.put("ychefei",ychefei);
        yueDanmap.put("yshijian",yshijian);
        yueDanmap.put("ydidian",ydidian);
        yueDanmap.put("ybeizhu",ybeizhu);
        yueDanmap.put("userObjectId",userObjectId);
        mSendProsenter = new SendOrderProsenter(this);
        mSendProsenter.sendOrder(yueDanmap);

    }

    private void initView() {
        rl_login_title = (RelativeLayout) findViewById(R.id.rl_login_title);
        et_order_title = (EditText) findViewById(R.id.et_order_title);
        rg_date_sex = (RadioGroup) findViewById(R.id.rg_date_sex);
        et_order_amount = (EditText) findViewById(R.id.et_order_amount);
        et_order_tip = (EditText) findViewById(R.id.et_order_tip);
        rg_date_type = (RadioGroup) findViewById(R.id.rg_date_type);
        et_filter_time = (EditText) findViewById(R.id.et_filter_time);
        et_date_location = (EditText) findViewById(R.id.et_date_location);
        et_date_remark = (EditText) findViewById(R.id.et_date_remark);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        et_filter_time.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_filter_time:
                showDialog().show();
                break;
        }
    }

    public void back(View v) {
        finish();
    }

    public Dialog showDialog() {
        c = Calendar.getInstance();
        dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        et_filter_time.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    }
                },
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH)// 传入天数
        );
        return dialog;
    }

    @Override
    public void sendOrder(boolean isSend) {

    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(SendOrder_Activity.this, msg, Toast.LENGTH_SHORT).show();
    }
}

