package com.lone.wjm.dating.Model;

import java.util.Map;

/**
 * Created by: Lone on 2016/6/1.
 * Contact: 4951048@qq.com
 */
public interface IMineInfo_ActivityModel {
    public boolean uploadHeadImg(String picturePath,String filename);
    public boolean saveUserInfo(Map<String,String>userInfo);
}
