package com.lone.wjm.dating.Ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lone.wjm.dating.Adapter.lv_mineAdapter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.activity.SendOrder_Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class OrderFragment extends Fragment {
    private ListView lv_order;
    private List<String> mList;
    private List<Integer> imgList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,null);
        lv_order = (ListView) view.findViewById(R.id.lv_order);
        mList = new ArrayList<String>();
        mList.add("发布订单");
        mList.add("我的订单");
        mList.add("抢单人列表");
        mList.add("约单人列表");
        imgList = new ArrayList<Integer>();
        imgList.add(R.drawable.send_order);
        imgList.add(R.drawable.my_order);
        imgList.add(R.drawable.grab_order);
        imgList.add(R.drawable.meet_order);
        lv_order.setAdapter(new lv_mineAdapter(getContext(),mList,imgList));
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                case 0:
                    getContext().startActivity(new Intent(getActivity(), SendOrder_Activity.class));
                break;
                }
            }
        });
        return view;
    }
}
