package com.lone.wjm.dating.Prosenter;

import com.lone.wjm.dating.Model.IRegisterModel;
import com.lone.wjm.dating.Model.impl.IRegisterModelImpl;
import com.lone.wjm.dating.Ui.IRegisterView;

import java.util.Map;

/**
 * Created by: Lone on 2016/5/31.
 * Contact: 4951048@qq.com
 */
public class RegisterProsenter {
    private IRegisterView mIRegisterView;
    private IRegisterModel mIReDianModel;
    private Map<String,String>userInfomap;
    public RegisterProsenter(IRegisterView mIRegisterView, Map<String,String>userInfomap) {
        this.userInfomap=userInfomap;
        this.mIRegisterView = mIRegisterView;
        if(mIReDianModel == null){
        mIReDianModel = new IRegisterModelImpl(mIRegisterView);
        }
    }
    public void getCode(){
        mIRegisterView.getCode(mIReDianModel.getCode(userInfomap));
    }
    public void register(){
        mIRegisterView.register(mIReDianModel.register(userInfomap));
    }
}
