package com.graduateassignment.Adapter;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.DB.Comment;
import com.graduateassignment.DB.CommentLevelOne;
import com.graduateassignment.DB.CommentLevelTwo;
import com.graduateassignment.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2020/3/18.
 */

public class CommentAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Context mContext;
    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;

    public CommentAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(LEVEL_ONE, R.layout.item_comment_level1);
        addItemType(LEVEL_TWO, R.layout.item_comment_level2);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()){
            case LEVEL_ONE:
                helper.addOnClickListener(R.id.item_comment_level1_icon);//给二级评论的用户头像添加点击事件;
                helper.addOnClickListener(R.id.item_comment_level1_like);//给二级评论的点赞按钮添加点击事件;
                helper.addOnClickListener(R.id.item_comment_level1_reply);//给二级评论的评论按钮添加点击事件;
                CommentLevelOne commentLevelOne = (CommentLevelOne) item;
                Comment comment = commentLevelOne.getComment();
                //赋值头像
                Glide.with(mContext).load(comment.getCommentator().getAvator().getUrl()).crossFade().into((CircleImageView) helper.getView(R.id.item_comment_level1_icon));
                //赋值用户名称
                helper.setText(R.id.item_comment_level1_icon_name,comment.getCommentator().getUsername());
                Log.d("asd",comment.getCommentator().getUsername());
                //赋值评论内容
                helper.setText(R.id.item_comment_level1_content,comment.getContent());
                //赋值评论日期
                helper.setText(R.id.item_comment_level1_date,comment.getCreatedAt());
                //赋值点赞数
                //helper.setText(R.id.item_comment_level1_likenum,comment.getLikeNum().size());
                break;
            case LEVEL_TWO:
                helper.addOnClickListener(R.id.item_comment_level2_icon);//给二级评论的用户头像添加点击事件;
                helper.addOnClickListener(R.id.item_comment_level2_like);//给二级评论的点赞按钮添加点击事件;
                helper.addOnClickListener(R.id.item_comment_level2_reply);//给二级评论的评论按钮添加点击事件;
                CommentLevelTwo commentLevelTwo = (CommentLevelTwo) item;
                Comment comment2 = commentLevelTwo.getComment();
                //赋值头像
                Glide.with(mContext).load(comment2.getCommentator().getAvator().getUrl()).crossFade().into((CircleImageView) helper.getView(R.id.item_comment_level2_icon));
                //赋值用户名称
                helper.setText(R.id.item_comment_level2_icon_name,comment2.getCommentator().getUsername() + " 回复 " +comment2.getBeRepliedUser());
                //赋值评论内容
                helper.setText(R.id.item_comment_level2_content,comment2.getContent());
                //赋值评论日期
                helper.setText(R.id.item_comment_level2_date,comment2.getCreatedAt());
                //赋值点赞数
                //helper.setText(R.id.item_comment_level1_likenum,comment2.getLikeNum().size());
                break;
        }
    }
}
