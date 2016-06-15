package com.lone.wjm.dating.Model.impl;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.lone.wjm.dating.Model.IRegisterModel;
import com.lone.wjm.dating.Ui.IRegisterView;
import com.lone.wjm.dating.Ui.activity.Register_Activity;

import java.util.Map;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public class IRegisterModelImpl implements IRegisterModel {
    private boolean phoneNumisChecked = false;
    public boolean phoneNumisSend = true;
    private boolean isRegister = false;
    private AVUser user;
    IRegisterView mIRegisterView;
    Handler mHandler;
    Handler handler;

    public IRegisterModelImpl(IRegisterView mIRegisterView) {
        this.mIRegisterView = mIRegisterView;
        user = new AVUser();
    }
    //手机号码验证
    private boolean checkPhoneNum(String phoneNum) {
        String telRegex = "[1][3578]\\d{9}";
        if (phoneNum.matches(telRegex)) {
            phoneNumisChecked = true;
        } else {
            phoneNumisChecked = false;
            mIRegisterView.showToastMessage("手机号码格式错误");
        }
        return phoneNumisChecked;
    }
    @Override
    public boolean getCode(Map<String, String> userInfomap) {
        phoneNumisSend = false;
        String phoneNum = userInfomap.get("phoneNum");
        phoneNumisChecked = checkPhoneNum(phoneNum);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 10;
                while (i >= 0) {
                    try {
                        Thread.sleep(1000);
                        Message message = new Message();
                        message.what = i;
                        mHandler.sendMessage(message);
                        i--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Register_Activity.getCode.setText(msg.what + "s后重新获取");
                Register_Activity.getCode.setEnabled(false);
                if (msg.what == 0) {
                    Register_Activity.getCode.setText("获取验证码");
                    Register_Activity.getCode.setEnabled(true);
                }
            }
        };
        if (phoneNumisChecked) {
            CheckedPhoneCode(phoneNum);
        }
        return false;
    }
    //获取手机验证码
    private void CheckedPhoneCode(String phoneNum) {
        user.put("mobilePhoneNumber", phoneNum);
        user.setUsername(phoneNum);
        user.setPassword("null");
        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    mIRegisterView.showToastMessage("发送成功,请注意查收");
                    Log.i("info", "done: 000000000000000000000000000000000");
                    phoneNumisSend = true;
                } else {
                    mIRegisterView.showToastMessage("获取失败，请检查网络连接");
                    phoneNumisSend = false;
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public boolean register(final Map<String, String> userInfomap) {
        if (TextUtils.isEmpty(userInfomap.get("phoneNum")) || TextUtils.isEmpty(userInfomap.get("username")) || TextUtils.isEmpty(userInfomap.get("password")) || TextUtils.isEmpty(userInfomap.get("phoneCode"))) {
            mIRegisterView.showToastMessage("用户名或密码为空");
        } else if (phoneNumisSend) {
            AVUser.verifyMobilePhoneInBackground(userInfomap.get("phoneCode"), new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        AVObject users = new AVObject("Users");
                        //TODO 初始化Users表
                        users.put("phoneNum", userInfomap.get("phoneNum"));
                        users.put("username", userInfomap.get("username"));
                        users.put("password", userInfomap.get("password"));
                        users.put("miaoshu", "勤劳");
                        users.put("birthday", "");
                        users.put("area", "");
                        users.put("sex","男");
                        users.saveInBackground();
                        mIRegisterView.showToastMessage("注册成功，请登录");
                        Message msg = handler.obtainMessage(1);
                        handler.sendMessage(msg);
                    } else {
                        mIRegisterView.showToastMessage("短信验证失败");
                    }
                }
            });
            //TODO 注册
        } else {
            mIRegisterView.showToastMessage("注册失败");
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isRegister = true;
                mIRegisterView.register(true);
            }
        };
        return isRegister;
    }
}
