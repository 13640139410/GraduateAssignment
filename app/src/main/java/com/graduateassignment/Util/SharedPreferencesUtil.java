package com.graduateassignment.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.graduateassignment.DB.ArticleCategory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by admin on 2020/2/25.
 */

public class SharedPreferencesUtil {

    public static final String ARTICLE_CATE_KEY = "articleCategorys";
    public static final String articleCategoryFileName = "articleCategory";
    private static final String spFileName = "welcomePage";
    public static final String FIRST_OPEN = "first_open";

    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {//strDefault	boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }

    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

    /**
     * 将数据库的文章类别存入缓存中
     * @param context
     */
    public static void putArticleCategory(final Context context,Set<String> articleCategorys){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                articleCategoryFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(ARTICLE_CATE_KEY, articleCategorys);
        editor.commit();
    }

    public static Set<String> getArticleCategory(final Context context){
        Set<String> result = new HashSet<>();
        SharedPreferences setPreferences = context.getSharedPreferences(
                articleCategoryFileName, Context.MODE_PRIVATE);
        result = setPreferences.getStringSet(ARTICLE_CATE_KEY, null);
        return result;
    }

}

