package com.graduateassignment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private ImageView backImageView;
    private EditText accountName;
    private EditText accountPwd;
    private EditText accountPwdSure;
    private EditText accountMail;
    private EditText accountPhone;
    private EditText accountSignature;
    private Button accountReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //绑定控件
        backImageView = (ImageView) findViewById(R.id.title_back);
        accountName = (EditText) findViewById(R.id.account_name);
        accountPwd = (EditText) findViewById(R.id.account_pwd);
        accountPwdSure = (EditText) findViewById(R.id.account_pwdsure);
        accountMail = (EditText) findViewById(R.id.account_mail);
        accountPhone = (EditText) findViewById(R.id.account_phone);
        accountSignature = (EditText) findViewById(R.id.account_signature);
        accountReg = (Button) findViewById(R.id.account_register);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        accountReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register() {
        String name = accountName.getText().toString();
        String pwd = accountPwd.getText().toString();
        String pwdsure = accountPwdSure.getText().toString();
        String mail = accountMail.getText().toString();
        String phone = accountPhone.getText().toString();
        String signature = accountSignature.getText().toString();
        //判断密码和确认密码是否相同
        if (!pwd.equalsIgnoreCase(pwdsure)){
            ToastUtil.show(this,"两次输入密码不同");
            return;
        }
        final User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);
        user.setEmail(mail);
        user.setMobilePhoneNumber(phone);
        user.setSignature(signature);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    ToastUtil.show(RegisterActivity.this,"注册成功");
                    finish();
                } else {
                    ToastUtil.show(RegisterActivity.this,e.getMessage());
                }
            }
        });
    }
}
