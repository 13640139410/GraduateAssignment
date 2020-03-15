package com.graduateassignment.DB;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by admin on 2020/2/28.
 */

public class Article extends BmobObject {
    private User author;//作者
    private String title;//标题
    private Content content;//文章内容
    private String url;//数据源URL
    private BmobRelation articleCategorys;//文章类别
    private BmobDate date;//发布文章的日期
    private Integer readNum;//阅读量
    private List<User> likeUsers;//喜欢的读者
    private String thumbnail_pic_s;//缩略图
    private String isReptile;//判断是网上爬虫还是用户生成

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BmobRelation getArticleCategorys() {
        return articleCategorys;
    }

    public void setArticleCategorys(BmobRelation articleCategorys) {
        this.articleCategorys = articleCategorys;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public List<User> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<User> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getIsReptile() {
        return isReptile;
    }

    public void setIsReptile(String isReptile) {
        this.isReptile = isReptile;
    }
}
