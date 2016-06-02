package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lone.wjm.dating.Prosenter.LoginProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.ILoginView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class Login_Activity extends Activity implements ILoginView{

    private ImageView iv_login_icon;
    private EditText et_login_count;
    private EditText et_login_pwd;
    private Map<String, String> map;
    private LoginProsenter mLoginProsenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        iv_login_icon = (ImageView) findViewById(R.id.iv_login_icon);
        et_login_count = (EditText) findViewById(R.id.et_login_count);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
    }

    public void login(View v) {
        map = new HashMap<String, String>();
        map.put("username", et_login_count.getText().toString());
        map.put("password", et_login_pwd.getText().toString());
        mLoginProsenter = new LoginProsenter(this,map,this);
        mLoginProsenter.login();
    }

    public void back(View v) {
        finish();
    }

    public void register(View v) {
        startActivity(new Intent(Login_Activity.this, Register_Activity.class));
    }

    @Override
    public void login(boolean islogin) {
        if(islogin) {
            new Handler().post(new Runnable() {
                public void run() {
                    startActivity(new Intent(Login_Activity.this,MainActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(Login_Activity.this,msg, Toast.LENGTH_SHORT).show();
    }
}
