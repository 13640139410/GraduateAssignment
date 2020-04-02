package com.graduateassignment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.graduateassignment.Activity.ChooseIconActivity;
import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.DB.User;
import com.graduateassignment.PopWindow.EditMsgPopup;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;
import com.graduateassignment.Util.UserUtil;

import java.util.UUID;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import razerdp.widget.QuickPopup;

import static com.graduateassignment.PopWindow.EditMsgPopup.CHANGE_EMAIL;
import static com.graduateassignment.PopWindow.EditMsgPopup.CHANGE_NAME;
import static com.graduateassignment.PopWindow.EditMsgPopup.CHANGE_PHONE;
import static com.graduateassignment.PopWindow.EditMsgPopup.CHANGE_PWD;
import static com.graduateassignment.PopWindow.EditMsgPopup.CHANGE_SIGNTURE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeMsgFragment extends Fragment implements View.OnClickListener{

    private User mUser;//传入的用户对象
    private LinearLayout linearLayout;//主要容器
    private TextView emptyTxt;//空布局文字
    private CircleImageView icon;//头像
    private TextView name;//名称
    private TextView signature;//签名
    private TextView phone;//手机
    private TextView email;//邮箱
    private ImageButton iconBtn;//头像编辑按钮
    private ImageButton nameBtn;//名称编辑按钮
    private ImageButton signatureBtn;//签名编辑按钮
    private ImageButton phoneBtn;//电话编辑按钮
    private ImageButton pwdBtn;//密码编辑按钮
    private ImageButton emailBtn;//邮箱编辑按钮
    private static final String ARG_PARAM1 = "param1";//获取传入的User对象
    private EditMsgPopup editMsgPopup;//编辑窗口
    private User newUser = new User();//用于缓存编辑用户信息后的信息

    public static Fragment newInstance(User user){
        MeMsgFragment meMsgFragment = new MeMsgFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,user);
        meMsgFragment.setArguments(args);
        return meMsgFragment;
    }

    public MeMsgFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_PARAM1);//获取传入的用户
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me_msg, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(UserUtil.isLogin())
        UserUtil.setUser(newUser,BmobUser.getCurrentUser(User.class));
        emptyTxt = (TextView) getActivity().findViewById(R.id.frag_memsg_emptytxt);//空布局文字
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.frag_memsg_layout);//容器布局
        icon = (CircleImageView) getActivity().findViewById(R.id.frag_memsg_icon);//头像
        name = (TextView) getActivity().findViewById(R.id.frag_memsg_name);//昵称
        signature = (TextView) getActivity().findViewById(R.id.frag_memsg_signature);//签名
        phone = (TextView) getActivity().findViewById(R.id.frag_memsg_phonetxt);//手机
        email = (TextView) getActivity().findViewById(R.id.frag_memsg_emailtxt);//邮箱

        iconBtn = (ImageButton) getActivity().findViewById(R.id.frag_memsg_iconedit);//头像编辑
        nameBtn = (ImageButton) getActivity().findViewById(R.id.frag_memsg_nameedit);//名称编辑
        signatureBtn = (ImageButton) getActivity().findViewById(R.id.frag_memsg_signatureedit);//签名编辑
        pwdBtn = (ImageButton) getActivity().findViewById(R.id.frag_memsg_pwdedit);//密码编辑
        phoneBtn = (ImageButton) getActivity().findViewById(R.id.frag_memsg_phoneedit);//电话编辑
        emailBtn = (ImageButton) getActivity().findViewById(R.id.frag_memsg_emailedit);//邮箱编辑

        iconBtn.setOnClickListener(this);
        nameBtn.setOnClickListener(this);
        pwdBtn.setOnClickListener(this);
        signatureBtn.setOnClickListener(this);
        phoneBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);
        initView();
    }

    private void initView(){
        //如果未登录
        if(mUser==null){
            emptyTxt.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }else {
            editMsgPopup = new EditMsgPopup(getContext());//初始化编辑窗口
            setClickListener(editMsgPopup);//设置编辑信息窗口点击事件
            Glide.with(this).load(mUser.getAvator().getUrl()).crossFade().into((CircleImageView) icon);//赋值头像
            name.setText(mUser.getUsername());
            signature.setText(mUser.getSignature()==null?"这个人很懒，什么都没写~~":mUser.getSignature());
            phone.setText("+86 "+ UserUtil.phoneToString(mUser.getMobilePhoneNumber()));
            email.setText(mUser.getEmail());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.frag_memsg_iconedit:
                Intent intent = new Intent(getActivity(), ChooseIconActivity.class);
                intent.putExtra("user",mUser);
                startActivity(intent);
                break;
            case R.id.frag_memsg_nameedit:
                initMsgPopup(editMsgPopup,CHANGE_NAME,newUser);//修改昵称
                break;
            case R.id.frag_memsg_emailedit:
                initMsgPopup(editMsgPopup,CHANGE_EMAIL,newUser);//修改邮箱
                break;
            case R.id.frag_memsg_phoneedit:
                initMsgPopup(editMsgPopup,CHANGE_PHONE,newUser);//修改电话
                break;
            case R.id.frag_memsg_pwdedit:
                initMsgPopup(editMsgPopup,CHANGE_PWD,newUser);//修改密码
                break;
            case R.id.frag_memsg_signatureedit:
                initMsgPopup(editMsgPopup,CHANGE_SIGNTURE,newUser);//修改签名
                break;
        }
    }

    /**
     * 初始化编辑信息窗口
     * @param editMsgPopup 编辑信息的窗口
     * @param operate_code 操作码
     * @param user 要修改的用户
     */
    private void initMsgPopup(EditMsgPopup editMsgPopup,int operate_code,User user){
        editMsgPopup.setOperate_code(operate_code);
        editMsgPopup.initView(editMsgPopup,operate_code,user);
        editMsgPopup.setPopupGravity(Gravity.CENTER);
        editMsgPopup.showPopupWindow();
    }

    private void setClickListener(final EditMsgPopup editMsgPopup){
        final User user = BmobUser.getCurrentUser(User.class);
        if(user==null) return;
        View view = editMsgPopup.getContentView();
        TextView title = (TextView) view.findViewById(R.id.window_editmsg_title); //窗口标题栏
        final EditText editText1 = (EditText)  view.findViewById(R.id.window_editmsg_edittext);//输入栏1
        final EditText editText2 = (EditText)  view.findViewById(R.id.window_editmsg_edittext2);//输入栏2
        Button sureBtn = (Button) view.findViewById(R.id.window_editmsg_surebtn);//确认按钮
        Button cancelBtn = (Button) view.findViewById(R.id.window_editmsg_cancelbtn);//取消按钮
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMsgPopup.dismiss();
            }
        });

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txt1 = editText1.getText().toString();
                switch (editMsgPopup.getOperate_code()){
                    case 0://名称
                        user.setUsername(txt1);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    newUser.setUsername(txt1);//更新本活动缓存
                                    editMsgPopup.dismiss();
                                    ToastUtil.show(getActivity(),"成功修改昵称");
                                    name.setText(txt1);
                                    TextView nametxt = (TextView) getActivity().findViewById(R.id.act_me_name);
                                    nametxt.setText(txt1);
                                }else{ ToastUtil.show(getActivity(),"修改昵称失败"); }
                            }
                        });
                        break;
                    case 1://签名
                        user.setSignature(txt1);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    newUser.setSignature(txt1);//更新本活动缓存
                                    editMsgPopup.dismiss();
                                    ToastUtil.show(getActivity(),"成功修改签名");
                                    signature.setText(txt1);
                                    TextView signaturetxt = (TextView) getActivity().findViewById(R.id.act_me_signature);
                                    signaturetxt.setText(txt1);
                                }else{ ToastUtil.show(getActivity(),"修改签名失败"); }
                            }
                        });
                        break;
                    case 2://修改密码
                        final String txt2 = editText2.getText().toString();
                        BmobUser.updateCurrentUserPassword(txt1, txt2, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    editMsgPopup.dismiss();
                                    ToastUtil.show(getActivity(),"成功修改密码");
                                }else{ ToastUtil.show(getActivity(),"修改密码失败"); }
                            }
                        });
                        break;
                    case 3://手机号码
                        user.setMobilePhoneNumber(txt1);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    newUser.setMobilePhoneNumber(txt1);//更新本活动缓存
                                    editMsgPopup.dismiss();
                                    ToastUtil.show(getActivity(),"成功修改手机号码");
                                    phone.setText(UserUtil.phoneToString(txt1));
                                }else{ ToastUtil.show(getActivity(),"修改手机号码失败，手机号码已存在或格式错误。"); }
                            }
                        });
                        break;
                    case 4://邮箱
                        newUser.setEmail(txt1);//更新本活动缓存
                        user.setEmail(txt1);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    editMsgPopup.dismiss();
                                    ToastUtil.show(getActivity(),"成功修改邮箱");
                                    email.setText(txt1);
                                }else{ ToastUtil.show(getActivity(),"修改邮箱失败，邮箱格式错误或邮箱已注册。"); }
                            }
                        });
                        break;
                }

            }
        });

    }
}
