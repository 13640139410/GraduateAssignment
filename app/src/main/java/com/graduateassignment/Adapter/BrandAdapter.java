package com.graduateassignment.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/2/25.
 */

public class BrandAdapter extends BaseQuickAdapter<Brand, BaseViewHolder>{

    public BrandAdapter(int layoutResId, List<Brand> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Brand item) {
        helper.setText(R.id.brand_text, item.getName());
        helper.setImageResource(R.id.brand_icon, item.getIcon());
    }


}
