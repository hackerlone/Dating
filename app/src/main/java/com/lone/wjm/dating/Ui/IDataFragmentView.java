package com.lone.wjm.dating.Ui;

import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/5.
 * Contact: 4951048@qq.com
 */
public interface IDataFragmentView {
    public void getAllUserByCity(List<Map<String,String>> userByCity);
    public void getYueDanById(Map<String,String> yuedanById);
    public void showToastMessage(String msg);
}
