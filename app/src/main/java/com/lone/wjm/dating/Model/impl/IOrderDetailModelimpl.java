package com.lone.wjm.dating.Model.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.lone.wjm.dating.Model.IOrderDetailModel;
import com.lone.wjm.dating.Ui.IOrderDetailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public class IOrderDetailModelimpl implements IOrderDetailModel {
    private Map<String, String> OrderInfo;
    private IOrderDetailView mIOrderDetailView;
    private Handler mHandler;
    private Activity activity;
    public IOrderDetailModelimpl(IOrderDetailView mIOrderDetailView, Activity activity) {
        this.mIOrderDetailView = mIOrderDetailView;
    }
    @Override
    public Map<String, String> getYueDanInfoById(String OrderObjectId) {
        final AVQuery<AVObject> statusQuery = new AVQuery<>("YueDan");
        statusQuery.whereEqualTo("objectId", OrderObjectId);
        statusQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list1, AVException e) {
                if (e == null) {
                    for (int i = 0; i < list1.size(); i++) {
                        OrderInfo = new HashMap<String, String>();
                        OrderInfo.put("objectId",list1.get(i).getString("objectId"));
                        OrderInfo.put("userObjectId",list1.get(i).getString("userObjectId"));
                        OrderInfo.put("TouserObjectId",list1.get(i).getString("TouserObjectId"));
                        OrderInfo.put("ymiaoshu",list1.get(i).getString("ymiaoshu"));
                        OrderInfo.put("ydidian",list1.get(i).getString("ydidian"));
                        OrderInfo.put("yshijian",list1.get(i).getString("yshijian"));
                        OrderInfo.put("yxiaofei",list1.get(i).getString("yxiaofei"));
                        OrderInfo.put("ysex",list1.get(i).getString("ysex"));
                        OrderInfo.put("ychefei",list1.get(i).getString("ychefei"));
                        OrderInfo.put("yfangshi",list1.get(i).getString("yfangshi"));
                        OrderInfo.put("ybeizhu",list1.get(i).getString("ybeizhu"));
                        OrderInfo.put("zhuangtai",list1.get(i).getString("zhuangtai"));
                        Message msg = new Message();
                        msg.obj = OrderInfo;
                        mHandler.sendMessage(msg);
                    }
                } else {
                    mIOrderDetailView.showToastMessage("查询失败，请检查网络连接");
                    e.printStackTrace();
                }
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mIOrderDetailView.getYueDanInfoById((Map<String, String>) msg.obj);
            }
        };
        return null;
    }

    @Override
    public Map<String, String> getYueDanUserInfoById(String OrderObjectId) {
        AVQuery<AVObject> query = new AVQuery<>("Users");
        query.whereEqualTo("objectId", OrderObjectId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject  user: list) {
                    Map<String,String> userInfo = new HashMap<String, String>();
                    userInfo.put("phoneNum",user.get("phoneNum").toString());
                    userInfo.put("username",user.get("username").toString());
                    userInfo.put("area",user.get("area").toString());
                    userInfo.put("userHead",user.get("userHead").toString());
                    userInfo.put("sex",user.get("sex").toString());
                    Log.i("info", "username: "+user.get("username").toString());
                    Message msg = new Message();
                    msg.obj = userInfo;
                    mHandler.sendMessage(msg);
                }
            }
        });
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mIOrderDetailView.getYueDanUserInfoById((Map<String, String>)msg.obj);
            }
        };
        return null;
    }

    @Override
    public Map<String, String> getYueDanToUserInfoById(String OrderObjectId) {
        Log.i("info", "getYueDanToUserInfoById: "+OrderObjectId);
        AVQuery<AVObject> query = new AVQuery<>("Users");
        query.whereEqualTo("objectId", OrderObjectId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject  user: list) {
                    Map<String,String> userInfo = new HashMap<String, String>();
                    userInfo.put("phoneNum",user.get("phoneNum").toString());
                    userInfo.put("username",user.get("username").toString());
                    userInfo.put("area",user.get("area").toString());
                    userInfo.put("userHead",user.get("userHead").toString());
                    userInfo.put("sex",user.get("sex").toString());
                    Log.i("info", "Tousername: "+user.get("username").toString());
                    Message msg1 = new Message();
                    msg1.obj = userInfo;
                    mHandler.sendMessage(msg1);
                }
            }
        });
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg1) {
                super.handleMessage(msg1);
                mIOrderDetailView.getYueDanToUserInfoById((Map<String, String>)msg1.obj);
            }
        };
        return null;
    }

    @Override
    public void quxiaoYueDan(String orderObjectId) {
        AVQuery.doCloudQueryInBackground("update YueDan set zhuangtai = '"+"已取消"+"' where objectId='"+orderObjectId+"'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(e == null){
                    mIOrderDetailView.showToastMessage("修改成功");
                }else{
                    mIOrderDetailView.showToastMessage("修改失败，检查网络连接");
                }
            }
        });
    }

    @Override
    public void querenYueDan(String orderObjectId) {
        AVQuery.doCloudQueryInBackground("update YueDan set zhuangtai = '"+"已完成"+"' where objectId='"+orderObjectId+"'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(e == null){
                    mIOrderDetailView.showToastMessage("修改成功");
                }else{
                    mIOrderDetailView.showToastMessage("修改失败，检查网络连接");
                }
            }
        });
    }
}
