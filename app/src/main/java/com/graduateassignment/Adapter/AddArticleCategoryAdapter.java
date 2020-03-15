package com.graduateassignment.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/3/11.
 */

public class AddArticleCategoryAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public AddArticleCategoryAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_articlecategory,item);
    }
}
