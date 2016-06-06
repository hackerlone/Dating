package com.lone.wjm.dating.Ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Prosenter.OrderDetailProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.IOrderDetailView;
import com.lone.wjm.dating.Util.Options;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Map;

/**
 * Created by: Lone on 2016/6/3.
 * Contact: 4951048@qq.com
 */
public class OrderDetailActivity extends Activity implements View.OnClickListener, IOrderDetailView {
    //region Description
    private TextView tv_detail_title;
    private TextView tv_order_status;
    private TextView tv_order_distance;
    private ImageView ib_head_left;
    private TextView tv_name_left;
    private ImageView iv_sex_left;
    private ImageView ib_head_right;
    private TextView tv_name_right;
    private ImageView iv_sex_right;
    private TextView tv_date_title;
    private TextView tv_date_time;
    private TextView tv_date_location;
    private TextView tv_date_amount;
    private TextView tv_date_tip;
    private TextView tv_date_remark;
    private LinearLayout ll_call;
    private LinearLayout ll_sms;
    private Button bt_order_pay;
    private Button bt_order_cancel;
    private Button bt_order_confirm;
    private String OrderObjectId;
    private OrderDetailProsenter mOrderDetailProsenter;
    private SharedPreferences sp;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();
        OrderObjectId = getIntent().getStringExtra("OrderObjectId");
        mOrderDetailProsenter = new OrderDetailProsenter(this, this);
        mOrderDetailProsenter.getYueDanInfoById(OrderObjectId);
        sp = ((MyApplication) getApplication()).getSp();
    }

    @Override
    public void getYueDanInfoById(Map<String, String> OrderInfo) {
        if (OrderInfo != null) {
            Log.i("info", "getYueDanInfoById: " + OrderInfo.get("ymiaoshu"));
            tv_detail_title.setText("约单详情");
            tv_order_status.setText(OrderInfo.get("zhuangtai"));
            tv_date_title.setText(OrderInfo.get("ymiaoshu"));
            tv_date_time.setText(OrderInfo.get("yshijian"));
            tv_date_location.setText(OrderInfo.get("ydidian"));
            tv_date_amount.setText(OrderInfo.get("yxiaofei"));
            tv_date_tip.setText(OrderInfo.get("ychefei"));
            tv_date_remark.setText(OrderInfo.get("ybeizhu"));
            //获取发单人信息
            String userObjectId = OrderInfo.get("userObjectId");

            //获取接单人信息
            String TouserObjectId = OrderInfo.get("TouserObjectId");

            //控件按钮显示
            if (OrderInfo.get("zhuangtai").equals("待接受")) {
                mOrderDetailProsenter.getYueDanUserInfoById(userObjectId);
                ll_call.setVisibility(View.GONE);
                ll_sms.setVisibility(View.GONE);
                bt_order_pay.setVisibility(View.GONE);
                bt_order_confirm.setVisibility(View.GONE);
                bt_order_cancel.setVisibility(View.VISIBLE);
            } else if (OrderInfo.get("zhuangtai").equals("已接受")) {
                OrderDetailProsenter mOrderDetailProsenter1 = new OrderDetailProsenter(this, this);
                OrderDetailProsenter mOrderDetailProsenter2 = new OrderDetailProsenter(this, this);
                mOrderDetailProsenter1.getYueDanUserInfoById(userObjectId);
                mOrderDetailProsenter2.getYueDanToUserInfoById(TouserObjectId);
                ll_call.setVisibility(View.VISIBLE);
                ll_sms.setVisibility(View.VISIBLE);
                bt_order_cancel.setVisibility(View.GONE);
                bt_order_pay.setVisibility(View.VISIBLE);
                bt_order_confirm.setVisibility(View.VISIBLE);
            } else if (OrderInfo.get("zhuangtai").equals("已完成")) {
                OrderDetailProsenter mOrderDetailProsenter1 = new OrderDetailProsenter(this, this);
                OrderDetailProsenter mOrderDetailProsenter2 = new OrderDetailProsenter(this, this);
                mOrderDetailProsenter1.getYueDanUserInfoById(userObjectId);
                mOrderDetailProsenter2.getYueDanToUserInfoById(TouserObjectId);
                ll_call.setVisibility(View.GONE);
                ll_sms.setVisibility(View.GONE);
                bt_order_cancel.setVisibility(View.GONE);
                bt_order_pay.setVisibility(View.GONE);
                bt_order_confirm.setVisibility(View.GONE);
            } else if (OrderInfo.get("zhuangtai").equals("已取消")) {
                mOrderDetailProsenter.getYueDanUserInfoById(userObjectId);
                ll_call.setVisibility(View.GONE);
                ll_sms.setVisibility(View.GONE);
                bt_order_cancel.setVisibility(View.GONE);
                bt_order_pay.setVisibility(View.GONE);
                bt_order_confirm.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getYueDanUserInfoById(final Map<String, String> OrderInfo) {
        if (OrderInfo != null) {
            tv_name_left.setText(OrderInfo.get("username"));
            ImageLoader.getInstance().displayImage(OrderInfo.get("userHead"), ib_head_left, Options.getListOptions());
            if (OrderInfo.get("sex").equals("男")) {
                iv_sex_left.setImageDrawable(getResources().getDrawable(R.drawable.boy));
            } else if (OrderInfo.get("sex").equals("男")) {
                iv_sex_left.setImageDrawable(getResources().getDrawable(R.drawable.girl));
            }
            //region Description
            String userPhoneNum = sp.getString("phoneNum", "null");//当前用户电话号码
            if (!OrderInfo.get("phoneNum").equals(userPhoneNum)) {
                ll_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + OrderInfo.get("phoneNum")));
                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }

                });

                ll_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+OrderInfo.get("phoneNum"));
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "来自约会吧：");
                        startActivity(intent);
                    }
                });
            }
            //endregion
        }
    }

    @Override
    public void getYueDanToUserInfoById(final Map<String, String> OrderInfo1) {
        if (OrderInfo1 != null) {
            tv_name_right.setText(OrderInfo1.get("username"));
            ImageLoader.getInstance().displayImage(OrderInfo1.get("userHead"), ib_head_right, Options.getListOptions());
            if (OrderInfo1.get("sex").equals("男")) {
                iv_sex_right.setImageDrawable(getResources().getDrawable(R.drawable.boy));
            } else if (OrderInfo1.get("sex").equals("男")) {
                iv_sex_right.setImageDrawable(getResources().getDrawable(R.drawable.girl));
            }
            //拨打电话
            //region Description
            String userPhoneNum = sp.getString("phoneNum", "null");//当前用户电话号码
            if (!OrderInfo1.get("phoneNum").equals(userPhoneNum)) {
                ll_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + OrderInfo1.get("phoneNum")));
                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }

                });
                ll_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("smsto:"+OrderInfo1.get("phoneNum"));
                        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                        intent.putExtra("sms_body", "来自约会吧：");
                        startActivity(intent);
                    }
                });
            }
            //endregion
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_order_pay:

                break;
            case R.id.bt_order_cancel://取消
                mOrderDetailProsenter.quxiaoYueDan(OrderObjectId);
                finish();
                break;
            case R.id.bt_order_confirm://确认
                mOrderDetailProsenter.querenYueDan(OrderObjectId);
                finish();
                break;
        }
    }

    private void initView() {
        tv_detail_title = (TextView) findViewById(R.id.tv_detail_title);//标题
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);//状态
        tv_order_distance = (TextView) findViewById(R.id.tv_order_distance);//发单人与约单人位置距离
        ib_head_left = (ImageView) findViewById(R.id.ib_head_left);//发单人头像
        tv_name_left = (TextView) findViewById(R.id.tv_name_left);//发单人昵称
        iv_sex_left = (ImageView) findViewById(R.id.iv_sex_left);//发单人性别
        ib_head_right = (ImageView) findViewById(R.id.ib_head_right);//接单人头像
        tv_name_right = (TextView) findViewById(R.id.tv_name_right);//接单人昵称
        iv_sex_right = (ImageView) findViewById(R.id.iv_sex_right);//接单人性别
        tv_date_title = (TextView) findViewById(R.id.tv_date_title);//约会主题
        tv_date_time = (TextView) findViewById(R.id.tv_date_time);//约会时间
        tv_date_location = (TextView) findViewById(R.id.tv_date_location);//约会地址
        tv_date_amount = (TextView) findViewById(R.id.tv_date_amount);//人均消费
        tv_date_tip = (TextView) findViewById(R.id.tv_date_tip);//报销车费
        tv_date_remark = (TextView) findViewById(R.id.tv_date_remark);//说明
        ll_call = (LinearLayout) findViewById(R.id.ll_call);//打电话按钮
        ll_sms = (LinearLayout) findViewById(R.id.ll_sms);//发短信按钮
        bt_order_pay = (Button) findViewById(R.id.bt_order_pay);//支付
        bt_order_cancel = (Button) findViewById(R.id.bt_order_cancel);//取消
        bt_order_confirm = (Button) findViewById(R.id.bt_order_confirm);//确认

        ib_head_left.setOnClickListener(this);
        ib_head_right.setOnClickListener(this);
        bt_order_pay.setOnClickListener(this);
        bt_order_cancel.setOnClickListener(this);
        bt_order_confirm.setOnClickListener(this);


    }

    public void back(View v) {
        finish();
    }

    @Override
    public void showToastMessage(String msg) {
        Toast.makeText(OrderDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
