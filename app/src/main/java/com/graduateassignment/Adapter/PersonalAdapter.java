package com.graduateassignment.Adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2020/3/26.
 */

public class PersonalAdapter extends BaseQuickAdapter<User,BaseViewHolder> {

    public PersonalAdapter(int layoutResId, List<User> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        //给用户头像和关注按钮设置点击事件
        helper.addOnClickListener(R.id.item_personal_cardview_icon);
        helper.addOnClickListener(R.id.item_personal_cardview_focus);
        //设置用户头像
        Glide.with(mContext).load(item.getAvator().getUrl()).crossFade().into((CircleImageView) helper.getView(R.id.item_personal_cardview_icon));
        //设置用户名称
        helper.setText(R.id.item_personal_cardview_name,item.getUsername());
        //设置用户签名
        helper.setText(R.id.item_personal_cardview_signature,item.getSignature());
    }
}
