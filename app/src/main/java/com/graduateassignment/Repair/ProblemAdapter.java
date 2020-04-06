package com.graduateassignment.Repair;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/4/4.
 */

public class ProblemAdapter extends BaseQuickAdapter<Problem,BaseViewHolder> {

    public ProblemAdapter(int layoutResId, List<Problem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Problem item) {
        View view = helper.getView(R.id.item_problem_layout);
        helper.setText(R.id.item_problem_name, item.getName());
        if (item.isChecked){
            helper.setTextColor(R.id.item_problem_name,view.getResources().getColor(R.color.white));
            helper.setBackgroundRes(R.id.item_problem_layout,R.drawable.rectangle_littleround_blue);
        }else{
            helper.setTextColor(R.id.item_problem_name,view.getResources().getColor(R.color.gary_deep));
            helper.setBackgroundRes(R.id.item_problem_layout,R.drawable.rectangle_littleround);
        }
        helper.addOnClickListener(R.id.item_problem_layout);
    }
}
