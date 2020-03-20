package com.graduateassignment.DB;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by admin on 2020/3/18.
 */

public class CommentLevelTwo implements MultiItemEntity{

    public Comment comment;

    public CommentLevelTwo(Comment comment){
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
