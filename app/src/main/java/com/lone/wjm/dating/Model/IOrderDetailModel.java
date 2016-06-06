package com.lone.wjm.dating.Model;

import java.util.Map;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public interface IOrderDetailModel {
    public Map<String, String>   getYueDanInfoById(String OrderObjectId);
    public Map<String, String>   getYueDanUserInfoById(String OrderObjectId);
    public Map<String, String>   getYueDanToUserInfoById(String OrderObjectId);

    public void quxiaoYueDan(String orderObjectId);

    public void querenYueDan(String orderObjectId);
}
