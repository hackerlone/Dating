package com.lone.wjm.dating.Model.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.lone.wjm.dating.Model.IDataFragmentModel;
import com.lone.wjm.dating.Ui.IDataFragmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/5.
 * Contact: 4951048@qq.com
 */
public class IDataFragmentModelimpl implements IDataFragmentModel {
    private IDataFragmentView mIDataFragmentView;
    private Context mContext;
    private Handler mHandler;

    public IDataFragmentModelimpl(IDataFragmentView IDataFragmentView, Context context) {
        mIDataFragmentView = IDataFragmentView;
        mContext = context;
    }

    @Override
    public List<Map<String, String>> getAllUserByCity(String userByCity) {
        final List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        AVQuery<AVObject> query = new AVQuery<>("Users");
        query.whereContains("area", userByCity);

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject lis : list) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userHead", lis.get("userHead").toString());
                        map.put("userObjectId", lis.getObjectId());
                        map.put("username", lis.get("username").toString());
                        map.put("sex", lis.get("sex").toString());
                        lists.add(map);
                    }

                    Message msg = new Message();
                    msg.obj = lists;
                    mHandler.sendMessage(msg);
                } else {
                    mIDataFragmentView.showToastMessage("当前没有附近的用户");
                }
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mIDataFragmentView.getAllUserByCity((List<Map<String, String>>) msg.obj);
            }
        };

        return null;
    }

    @Override
    public Map<String, String> getYueDanById(String yuedanById) {
        final Map<String, String> maps = new HashMap<String, String>();
        AVQuery<AVObject> query = new AVQuery<>("YueDan");
        query.whereContains("userObjectId", yuedanById);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    maps.put("yuedanID", avObject.getObjectId());
                    maps.put("ysex", avObject.getString("ysex"));
                    maps.put("yfangshi", avObject.getString("yfangshi"));
                    maps.put("ymiaoshu", avObject.getString("ymiaoshu"));
                    maps.put("yshijian", avObject.getString("yshijian"));
                    maps.put("ychefei", avObject.getString("ychefei"));
                    maps.put("ydidian", avObject.getString("ydidian"));
                    maps.put("ybeizhu", avObject.getString("ybeizhu"));

                    Message msg = new Message();
                    msg.obj = maps;
                    mHandler.sendMessage(msg);
                } else {
                    mIDataFragmentView.showToastMessage("对方尚未发起过约单");
                }
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mIDataFragmentView.getYueDanById((Map<String, String>) msg.obj);
            }
        };
        return null;
    }
    @Override
    public void sendYueDan(String yueDanId, String userId) {
        AVQuery.doCloudQueryInBackground("update YueDan set TouserObjectId='"+userId+"',zhuangtai = '"+"已接受"+"' where objectId='"+yueDanId+"'", new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                if(e == null){
                    mIDataFragmentView.showToastMessage("抢单成功，去我的订单联系他(她)约会吧");
                }else{
                    mIDataFragmentView.showToastMessage("抢单失败，请检查网络连接");
                }
            }
        });
    }
}
