package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lone.wjm.dating.Prosenter.RegisterProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.IRegisterView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class Register_Activity extends Activity implements IRegisterView{
    EditText etregisternum;
    EditText etregistercode;
    EditText etregisterpwd;
    EditText et_register_username;
    public static Button getCode;
    private RegisterProsenter mProsenter;
    private Map<String,String>userInfomap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        userInfomap = new HashMap<String, String>();
    }
    public void getcode(View v) {
        userInfomap.put("phoneNum",etregisternum.getText().toString());
        mProsenter = new RegisterProsenter(this,userInfomap);
        mProsenter.getCode();
    }
    public void register(View v) {
        userInfomap.put("phoneNum",etregisternum.getText().toString());
        userInfomap.put("username",et_register_username.getText().toString());
        userInfomap.put("password",etregisterpwd.getText().toString());
        userInfomap.put("phoneCode",etregistercode.getText().toString());
        mProsenter = new RegisterProsenter(this,userInfomap);
        mProsenter.register();

    }
    private void initView() {
        etregisternum = (EditText) findViewById(R.id.et_register_num);
        etregistercode = (EditText) findViewById(R.id.et_register_code);
        etregisterpwd = (EditText) findViewById(R.id.et_register_pwd);
        et_register_username = (EditText) findViewById(R.id.et_register_username);
        getCode = (Button) findViewById(R.id.getCode);
    }

    @Override
    public void getCode(boolean ischecked) {

    }
    @Override
    public void register(boolean isRegister) {
        if(isRegister) {
            new Handler().post(new Runnable() {
                public void run() {
                    startActivity(new Intent(Register_Activity.this,Login_Activity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(Register_Activity.this,msg, Toast.LENGTH_SHORT).show();
    }
    public void back(View v){
        //startActivity(new Intent(Register_Activity.this,Login_Activity.class));
        finish();
    }
}
