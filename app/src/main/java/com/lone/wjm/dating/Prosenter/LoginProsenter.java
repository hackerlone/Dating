package com.lone.wjm.dating.Prosenter;

import android.app.Activity;

import com.lone.wjm.dating.Model.ILoginModel;
import com.lone.wjm.dating.Model.impl.ILoginModelImpl;
import com.lone.wjm.dating.Ui.ILoginView;

import java.util.Map;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public class LoginProsenter {
    private ILoginView mILoginView;
    private ILoginModel mILoginModel;
    private Map<String,String>userInfomap;
    private Activity activity;
    public LoginProsenter(ILoginView mILoginView, Map<String,String>userInfomap,Activity activity) {
        this.userInfomap=userInfomap;
        this.mILoginView = mILoginView;
        this.activity=activity;
        if(mILoginModel == null){
            mILoginModel = new ILoginModelImpl(mILoginView,activity);
        }
    }
    public void login(){
        mILoginView.login(mILoginModel.login(userInfomap));
    }
}
