package com.lone.wjm.dating.Model.impl;

import android.os.Handler;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.lone.wjm.dating.Model.IMyOrderModel;
import com.lone.wjm.dating.Ui.IMyOrderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public class IMyOrderModelimpl implements IMyOrderModel {
    private IMyOrderView mIMyOrderView;
    private Handler mHandler;
    private List<Map<String, String>> list;

    public IMyOrderModelimpl(IMyOrderView mIMyOrderView) {
        this.mIMyOrderView = mIMyOrderView;
    }

    @Override
    public List<Map<String, String>> getYueDan(String objectId) {
        //TODO 获取我的约单信息
        list = new ArrayList<Map<String, String>>();
        final AVQuery<AVObject> priorityQuery = new AVQuery<>("YueDan");
        priorityQuery.whereGreaterThanOrEqualTo("objectId", objectId);
        final AVQuery<AVObject> statusQuery = new AVQuery<>("YueDan");
        statusQuery.whereEqualTo("userObjectId", objectId);
        AVQuery<AVObject> query = AVQuery.or(Arrays.asList(priorityQuery, statusQuery));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list1, AVException e) {
                if (e == null) {
                    for (int i = 0; i < list1.size(); i++) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("objectId",list1.get(i).getString("objectId"));
                        map.put("userObjectId",list1.get(i).getString("userObjectId"));
                        map.put("TouserObjectId",list1.get(i).getString("TouserObjectId"));
                        map.put("ymiaoshu",list1.get(i).getString("ymiaoshu"));
                        map.put("ydidian",list1.get(i).getString("ydidian"));
                        map.put("yshijian",list1.get(i).getString("yshijian"));
                        map.put("yxiaofei",list1.get(i).getString("yxiaofei"));
                        map.put("ysex",list1.get(i).getString("ysex"));
                        map.put("ychefei",list1.get(i).getString("ychefei"));
                        map.put("yfangshi",list1.get(i).getString("yfangshi"));
                        map.put("ybeizhu",list1.get(i).getString("ybeizhu"));
                        map.put("zhuangtai",list1.get(i).getString("zhuangtai"));
                        list.add(map);
                        Message msg = new Message();
                        msg.obj = list;
                        mHandler.sendMessage(msg);

                    }
                } else {
                    mIMyOrderView.showToastMessage("查询失败，请检查网络连接");
                    e.printStackTrace();
                }
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mIMyOrderView.getYueDan((List<Map<String, String>>) msg.obj);
            }
        };
        return list;
    }
}
