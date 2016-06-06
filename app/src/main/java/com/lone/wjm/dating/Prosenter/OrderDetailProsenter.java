package com.lone.wjm.dating.Prosenter;

import android.app.Activity;

import com.lone.wjm.dating.Model.IOrderDetailModel;
import com.lone.wjm.dating.Model.impl.IOrderDetailModelimpl;
import com.lone.wjm.dating.Ui.IOrderDetailView;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public class OrderDetailProsenter {
    private IOrderDetailView mIOrderDetailView;
    private IOrderDetailModel mIOrderDetailModel;
    private Activity activity;
    public OrderDetailProsenter(IOrderDetailView mIOrderDetailView, Activity activity) {
        this.mIOrderDetailView = mIOrderDetailView;
        this.activity = activity;
        mIOrderDetailModel = new IOrderDetailModelimpl(mIOrderDetailView,  activity);
    }
    public void getYueDanInfoById(String OrderObjectId){
        mIOrderDetailView.getYueDanInfoById(mIOrderDetailModel.getYueDanInfoById(OrderObjectId));
    }

    public void getYueDanUserInfoById(String OrderObjectId) {
        mIOrderDetailView.getYueDanUserInfoById(mIOrderDetailModel.getYueDanUserInfoById(OrderObjectId));
    }
    public void getYueDanToUserInfoById(String OrderObjectId){
        mIOrderDetailView.getYueDanToUserInfoById(mIOrderDetailModel.getYueDanToUserInfoById(OrderObjectId));
    }

    public void quxiaoYueDan(String OrderObjectId) {
        mIOrderDetailModel.quxiaoYueDan(OrderObjectId);
    }

    public void querenYueDan(String OrderObjectId) {
        mIOrderDetailModel.querenYueDan(OrderObjectId);
    }
}
