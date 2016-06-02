package com.lone.wjm.dating.Model;

import java.util.Map;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public interface IRegisterModel {
    public boolean  getCode(Map<String,String>userInfomap);
    public boolean  register(Map<String,String>userInfomap);
}
