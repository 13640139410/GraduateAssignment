package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2020/4/7.
 */

public class Message extends BmobObject {
    private String cate;//0 关注 1 点赞文章或评论 2 维修订单
    private User me;
    private User other;
    private Article article;
    private Comment comment;
    private RepairOrder repairOrder;
    private String isRead;

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public User getOther() {
        return other;
    }

    public void setOther(User other) {
        this.other = other;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
