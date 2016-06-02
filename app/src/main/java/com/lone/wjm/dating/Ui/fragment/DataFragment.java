package com.lone.wjm.dating.Ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.activity.City_Select_Activity;
import com.lone.wjm.dating.Ui.activity.Filter_Activity;
import com.lone.wjm.dating.Util.BaiduMap.BaseLocation;
import com.lone.wjm.dating.Util.BaiduMap.MyLocationListener;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class DataFragment extends Fragment implements View.OnClickListener {
    public static TextView tv_city_name;
    private ImageView iv_home_select;
    private ImageView civ_home_head;
    private TextView tv_home_name;
    private ImageView iv_home_sex;
    private TextView iv_home_age;
    private TextView tv_home_distance;
    private TextView tv_home_date_sex;
    private TextView tv_home_date_type;
    private TextView tv_home_date_content;
    private TextView tv_home_date_tip;
    private TextView tv_home_date_time;
    private TextView tv_home_date_location;
    private TextView tv_home_date_ext;
    private Button tv_home_receive_order;
    private LinearLayout ll_find_city;
    private SharedPreferences.Editor mEditor;
    //TODO  介入百度SDK
    BaseLocation mBaseLocation;
    public static  Handler mhandler;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, null);
        initView(view);
        if(mBaseLocation == null){
            mBaseLocation = new BaseLocation(getActivity());
        }
        mBaseLocation.mLocationClient.start();//启动定位
        mhandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    if(MyLocationListener.map.get("city") != null) {
                        tv_city_name.setText(MyLocationListener.map.get("city"));
                        Toast.makeText(getContext(), "您现在位于" + MyLocationListener.map.get("addr") + "附近", Toast.LENGTH_SHORT).show();
                        mBaseLocation.mLocationClient.stop();//停止定位
                        mBaseLocation = null;
                    }else{
                        Toast.makeText(getContext(), "没有获取到您的位置，请打开定位服务", Toast.LENGTH_SHORT).show();
                        mBaseLocation.mLocationClient.stop();//停止定位
                        mBaseLocation = null;
                    }}
            }
        };
        return view;
    }

    public void onDestroy() {
        super.onDestroy();
        mBaseLocation.mLocationClient.stop();//停止定位
    }
    private void initView(View view) {
        tv_city_name = (TextView) view.findViewById(R.id.tv_city_name);
        iv_home_select = (ImageView) view.findViewById(R.id.iv_home_select);
        civ_home_head = (ImageView) view.findViewById(R.id.civ_home_head);
        tv_home_name = (TextView) view.findViewById(R.id.tv_home_name);
        iv_home_sex = (ImageView) view.findViewById(R.id.iv_home_sex);
        iv_home_age = (TextView) view.findViewById(R.id.iv_home_age);
        tv_home_distance = (TextView) view.findViewById(R.id.tv_home_distance);
        tv_home_date_sex = (TextView) view.findViewById(R.id.tv_home_date_sex);
        tv_home_date_type = (TextView) view.findViewById(R.id.tv_home_date_type);
        tv_home_date_content = (TextView) view.findViewById(R.id.tv_home_date_content);
        tv_home_date_tip = (TextView) view.findViewById(R.id.tv_home_date_tip);
        tv_home_date_time = (TextView) view.findViewById(R.id.tv_home_date_time);
        tv_home_date_location = (TextView) view.findViewById(R.id.tv_home_date_location);
        tv_home_date_ext = (TextView) view.findViewById(R.id.tv_home_date_ext);
        tv_home_receive_order = (Button) view.findViewById(R.id.tv_home_receive_order);
        ll_find_city = (LinearLayout) view.findViewById(R.id.ll_find_city);

        iv_home_select.setOnClickListener(this);
        tv_home_receive_order.setOnClickListener(this);
        ll_find_city.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_find_city:
                startActivity(new Intent(getContext(), City_Select_Activity.class));
                break;
            case R.id.iv_home_select:
                Intent intent = new Intent(new Intent(getContext(), Filter_Activity.class));
                intent.putExtra("city",tv_city_name.getText().toString());
                getContext().startActivity(intent);
                break;
        }
    }
}
