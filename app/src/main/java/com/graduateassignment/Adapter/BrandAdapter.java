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

//    private List<Brand> mBrandList;
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        View brandView;
//        ImageView brandImg;
//        TextView brandName;
//
//        public ViewHolder(View view) {
//            super(view);
//            brandView = view;
//            brandImg = (ImageView) view.findViewById(R.id.brand_icon);
//            brandName = (TextView) view.findViewById(R.id.brand_text);
//        }
//    }
//
//    public BrandAdapter(List<Brand> fruitList) {
//        mBrandList = fruitList;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand, parent, false);
//        final ViewHolder holder = new ViewHolder(view);
//        holder.brandView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Brand brand = mBrandList.get(position);
//                Toast.makeText(v.getContext(), "you clicked view " + brand.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.brandImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Brand brand = mBrandList.get(position);
//                Toast.makeText(v.getContext(), "you clicked image " + brand.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Brand brand = mBrandList.get(position);
//        holder.brandImg.setImageResource(brand.getIcon());
//        holder.brandName.setText(brand.getName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mBrandList.size();
//    }

}
