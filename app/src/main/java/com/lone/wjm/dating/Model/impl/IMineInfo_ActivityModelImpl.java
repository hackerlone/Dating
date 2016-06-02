package com.lone.wjm.dating.Model.impl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.SaveCallback;
import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Model.IMineInfo_ActivityModel;
import com.lone.wjm.dating.Ui.IMineInfo_ActivityView;
import com.lone.wjm.dating.Ui.activity.MineInfo_Activity;
import com.lone.wjm.dating.Util.Options;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/1.
 * Contact: 4951048@qq.com
 */
public class IMineInfo_ActivityModelImpl implements IMineInfo_ActivityModel {
    private IMineInfo_ActivityView mIMineInfo_activityView;
    private Activity activity;
    private SharedPreferences sp;
    private SharedPreferences.Editor mEditor;

    public IMineInfo_ActivityModelImpl(IMineInfo_ActivityView mIMineInfo_activityView, Activity activity) {
        this.mIMineInfo_activityView = mIMineInfo_activityView;
        this.activity = activity;
        sp = ((MyApplication) activity.getApplication()).getSp();
        mEditor = ((MyApplication) activity.getApplication()).getEditor();
    }

    @Override
    public boolean uploadHeadImg(String picturePath, String filename) {
        if (picturePath.equals("")) {
            mIMineInfo_activityView.showToastMessage("您没有选择头像");
        } else {
            try {
                final AVFile file = AVFile.withAbsoluteLocalPath(filename, picturePath);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Log.d("info", file.getUrl());//返回一个唯一的 Url 地址
                            String headUrl = file.getUrl();
                            ImageLoader.getInstance().displayImage(headUrl, MineInfo_Activity.civ_info_icon, Options.getListOptions());
                            mEditor.putString("userHead", headUrl);
                            mEditor.commit();
                            AVQuery.doCloudQueryInBackground("update Users set userHead = '" + headUrl + "' where objectId='" + sp.getString("objectId", "null") + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                                @Override
                                public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                    if (e == null) {
                                        mIMineInfo_activityView.showToastMessage("头像上传完成");
                                    } else {
                                        mIMineInfo_activityView.showToastMessage("上传失败，请检查网络连接");
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean saveUserInfo(Map<String, String> userInfo) {
        String ObjectId = sp.getString("objectId", "null");
        final String username = userInfo.get("username");
        final String miaoshu = userInfo.get("miaoshu");
        final String birthday = userInfo.get("birthday");
        final String area = userInfo.get("area");
        final String sexsec = userInfo.get("sexsec");
        String cql = "update Users set username='" + username + "' , miaoshu = '" + miaoshu + "'," +
                "birthday = '" + birthday + "', area = '"+area+"' , sex = '"+sexsec+"'"
        +" where objectId= '"+ObjectId+"'";
        Log.i("info", "saveUserInfo: "+cql);
        AVQuery.doCloudQueryInBackground(cql, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
               if(e==null){
                   mEditor.putString("username",username);
                   mEditor.putString("miaoshu",miaoshu);
                   mEditor.putString("birthday",birthday);
                   mEditor.putString("area",area);
                   mEditor.putString("sex",sexsec);
                   mEditor.commit();
                   mIMineInfo_activityView.showToastMessage("修改成功");
               }else{
                   mIMineInfo_activityView.showToastMessage("修改失败");
                   e.printStackTrace();
               }
            }
        });
        return false;
    }
}
