package com.lone.wjm.dating.Ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.IMainView;
import com.lone.wjm.dating.Ui.fragment.DataFragment;
import com.lone.wjm.dating.Ui.fragment.MSHFragment;
import com.lone.wjm.dating.Ui.fragment.MineFragment;
import com.lone.wjm.dating.Ui.fragment.OrderFragment;
import com.lone.wjm.dating.Ui.fragment.TXLFragment;

public class MainActivity extends AppCompatActivity implements IMainView {
    private DataFragment mDataFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mDataFragment = new DataFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content, new MSHFragment());
        ft.commit();
        rg_content_fragment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (checkedId) {
                    case R.id.rb_content_fragment_home://约单
                        ft.replace(R.id.content, mDataFragment);
                        ft.commit();
                        break;
                    case R.id.rb_content_fragment_order://定单
                        Toast.makeText(MainActivity.this, "定单", Toast.LENGTH_SHORT).show();
                        ft.replace(R.id.content, new OrderFragment());
                        ft.commit();
                        break;
                    case R.id.rb_content_fragment_food://美食汇.
                        ft.replace(R.id.content, new MSHFragment());
                        ft.commit();
                        Toast.makeText(MainActivity.this, "美食汇", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_content_fragment_contacts://通讯录
                        ft.replace(R.id.content, new TXLFragment());
                        ft.commit();
                        Toast.makeText(MainActivity.this, "通讯录", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_content_fragment_mine://我的
                        ft.replace(R.id.content, new MineFragment());
                        ft.commit();
                        Toast.makeText(MainActivity.this, "我的", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }

    private void initView() {
        content = (FrameLayout) findViewById(R.id.content);
        rg_content_fragment = (RadioGroup) findViewById(R.id.rg_content_fragment);
    }

    /*变量声明区*/
    private FrameLayout content;
    private RadioGroup rg_content_fragment;
}
