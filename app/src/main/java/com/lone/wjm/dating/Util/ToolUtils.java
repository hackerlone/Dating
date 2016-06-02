package com.lone.wjm.dating.Util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by: Lone on 2016/5/18.
 * Contact: 4951048@qq.com
 * �ж���������״̬
 */
public class ToolUtils {
    public static boolean isNetWorkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conn == null){
            return false;
        }else{
            // ��ȡNetworkInfo����
            NetworkInfo [] networkInfo = conn.getAllNetworkInfo();
            if(networkInfo != null && networkInfo.length > 0){
                for (int i = 0; i < networkInfo.length; i++) {
                    //				Log.i("test", "===״̬==="
//						+ networkInfo.getState());
//				Log.i("test","===����==="
//						+ networkInfo.getTypeName());
                    // �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
                    if(networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
