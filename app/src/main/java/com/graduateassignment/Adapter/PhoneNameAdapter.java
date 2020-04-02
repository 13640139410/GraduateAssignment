package com.graduateassignment.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.PhoneModel;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/4/2.
 */

public class PhoneNameAdapter extends BaseQuickAdapter<PhoneModel, BaseViewHolder> {
    public PhoneNameAdapter(int layoutResId, List<PhoneModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhoneModel item) {
        //设置品牌名称
        helper.setText(R.id.item_phone_txt,item.getName());
    }
}
