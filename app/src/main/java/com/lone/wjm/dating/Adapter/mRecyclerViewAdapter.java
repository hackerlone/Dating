package com.lone.wjm.dating.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.activity.MineInfo_Activity;
import com.lone.wjm.dating.Ui.activity.MyOrderActivity;
import com.lone.wjm.dating.Ui.fragment.DataFragment;
import com.lone.wjm.dating.Util.Options;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/4.
 * Contact: 4951048@qq.com
 */
public class mRecyclerViewAdapter extends RecyclerView.Adapter<mRecyclerViewAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<Map<String,String>> list;
    private Context context;
    private SharedPreferences sp;
    private Activity activity;
    public mRecyclerViewAdapter(Context context, List<Map<String,String>> list, Activity activity)
    {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.activity = activity;
        sp = ((MyApplication)activity.getApplication()).getSp();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.my_image_view,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        viewHolder.mTxt = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        final Map<String,String> mDatas = list.get(i);
        final String userHeadUrl = mDatas.get("userHead").toString();
        final String sex = mDatas.get("sex").toString();
        ImageLoader.getInstance().displayImage(userHeadUrl,viewHolder.mImg, Options.getListOptions());
        viewHolder.mTxt.setText(mDatas.get("username"));

        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.getInstance().displayImage(userHeadUrl, DataFragment.civ_home_head,Options.getListOptions());
                DataFragment.civ_home_head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MineInfo_Activity.class));
                    }
                });
                if(sex.equals("男")){
                    DataFragment.iv_home_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.boy));
                }else if(sex.equals("女")){
                    DataFragment.iv_home_sex.setImageDrawable(context.getResources().getDrawable(R.drawable.girl));
                }

                String userObjectId = mDatas.get("userObjectId");
                if(userObjectId.equals(sp.getString("objectId","null"))){
                    context.startActivity(new Intent(context,MyOrderActivity.class));
                }else{
                    DataFragment.dataFrgmentProsenter.getYueDanById(userObjectId);
                }
            }
        });
    }

}
