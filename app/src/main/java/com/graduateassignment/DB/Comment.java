package com.graduateassignment.DB;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by admin on 2020/3/18.
 */

public class Comment extends BmobObject{
    private Comment comment;//子评论所附属的父评论
    private String commentLevel;//评论等级
    private String content;//评论内容
    private User commentator;//进行评论的用户
    private Article article;//该评论所附属的文章
    private BmobRelation likeNum;//评论点赞数
    private String beRepliedUser;//被回复的评论所属的用户名称

    public Comment getComment() {
        return comment;
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public User getCommentator() {
        return commentator;
    }
    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }
    public Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        this.article = article;
    }
    public BmobRelation getLikeNum() {
        return likeNum;
    }
    public void setLikeNum(BmobRelation likeNum) {
        this.likeNum = likeNum;
    }
    public String getCommentLevel() {
        return commentLevel;
    }
    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }
    public String getBeRepliedUser() {
        return beRepliedUser;
    }
    public void setBeRepliedUser(String beRepliedUser) {
        this.beRepliedUser = beRepliedUser;
    }
}
