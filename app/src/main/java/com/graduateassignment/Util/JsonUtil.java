package com.graduateassignment.Util;

import android.util.Log;

import com.graduateassignment.Json.Article;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by admin on 2020/2/28.
 */

public class JsonUtil {
    public static List<Article> parseArticleJson(String jsonData){
        List<Article> articleList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray("["+jsonData+"]");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            JSONArray jsonArray1 = new JSONArray("["+jsonObject.getString("result")+"]");
            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

            JSONArray jsonArray2 = new JSONArray(jsonObject1.getString("data"));
            for (int i=0;i<jsonArray2.length();i++){
                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                Article article = new Article();
                article.setTitle(jsonObject2.getString("title"));
                article.setDate(jsonObject2.getString("date"));
                article.setAuthor_name(jsonObject2.getString("author_name"));
                article.setUrl(jsonObject2.getString("url"));
                article.setThumbnail_pic_s(jsonObject2.optString("thumbnail_pic_s"));
                article.setThumbnail_pic_s02(jsonObject2.optString("thumbnail_pic_s02"));
                article.setThumbnail_pic_s03(jsonObject2.optString("thumbnail_pic_s03"));
                articleList.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articleList;
    }

    /**
     * 将获取到的Json.Article集合转化为DB.Article集合
     */
    public static List<com.graduateassignment.DB.Article> convertToDBArticleList(List<Article> articles){
        List<com.graduateassignment.DB.Article> dbArticleList = new ArrayList<>();
        for (Article article:articles){
            com.graduateassignment.DB.Article dbArticle = new com.graduateassignment.DB.Article();
            dbArticle.setUrl(article.getUrl());//设置数据源URL
            if(article.getThumbnail_pic_s()!=null) {
                dbArticle.setThumbnail_pic_s(article.getThumbnail_pic_s());//默认选中第一张缩略图为封面图
            }
            dbArticle.setDate(new BmobDate(DateUtil.getDateFromJuheDate(article.getDate())));
            dbArticle.setTitle(article.getTitle());//设置标题
            dbArticle.setIsReptile("是");
            dbArticleList.add(dbArticle);
        }
        return dbArticleList;
    }
}
