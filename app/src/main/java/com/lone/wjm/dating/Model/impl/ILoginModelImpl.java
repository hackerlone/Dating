package com.lone.wjm.dating.Model.impl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Model.ILoginModel;
import com.lone.wjm.dating.Ui.ILoginView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public class ILoginModelImpl implements ILoginModel {
    private boolean isLogin = false;
    ILoginView mILoginView;
    private Activity activity;
    private Handler loginhandler;
    private SharedPreferences sp;
    private SharedPreferences.Editor mEditor;

    public ILoginModelImpl(ILoginView mILoginView, Activity activity) {
        this.activity = activity;
        this.mILoginView = mILoginView;
        sp = ((MyApplication) activity.getApplication()).getSp();
        mEditor = ((MyApplication) activity.getApplication()).getEditor();
    }

    @Override
    public boolean login(final Map<String, String> userInfomap) {
        if (TextUtils.isEmpty(userInfomap.get("username")) || TextUtils.isEmpty(userInfomap.get("password"))) {
            mILoginView.showToastMessage("用户名或密码为空");
        } else {
            String cql = "select * from Users where " +
                    "(username='" + userInfomap.get("username") + "' or phoneNum ='" + userInfomap.get("username") + "') " +
                    "and (password = '" + userInfomap.get("password") + "')";
            Log.i("info", "cql*******: " + cql);
            AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
                @Override
                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                    Log.i("info", "done://///////// " + avCloudQueryResult.getResults());
                    if (e == null) {
                        try {
                            if (String.valueOf(avCloudQueryResult.getResults()).equals("[]") || String.valueOf(avCloudQueryResult.getResults()) == "[]") {
                                mILoginView.showToastMessage("帐号或密码错误");
                            } else {
                                String userJson = String.valueOf(avCloudQueryResult.getResults());
                                JSONArray jsonArray = new JSONArray(userJson);
                                JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(0)));
                                JSONObject userInfo = new JSONObject(String.valueOf(jsonObject.getJSONObject("serverData")));
                                String username = userInfo.getString("username");
                                String phoneNum = userInfo.getString("phoneNum");
                                String userHead = userInfo.getString("userHead");
                                mEditor.putString("objectId", jsonObject.getString("objectId"));
                                mEditor.putString("username", username);
                                mEditor.putString("phoneNum", phoneNum);
                                mEditor.putBoolean("isLogn", true);
                                mEditor.putString("userHead", userHead);
                                mEditor.putString("miaoshu", userInfo.getString("miaoshu"));
                                mEditor.putString("birthday", userInfo.getString("birthday"));
                                mEditor.putString("area", userInfo.getString("area"));
                                mEditor.putString("sex",userInfo.getString("sex"));
                                mEditor.commit();
                                mILoginView.showToastMessage("登录成功");
                                Message msg = loginhandler.obtainMessage(1);
                                loginhandler.sendMessage(msg);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        mILoginView.showToastMessage("请检查网络连接");
                        e.printStackTrace();
                    }
                }
            });
        }
        //TODO 登录
        loginhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isLogin = true;
                mILoginView.login(true);
            }
        };
        return isLogin;
    }

}
