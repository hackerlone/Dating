package com.lone.wjm.dating.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.activity.OrderDetailActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public class MyOrderAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private Context mContext;
    private LayoutInflater mInflater;
    private SharedPreferences sp;
    public MyOrderAdapter(Context mContext, List<Map<String, String>> list,Activity activity) {
        this.list = list;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        sp = ((MyApplication)activity.getApplication()).getSp();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView itemtvtime;
        TextView itemtvtitle;
        TextView itemtvtype;
        TextView itemtvtip;
        ImageView itemivorderTag;
        Button itembtorderstatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, String> map = new HashMap<String, String>();
        map = list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_our_order, null);
            holder.itemtvtime = (TextView) convertView.findViewById(R.id.item_tv_time);
            holder.itemtvtitle = (TextView) convertView.findViewById(R.id.item_tv_title);
            holder.itemtvtype = (TextView) convertView.findViewById(R.id.item_tv_type);
            holder.itemtvtip = (TextView) convertView.findViewById(R.id.item_tv_tip);
            holder.itemivorderTag = (ImageView) convertView.findViewById(R.id.item_iv_order_Tag);
            holder.itembtorderstatus = (Button) convertView.findViewById(R.id.item_bt_order_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemtvtime.setText(map.get("yshijian"));
        holder.itemtvtitle.setText(map.get("ymiaoshu"));
        holder.itemtvtype.setText(map.get("ysex"));
        holder.itemtvtip.setText(map.get("ychefei"));
        holder.itembtorderstatus.setText(map.get("zhuangtai"));
        if(map.get("userObjectId").equals(sp.getString("objectId","null"))){
            holder.itemivorderTag.setImageDrawable(mContext.getResources().getDrawable(R.drawable.fa));
        }else{
        holder.itemivorderTag.setImageDrawable(mContext.getResources().getDrawable(R.drawable.jie));
        }
        final String objectId = map.get("objectId");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("OrderObjectId", objectId);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


}
