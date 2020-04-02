package com.graduateassignment.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.FileUtil;
import com.graduateassignment.Util.ToastUtil;
import com.graduateassignment.Util.UiUtil;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.graduateassignment.Util.FileUtil.CHOOSE_PHOTO;

public class ChooseIconActivity extends AppCompatActivity implements View.OnClickListener{

    private User mUser;
    private ImageView icon;      private Button changeBtn;
    private Button sureBtn;     private Button cancelBtn;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_icon);
        mUser = (User) getIntent().getSerializableExtra("user");
        initView();
    }

    private void initView(){
        icon = (ImageView) findViewById(R.id.act_chooseicon_img);
        changeBtn = (Button) findViewById(R.id.act_chooseicon_change);
        sureBtn = (Button) findViewById(R.id.act_chooseicon_sure);
        cancelBtn = (Button) findViewById(R.id.act_chooseicon_cancel);
        changeBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.act_chooseicon_imglayout);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtil.getScreenWidth(this)));
        Glide.with(this).load(mUser.getAvator().getUrl()).crossFade().into(icon);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        imgPath = FileUtil.handleImageOnKitKat(this,data);
                        FileUtil.displayImage(this,icon,imgPath);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        imgPath = FileUtil.handleImageBeforeKitKat(this,data);
                        FileUtil.displayImage(this,icon,imgPath);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FileUtil.openAlbum(ChooseIconActivity.this);
            } else {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_chooseicon_change:
                if (ContextCompat.checkSelfPermission(ChooseIconActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChooseIconActivity.this, new
                            String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    FileUtil.openAlbum(ChooseIconActivity.this);
                }
                break;
            case R.id.act_chooseicon_sure:
                storeImage(imgPath);
                break;
            case R.id.act_chooseicon_cancel:
                finish();
                break;
        }
    }

    private void storeImage(String imgPath){
        File saveFile = new File(imgPath);
        final BmobFile file = new BmobFile(saveFile);
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null) {
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setAvator(file);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                ToastUtil.show(ChooseIconActivity.this,"更新头像成功");
                            }else {
                                ToastUtil.show(ChooseIconActivity.this,e.getMessage());
                            }
                        }
                    });
                }
                else
                    Log.d("TestRichEditorActivity","error:"+e.getMessage());
            }

        });
    }
}
