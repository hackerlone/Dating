package com.lone.wjm.dating.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lone.wjm.dating.R;

import java.util.List;

/**
 * Created by: Lone on 2016/5/30.
 * Contact: 4951048@qq.com
 */
public class lv_mineAdapter extends BaseAdapter {
    private List<String>mList;
    private List<Integer>imgList;
    private Context mContext;
    private LayoutInflater mInflater;
    public lv_mineAdapter(Context mContext,List<String>mList,List<Integer>imgList) {
        this.mContext = mContext;
        this.mList = mList;
        this.imgList = imgList;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder {
        public final ImageView img;
        public final TextView texts;
        public final ImageView imgs;
        public final View root;
        public ViewHolder(View root) {
            img = (ImageView) root.findViewById(R.id.img);
            texts = (TextView) root.findViewById(R.id.texts);
            imgs = (ImageView) root.findViewById(R.id.imgs);
            this.root = root;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder hover;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_iv_mine,null);
            hover = new ViewHolder(convertView);
            convertView.setTag(hover);
        }else{
            hover = (ViewHolder) convertView.getTag();
        }
        hover.img.setImageDrawable(mContext.getResources().getDrawable(imgList.get(position)));
        hover.texts.setText(mList.get(position));
        hover.imgs.setImageDrawable(mContext.getResources().getDrawable(R.drawable.right));
        return convertView;
    }


}
