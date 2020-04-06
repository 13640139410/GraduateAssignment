package com.graduateassignment.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.ArticleCategory;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by admin on 2020/3/15.
 */

public class ArticleAdapter extends BaseQuickAdapter<Article,BaseViewHolder> {

    private List<ArticleCategory> articleCategories;

    public ArticleAdapter(int layoutResId, List<Article> data,List<ArticleCategory> articleCategories) {
        super(layoutResId, data);
        this.articleCategories = articleCategories;
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        //设置封面
        Glide.with(mContext).load(item.getThumbnail_pic_s()).crossFade().into((ImageView) helper.getView(R.id.item_article_cover));
        //设置标题
        helper.setText(R.id.item_article_title,item.getTitle());
        //设置作者
        //ArticleCategory articleCategory = (ArticleCategory)item.getArticleCategorys().getObjects().get(0)
        helper.setText(R.id.item_article_author,item.getArticleCates().get(0) + " · " + item.getAuthor().getUsername());
        //设置时间
        helper.setText(R.id.item_article_time,item.getUpdatedAt());
    }
}
