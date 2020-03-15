package com.graduateassignment.Adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/3/15.
 */

public class ArticleAdapter extends BaseQuickAdapter<Article,BaseViewHolder> {

    public ArticleAdapter(int layoutResId, List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        //设置封面
        Glide.with(mContext).load(item.getThumbnail_pic_s()).crossFade().into((ImageView) helper.getView(R.id.item_article_cover));
        //设置标题
        helper.setText(R.id.item_article_title,item.getTitle());
        //设置作者
        helper.setText(R.id.item_article_author,item.getAuthor().getUsername());
        //设置时间
        helper.setText(R.id.item_article_time,item.getUpdatedAt());
    }
}
