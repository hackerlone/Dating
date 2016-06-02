package com.lone.wjm.dating.Prosenter;

import android.app.Activity;

import com.lone.wjm.dating.Model.IMineInfo_ActivityModel;
import com.lone.wjm.dating.Model.impl.IMineInfo_ActivityModelImpl;
import com.lone.wjm.dating.Ui.IMineInfo_ActivityView;

import java.util.Map;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public class MineInfo_ActivityProsenter {
    private IMineInfo_ActivityView mIMineInfo_activityView;
    private IMineInfo_ActivityModel mIMineInfo_activityModel;
    private String picturePath;
    private String filename;
    private  Activity activity;
    public MineInfo_ActivityProsenter(IMineInfo_ActivityView mIMineInfo_activityView, Activity activity) {
        this.picturePath=picturePath;
        this.mIMineInfo_activityView = mIMineInfo_activityView;
        this.filename=filename;
        this.activity=activity;
        if(mIMineInfo_activityModel == null){
            mIMineInfo_activityModel = new IMineInfo_ActivityModelImpl(mIMineInfo_activityView,activity);
        }
    }
    public void saveUserInfo(Map<String,String> userInfo){
        mIMineInfo_activityView.saveUserInfo(mIMineInfo_activityModel.saveUserInfo(userInfo));
    }
    public void uploadHeadImg(String picturePath,String filename){
        mIMineInfo_activityView.uploadHeadImg(mIMineInfo_activityModel.uploadHeadImg(picturePath,filename));
    }
}
