package com.lone.wjm.dating.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/5.
 * Contact: 4951048@qq.com
 */
public interface IDataFragmentModel {
    public List<Map<String,String>> getAllUserByCity(String userByCity);
    public Map<String,String> getYueDanById(String yuedanById);
    public void sendYueDan(String yueDanId,String userId);
}
