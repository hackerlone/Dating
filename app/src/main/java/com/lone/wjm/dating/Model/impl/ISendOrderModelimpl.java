package com.lone.wjm.dating.Model.impl;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.lone.wjm.dating.Model.ISendOrderModel;
import com.lone.wjm.dating.Ui.ISendOrderView;

import java.util.Map;

/**
 * Created by: Lone on 2016/6/2.
 * Contact: 4951048@qq.com
 */
public class ISendOrderModelimpl implements ISendOrderModel {
    private ISendOrderView mISendOrderView;

    public ISendOrderModelimpl(ISendOrderView mISendOrderView) {
        this.mISendOrderView = mISendOrderView;
    }
    @Override
    public boolean sendOrder(Map<String, String> yueDanmap) {
        String ysex = yueDanmap.get("ysex");
        String yfangshi = yueDanmap.get("yfangshi");
        String ymiaoshu = yueDanmap.get("ymiaoshu");
        String yxiaofei = yueDanmap.get("yxiaofei");
        String ychefei = yueDanmap.get("ychefei");
        String yshijian = yueDanmap.get("yshijian");
        String ydidian = yueDanmap.get("ydidian");
        String ybeizhu = yueDanmap.get("ybeizhu");
        String userObjectId = yueDanmap.get("userObjectId");
        if (userObjectId.equals("null")) {
            mISendOrderView.showToastMessage("请先登录");
        } else if (TextUtils.isEmpty(ysex) || TextUtils.isEmpty(yfangshi) || TextUtils.isEmpty(ymiaoshu)
                || TextUtils.isEmpty(yxiaofei) || TextUtils.isEmpty(ychefei) || TextUtils.isEmpty(yshijian) || TextUtils.isEmpty(ydidian)||TextUtils.isEmpty(ybeizhu)){
            mISendOrderView.showToastMessage("请完善约单信息");
        }else{
            AVObject yueDan = new AVObject("YueDan");// 构建对象
            yueDan.put("ymiaoshu", ymiaoshu);
            yueDan.put("yfangshi", yfangshi);
            yueDan.put("yxiaofei", yxiaofei);
            yueDan.put("ychefei", ychefei);
            yueDan.put("yshijian", yshijian);
            yueDan.put("ydidian", ydidian);
            yueDan.put("ybeizhu", ybeizhu);
            yueDan.put("userObjectId", userObjectId);
            yueDan.saveInBackground(new SaveCallback() {// 保存到服务端
                @Override
                public void done(AVException e) {
                    if(e == null) {
                        mISendOrderView.showToastMessage("创建成功");
                    }else{
                        mISendOrderView.showToastMessage("创建失败，检查网络连接");
                        e.printStackTrace();
                    }
                }
            });
        }
            return false;
    }
}
