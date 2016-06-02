package com.lone.wjm.dating.Model.impl;

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
        } else if (ysex.equals("null") || yfangshi.equals("null") || ymiaoshu.equals("")
                || yxiaofei.equals("") || ychefei.equals("") || yshijian.equals("") || ydidian.equals("")||ybeizhu.equals("")){
            mISendOrderView.showToastMessage("请完善约单信息");
        }else{

        }
            return false;
    }
}
