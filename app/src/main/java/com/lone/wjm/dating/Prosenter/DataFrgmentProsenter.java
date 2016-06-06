package com.lone.wjm.dating.Prosenter;

import android.content.Context;

import com.lone.wjm.dating.Model.IDataFragmentModel;
import com.lone.wjm.dating.Model.impl.IDataFragmentModelimpl;
import com.lone.wjm.dating.Ui.IDataFragmentView;

/**
 * Created by: Lone on 2016/6/5.
 * Contact: 4951048@qq.com
 */
public class DataFrgmentProsenter {
    private IDataFragmentView mIDataFragmentView;
    private IDataFragmentModel mIDataFragmentModel;
    private Context mContext;

    public DataFrgmentProsenter(Context context,IDataFragmentView mIDataFragmentView) {
        mContext = context;
        this.mIDataFragmentView = mIDataFragmentView;
        mIDataFragmentModel = new IDataFragmentModelimpl(mIDataFragmentView,mContext);

    }
    public void getAllUserByCity(String userCity){
        mIDataFragmentView.getAllUserByCity(mIDataFragmentModel.getAllUserByCity(userCity));
    }

    public void getYueDanById(String userObjectId) {
        mIDataFragmentView.getYueDanById(mIDataFragmentModel.getYueDanById(userObjectId));
    }

    public void sendYueDan(String yueDanId,String userId) {
        mIDataFragmentModel.sendYueDan(yueDanId,userId);
    }
}
