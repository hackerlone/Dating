package com.lone.wjm.dating.Ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lone.wjm.dating.Adapter.mRecyclerViewAdapter;
import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Prosenter.DataFrgmentProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.IDataFragmentView;
import com.lone.wjm.dating.Ui.activity.City_Select_Activity;
import com.lone.wjm.dating.Ui.activity.Filter_Activity;
import com.lone.wjm.dating.Util.BaiduMap.BaseLocation;
import com.lone.wjm.dating.Util.BaiduMap.MyLocationListener;

import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class DataFragment extends Fragment implements View.OnClickListener, IDataFragmentView {
    //region Description
    public static TextView tv_city_name;
    private ImageView iv_home_select;
    public static ImageView civ_home_head;
    private TextView tv_home_name;
    public static ImageView iv_home_sex;
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
    private RecyclerView mRecyclerView;
    public static String yueDanId;
    //TODO  介入百度SDK
    BaseLocation mBaseLocation;
    public static Handler mhandler;
    public static DataFrgmentProsenter dataFrgmentProsenter;
    private SharedPreferences sp;

    //endregion
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, null);
        initView(view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        sp = ((MyApplication)getActivity().getApplication()).getSp();
        //region Description
        if (mBaseLocation == null) {
            mBaseLocation = new BaseLocation(getActivity());
        }
        mBaseLocation.mLocationClient.start();//启动定位
        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    if (MyLocationListener.map.get("city") != null) {
                        tv_city_name.setText(MyLocationListener.map.get("city"));
                        Toast.makeText(getContext(), "您现在位于" + MyLocationListener.map.get("addr") + "附近", Toast.LENGTH_SHORT).show();
                        mBaseLocation.mLocationClient.stop();//停止定位
                        mBaseLocation = null;
                    } else {
                        Toast.makeText(getContext(), "没有获取到您的位置，请打开定位服务", Toast.LENGTH_SHORT).show();
                        mBaseLocation.mLocationClient.stop();//停止定位
                        mBaseLocation = null;
                    }
                }
            }
        };
        //endregion
//        initmRecyclerView();
        dataFrgmentProsenter = new DataFrgmentProsenter(getContext(), DataFragment.this);
        dataFrgmentProsenter.getAllUserByCity("成都");
        return view;
    }

    public void initmRecyclerView(List<Map<String, String>> list) {
        //TODO 约单页轮播
        mRecyclerViewAdapter mAdapter = new mRecyclerViewAdapter(getContext(), list,getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void getAllUserByCity(List<Map<String, String>> userByCity) {
        if (userByCity != null) {
            initmRecyclerView(userByCity);
        }
    }

    @Override
    public void getYueDanById(Map<String, String> yuedanById) {
        if (yuedanById != null) {
            Log.i("info", "getYueDanById: "+yuedanById.get("yfangshi"));
            yueDanId = yuedanById.get("yuedanID");
            tv_home_date_sex.setText(yuedanById.get("ysex"));
            tv_home_date_type.setText(yuedanById.get("yfangshi"));
            tv_home_date_content.setText(yuedanById.get("ymiaoshu"));
            tv_home_date_tip.setText(yuedanById.get("ychefei"));
            tv_home_date_time.setText(yuedanById.get("yshijian"));
            tv_home_date_location.setText(yuedanById.get("ydidian"));
            tv_home_date_ext.setText(yuedanById.get("ybeizhu"));
        }
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

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
                intent.putExtra("city", tv_city_name.getText().toString());
                getContext().startActivity(intent);
                break;
            case R.id.tv_home_receive_order:
                //TODO 抢单
                dataFrgmentProsenter.sendYueDan(yueDanId,sp.getString("objectId","null"));
                break;
        }
    }
}
