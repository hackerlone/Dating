package com.lone.wjm.dating.Ui;

import java.util.Map;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public interface IOrderDetailView  {
    public void getYueDanInfoById(Map<String, String> OrderInfo);
    public void getYueDanUserInfoById(Map<String, String> OrderInfo);
    public void getYueDanToUserInfoById(Map<String, String> OrderInfo);
    public void showToastMessage(String msg);
}
