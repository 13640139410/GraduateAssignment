package com.graduateassignment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView backImageView;
    private EditText emailEditText;
    private Button sureButton;
    private TextView tipsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        backImageView = (ImageView) findViewById(R.id.act_forget_back);
        emailEditText = (EditText) findViewById(R.id.act_forget_email);
        sureButton = (Button) findViewById(R.id.act_forget_sure);
        tipsTextView = (TextView) findViewById(R.id.act_forget_tips);

        backImageView.setOnClickListener(this);
        sureButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_forget_back:
                backToLogin();
                break;
            case R.id.act_forget_sure:
                commitEmail();
                break;
            default:
                break;
        }
    }

    private void backToLogin(){
        finish();
    }

    private void commitEmail(){
        final String email = emailEditText.getText().toString();
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    tipsTextView.setText("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                } else {
                    tipsTextView.setText(getResources().getString(R.string.act_forget_fail));
                    ToastUtil.show(ForgetActivity.this,getResources().getString(R.string.act_forget_fail)+"\n"+e.getMessage());
                }
            }
        });
    }
}
