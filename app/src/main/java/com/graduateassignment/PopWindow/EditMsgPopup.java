package com.graduateassignment.PopWindow;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by admin on 2020/3/28.
 */

public class EditMsgPopup extends BasePopupWindow {

    public static int CHANGE_NAME = 0;
    public static int CHANGE_SIGNTURE = 1;
    public static int CHANGE_PWD = 2;
    public static int CHANGE_PHONE = 3;
    public static int CHANGE_EMAIL = 4;
    private static String C_NAME = "修改昵称";
    private static String C_SIGNATURE = "修改签名";
    private static String C_PWD = "修改密码";
    private static String C_PHONE = "修改手机";
    private static String C_EMAIL = "修改邮箱";
    private int operate_code = -1;

    public int getOperate_code() { return operate_code;}
    public void setOperate_code(int operate_code) { this.operate_code = operate_code; }

    public EditMsgPopup(Context context) {
        super(context);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        Animation showAnimation = new ScaleAnimation(0, 1f, 0, 1f);
        showAnimation.setDuration(200);
        return showAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation showAnimation = new ScaleAnimation(1f, 0, 1f, 0);
        showAnimation.setDuration(200);
        return showAnimation;
    }

    // 必须实现，这里返回您的contentView
    // 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
    @Override
    public View onCreateContentView() {
        View view = createPopupById(R.layout.window_editmymsg);
        return view;
    }

    public void initView(EditMsgPopup editMsgPopup,int operate_code,User user){
        View view = editMsgPopup.getContentView();
        switch (operate_code){
            case 0://名称
                setWindowTxt(view,C_NAME,user);
                break;
            case 1://签名
                setWindowTxt(view,C_SIGNATURE,user);
                break;
            case 2://修改密码
                setWindowTxt(view,C_PWD,user);
                break;
            case 3://手机号码
                setWindowTxt(view,C_PHONE,user);
                break;
            case 4://邮箱
                setWindowTxt(view,C_EMAIL,user);
                break;
        }
    }

    /**
     * 设置编辑信息窗口的文字
     * @param view 编辑窗口
     * @param titleTxt 窗口标题
     * @param user 要修改的用户
     */
    private void setWindowTxt(View view,String titleTxt,User user){
        TextView title = (TextView) view.findViewById(R.id.window_editmsg_title); //窗口标题栏
        EditText editText1 = (EditText)  view.findViewById(R.id.window_editmsg_edittext);//输入栏1
        EditText editText2 = (EditText)  view.findViewById(R.id.window_editmsg_edittext2);//输入栏2
        title.setText(titleTxt);
        switch (titleTxt){
            case "修改昵称":
                editText2.setVisibility(View.GONE);
                editText1.setInputType(InputType.TYPE_CLASS_TEXT);
                editText1.setText(user.getUsername());
                break;
            case "修改签名":
                editText2.setVisibility(View.GONE);
                editText1.setInputType(InputType.TYPE_CLASS_TEXT);
                editText1.setText(user.getSignature()==null?"这个人很懒，什么都没写":user.getSignature());
                break;
            case "修改密码":
                editText1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText2.setVisibility(View.VISIBLE);
                editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText1.setText("");                 editText2.setText("");
                editText1.setHint("请输入旧密码");    editText2.setHint("请输入新密码");
                break;
            case "修改手机":
                editText2.setVisibility(View.GONE);
                editText1.setInputType(InputType.TYPE_CLASS_PHONE);
                editText1.setText(user.getMobilePhoneNumber());
                break;
            case "修改邮箱":
                editText2.setVisibility(View.GONE);
                editText1.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                editText1.setText(user.getEmail());
                break;
        }
    }

    public void setClickListener(final EditMsgPopup editMsgPopup, int operate_code){
        View view = editMsgPopup.getContentView();
        TextView title = (TextView) view.findViewById(R.id.window_editmsg_title); //窗口标题栏
        EditText editText1 = (EditText)  view.findViewById(R.id.window_editmsg_edittext);//输入栏1
        EditText editText2 = (EditText)  view.findViewById(R.id.window_editmsg_edittext2);//输入栏2
        Button sureBtn = (Button) view.findViewById(R.id.window_editmsg_surebtn);//确认按钮
        Button cancelBtn = (Button) view.findViewById(R.id.window_editmsg_cancelbtn);//取消按钮
        switch (operate_code){
            case 0://名称
                final String name = editText1.getText().toString();
                sureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final User user = BmobUser.getCurrentUser(User.class);
                        ToastUtil.show(editMsgPopup.getContext(),name);
                        user.setUsername(name);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    editMsgPopup.dismiss();
                                    ToastUtil.show(editMsgPopup.getContext(),"成功修改昵称");
                                }else{ ToastUtil.show(editMsgPopup.getContext(),"修改昵称失败"); }
                            }
                        });
                    }
                });
                break;
            case 1://签名
                break;
            case 2://修改密码
                break;
            case 3://手机号码
                break;
            case 4://邮箱
                break;
        }
    }
}
