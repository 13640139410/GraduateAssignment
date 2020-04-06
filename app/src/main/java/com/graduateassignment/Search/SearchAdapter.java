package com.graduateassignment.Search;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.Article;
import com.graduateassignment.R;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2020/4/7.
 */

public class SearchAdapter extends BaseQuickAdapter<BmobObject,BaseViewHolder> {

    private String search_cate = "";

    public SearchAdapter(int layoutResId, List<BmobObject> data,String search_cate){
        super(layoutResId,data);
        this.search_cate = search_cate;
    }

    @Override
    protected void convert(BaseViewHolder helper, BmobObject item) {
        if(search_cate.equalsIgnoreCase(SearchActivity.SEARCH_ARTICLE)){
            Article article = (Article) item;
            Glide.with(mContext).load(article.getThumbnail_pic_s()).crossFade().into((ImageView) helper.getView(R.id.item_article_cover));
            helper.setText(R.id.item_article_title,article.getTitle());
            helper.setText(R.id.item_article_author,article.getArticleCates().get(0) + " · " + article.getAuthor().getUsername());
        }
    }
}
