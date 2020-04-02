package com.graduateassignment.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/4/2.
 */

public class PhoneBrandAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public PhoneBrandAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //设置品牌名称
        helper.setText(R.id.item_mobilebrand_txt,item);
    }
}
