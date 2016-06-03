package com.lone.wjm.dating.Prosenter;

import com.lone.wjm.dating.Model.IMyOrderModel;
import com.lone.wjm.dating.Model.impl.IMyOrderModelimpl;
import com.lone.wjm.dating.Ui.IMyOrderView;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public class MyOrderProsenter {
    private String objectId;
    private IMyOrderView mIMyOrderView;
    private IMyOrderModel mIMyOrderModel;
    public MyOrderProsenter(String objectId, IMyOrderView mIMyOrderView) {
        this.objectId = objectId;
        this.mIMyOrderView = mIMyOrderView;
        mIMyOrderModel = new IMyOrderModelimpl(mIMyOrderView);
    }
    public void getYueDan(){
        mIMyOrderView.getYueDan(mIMyOrderModel.getYueDan(objectId));
    }
}
