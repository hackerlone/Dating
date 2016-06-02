package com.lone.wjm.dating.Ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lone.wjm.dating.Application.MyApplication;
import com.lone.wjm.dating.Prosenter.MineInfo_ActivityProsenter;
import com.lone.wjm.dating.R;
import com.lone.wjm.dating.Ui.IMineInfo_ActivityView;
import com.lone.wjm.dating.Util.FileUtil;
import com.lone.wjm.dating.Util.Options;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Lone on 2016/6/1.
 * Contact: 4951048@qq.com
 */
public class MineInfo_Activity extends Activity implements View.OnClickListener,IMineInfo_ActivityView{
    public static ImageView civ_info_icon;
    private EditText et_info_describe;
    private TextView tv_info_count;
    private EditText et_info_nick;
    private EditText et_info_birth;
    private EditText et_info_area;
    private RadioButton rb_sex1;
    private RadioButton rb_sex2;
    private RadioGroup rg_sex;
    private PopupWindow popupWindow;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    private String filename;
    private MineInfo_ActivityProsenter mineProsenter;
    private SharedPreferences sp;
    private Calendar c = null;
    private Dialog dialog = null;
    public static String sex = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        initView();
        sp = ((MyApplication)getApplication()).getSp();

        ImageLoader.getInstance().displayImage(sp.getString("userHead","http://ac-hlwakbmt.clouddn.com/1a9272ebaa4940fb.jpg"), civ_info_icon, Options.getListOptions());
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_sex1){
                    sex = "男";
                }else if(checkedId == R.id.rb_sex2){
                    sex = "女";
                }
            }
        });
        mineProsenter = new MineInfo_ActivityProsenter(this,this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_info_icon:
                LayoutInflater inflater = LayoutInflater.from(this);
                View headImg = inflater.inflate(R.layout.layout_comment, null);
                setPopupWindow(headImg, v);
                break;
            case R.id.et_info_birth://生日
                showDialog().show();
                break;
        }
    }
//TODO 保存用户信息更改
    public void save(View v) {
        String miaoshu = et_info_describe.getText().toString();
        String username = et_info_nick.getText().toString();
        String birthday = et_info_birth.getText().toString();
        String area = et_info_area.getText().toString();
        String sexsec = sex;
        Map<String,String>userInfo  = new HashMap<String,String>();
        userInfo.put("objectId",sp.getString("objectId","null"));
        userInfo.put("miaoshu",miaoshu);
        userInfo.put("username",username);
        userInfo.put("birthday",birthday);
        userInfo.put("area",area);
        userInfo.put("sexsec",sexsec);

        mineProsenter.saveUserInfo(userInfo);

    }
    //TODO 获取用户信息
    public void getUserInfo(){
        et_info_describe.setText(sp.getString("miaoshu","勤劳"));//描述
        et_info_describe.setSelection(sp.getString("miaoshu","勤劳").length());

        tv_info_count.setText(sp.getString("phoneNum","***"));//帐号

        et_info_nick.setText(sp.getString("username","**"));//昵称
        et_info_nick.setSelection(sp.getString("username","**").length());

        et_info_birth.setText(sp.getString("birthday","没有填写"));//生日

        et_info_area.setText(sp.getString("area","北京"));//地区
        et_info_area.setSelection(sp.getString("area","北京").length());
        if(sp.getString("sex","null").equals("男")){
        rb_sex1.setChecked(true);//男
        }else if(sp.getString("sex","null").equals("女")) {
            rb_sex2.setChecked(true);//女
        }
    }
    //上传头像图片
    public void uploadHeadImg(String picturePath,String filename){
        mineProsenter.uploadHeadImg(picturePath,filename);
    }
    public void paizhao(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }
    public void xiangce(View v) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = null;
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            //从相册取图片地址
            Uri selectedImage = data.getData();
            picturePath = FileUtil.getImagePathFromUri(this,selectedImage);
            SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
            filename = "MT" + (t.format(new Date())) + ".jpg";
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        if (requestCode == 2) {
            //相机获取图片
            Uri uri = data.getData();
            if (uri != null) {
                photo = BitmapFactory.decodeFile(uri.getPath());
            }
            if (photo == null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    photo = (Bitmap) bundle.get("data");
                    FileOutputStream fileOutputStream = null;
                    try {
                        // 获取 SD 卡根目录
                        String saveDir = Environment.getExternalStorageDirectory() + "/meitian_photos";
                        // 新建目录
                        File dir = new File(saveDir);
                        if (!dir.exists()) dir.mkdir();
                        // 生成文件名
                        SimpleDateFormat t = new SimpleDateFormat("yyyyMMddssSSS");
                        filename = "MT" + (t.format(new Date())) + ".jpg";
                        // 新建文件
                        File file = new File(saveDir, filename);
                        // 打开文件输出流
                        fileOutputStream = new FileOutputStream(file);
                        // 生成图片文件
                        photo.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        // 相片的完整路径
                        picturePath =file.getPath();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    Toast.makeText(MineInfo_Activity.this, "拍照失败", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        uploadHeadImg(picturePath,filename);
    }

    public void setPopupWindow(View contentView, View v) {
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.footbar_bg));
        // 设置好参数之后再show
        View rootview = LayoutInflater.from(MineInfo_Activity.this).inflate(R.layout.activity_my_info, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 8);
        popupWindow.showAsDropDown(v);
    }
    private void initView() {
        civ_info_icon = (ImageView) findViewById(R.id.civ_info_icon);
        et_info_describe = (EditText) findViewById(R.id.et_info_describe);
        tv_info_count = (TextView) findViewById(R.id.tv_info_count);
        et_info_nick = (EditText) findViewById(R.id.et_info_nick);
        et_info_birth = (EditText) findViewById(R.id.et_info_birth);
        et_info_area = (EditText) findViewById(R.id.et_info_area);
        rb_sex1 = (RadioButton) findViewById(R.id.rb_sex1);
        rb_sex2 = (RadioButton) findViewById(R.id.rb_sex2);
        rg_sex = (RadioGroup) findViewById(R.id.rg_sex);
        civ_info_icon.setOnClickListener(this);
        et_info_birth.setOnClickListener(this);
    }
    public void quxiao(View v) {
        popupWindow.dismiss();
    }
    public void back(View v) {
        finish();
    }

    @Override
    public void uploadHeadImg(boolean isipload) {

    }

    @Override
    public void saveUserInfo(boolean isSaved) {

    }

    public void showToastMessage(String msg) {
        Toast.makeText(MineInfo_Activity.this,msg, Toast.LENGTH_SHORT).show();
    }



    public Dialog showDialog(){
        c = Calendar.getInstance();
        dialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                        et_info_birth.setText(year + "年" + (month+1) + "月" + dayOfMonth + "日");
                    }
                },
                c.get(Calendar.YEAR), // 传入年份
                c.get(Calendar.MONTH), // 传入月份
                c.get(Calendar.DAY_OF_MONTH)// 传入天数
        );
        return dialog;
    }
}
