package com.lone.wjm.dating.Ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.lone.wjm.dating.Adapter.lv_mineAdapter;
import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.activity.Login_Activity;
import com.lone.wjm.dating.Ui.activity.MineInfo_Activity;
import com.lone.wjm.dating.Util.Options;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class MineFragment extends Fragment {
    private ListView lv_mine;
    private List<String> mList;
    private List<Integer> imgList;
    private ImageView iv_head_icon;
    private SharedPreferences sp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        lv_mine = (ListView) view.findViewById(R.id.lv_mine);
        iv_head_icon = (ImageView) view.findViewById(R.id.iv_head_icon);
        sp = ((MyApplication)getActivity().getApplication()).getSp();
        String headUrl = sp.getString("userHead","http://ac-hlwakbmt.clouddn.com/1a9272ebaa4940fb.jpg");
        if(sp.getString("username","null").equals("null")){
            iv_head_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(new Intent(getActivity(),Login_Activity.class));
                }
            });
        }else{
            ImageLoader.getInstance().displayImage(headUrl, iv_head_icon, Options.getListOptions());
            iv_head_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContext().startActivity(new Intent(getActivity(), MineInfo_Activity.class));
                }
            });
        }
        mList = new ArrayList<String>();
        mList.add("我的相册");
        mList.add("我的分享");
        mList.add("我的钱包");
        mList.add("消息中心");
        mList.add("游戏大厅");
        mList.add("设置中心");
        imgList = new ArrayList<Integer>();
        imgList.add(R.drawable.photo);
        imgList.add(R.drawable.my_order);
        imgList.add(R.drawable.wallet);
        imgList.add(R.drawable.message);
        imgList.add(R.drawable.game);
        imgList.add(R.drawable.setting);

        lv_mine.setAdapter(new lv_mineAdapter(getContext(),mList,imgList));
        return view;
    }

}
