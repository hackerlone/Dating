package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lone.wjm.dating.Adapter.MyOrderAdapter;
import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Prosenter.MyOrderProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.IMyOrderView;

import java.util.List;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/2.
 * Contact: 4951048@qq.com
 */
public class MyOrderActivity extends Activity implements IMyOrderView {
    private ListView lv_our_order;
    private TextView nullYuedan;
    private SharedPreferences sp;
    private MyOrderProsenter myOrderProsenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_order);
        initView();
        sp = ((MyApplication)getApplication()).getSp();
        String objectId = sp.getString("objectId","null");
        myOrderProsenter = new MyOrderProsenter(objectId,this);
        myOrderProsenter.getYueDan();
    }
    @Override
    public void getYueDan(List<Map<String, String>> list) {

        if(list.size()==0){
            nullYuedan.setVisibility(View.VISIBLE);
        }else {
            nullYuedan.setVisibility(View.GONE);
        MyOrderAdapter adapter = new MyOrderAdapter(this,list,this);
        lv_our_order.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        }
    }
    private void initView() {
        lv_our_order = (ListView) findViewById(R.id.lv_our_order);
        nullYuedan = (TextView) findViewById(R.id.nullYuedan);
    }

    public void back(View v){
        finish();
    }



    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(MyOrderActivity.this, "msg", Toast.LENGTH_SHORT).show();
    }
}
