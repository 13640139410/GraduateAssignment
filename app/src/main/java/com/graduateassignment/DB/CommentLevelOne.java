package com.graduateassignment.DB;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.graduateassignment.Adapter.CommentAdapter;

/**
 * Created by admin on 2020/3/18.
 */

public class CommentLevelOne extends AbstractExpandableItem<CommentLevelTwo> implements MultiItemEntity{

    public Comment comment;

    public CommentLevelOne(Comment comment){
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
        return 1;
    }

    @Override
    public int getLevel() {
        return CommentAdapter.LEVEL_ONE;
    }
}
