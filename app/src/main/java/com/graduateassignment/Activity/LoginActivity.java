package com.graduateassignment.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEditText;//账号栏
    private EditText pwdEditText;//密码栏
    private Button loginBtn;//登录按钮
    private Button regBtn;//注册按钮
    private TextView forgetTextView;//忘记密码栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取控件
        accountEditText = (EditText) findViewById(R.id.act_login_account);
        pwdEditText = (EditText) findViewById(R.id.act_login_pwd);
        loginBtn = (Button) findViewById(R.id.act_login_login);
        regBtn = (Button) findViewById(R.id.act_login_reg);
        forgetTextView = (TextView) findViewById(R.id.act_login_foeget);

        accountEditText.setOnClickListener(this);
        pwdEditText.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        forgetTextView.setOnClickListener(this);

        //如果用户不为空，默认跳转到主界面
        if(BmobUser.getCurrentUser(User.class)!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_login_login:
                loginByAccount();
                break;
            case R.id.act_login_reg:
                toRegisterActivity();
                break;
            case R.id.act_login_foeget:
                toForgetActivity();
                break;
        }
    }

    /**
     * 登录功能，登录成功后退回上一个活动
     */
    private void loginByAccount() {
        String account = accountEditText.getText().toString();
        String pwd = pwdEditText.getText().toString();
        //此处替换为你的用户名密码
        BmobUser.loginByAccount(account, pwd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    ToastUtil.show(LoginActivity.this,"登录成功："+user.getUsername());
                    LoginActivity.this.finish();
                } else {
                    ToastUtil.show(LoginActivity.this,"登陆失败，账号或者密码错误");
                }
            }
        });
    }

    /**
     * 前往忘记密码活动
     */
    private void toForgetActivity(){
        Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
        startActivity(intent);
    }

    /**
     * 前往注册活动
     */
    private void toRegisterActivity(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
