package com.lone.wjm.dating.Prosenter;

import com.lone.wjm.dating.Model.ISendOrderModel;
import com.lone.wjm.dating.Model.impl.ISendOrderModelimpl;
import com.lone.wjm.dating.Ui.ISendOrderView;

import java.util.Map;

/**
 * Created by: Lone on 2016/6/2.
 * Contact: 4951048@qq.com
 */
public class SendOrderProsenter {
    private ISendOrderView mISendOrderView;
    private ISendOrderModel mISendOrderModel;
    public SendOrderProsenter(ISendOrderView mISendOrderView) {
        this.mISendOrderView = mISendOrderView;
        mISendOrderModel = new ISendOrderModelimpl(mISendOrderView);
    }
    public void sendOrder(Map<String,String>yuedanMap){
        mISendOrderView.sendOrder(mISendOrderModel.sendOrder(yuedanMap));
    }
}
