package com.graduateassignment.Repair;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.graduateassignment.DB.RepairOrder;
import com.graduateassignment.R;

import java.util.List;

/**
 * Created by admin on 2020/4/5.
 */

public class OrderMsgAdapter extends BaseQuickAdapter<RepairOrder,BaseViewHolder> {

    public static final int ACT_GET_ORDER = 0;//商家查看附近订单
    public static final int ACT_PROVIDER_ORDER = 1;//商家查看订单
    public static final int ACT_USER_ORDER = 2;//用户查看订单
    private int actCode;

    public int getActCode(){
        return actCode;
    }

    public OrderMsgAdapter(int layoutResId, List<RepairOrder> data,int actCode) {
        super(layoutResId, data);
        this.actCode = actCode;
    }

    @Override
    protected void convert(BaseViewHolder helper, RepairOrder item) {
        String status = item.getStatus();
        String problemStr = "";
        for(String problem:item.getProblemList()){
            problemStr += problem + "; ";
        }
        Button button = (Button) helper.getView(R.id.item_getorder_get);
        helper.setText(R.id.item_getorder_id,"订单号:"+item.getObjectId());
        helper.setText(R.id.item_getorder_phonename,item.getPhoneModel().getName());
        helper.setText(R.id.item_getorder_phoneproblem,problemStr);
        helper.setText(R.id.item_getorder_problemcount,"共计"+item.getProblemList().size()+"项服务");
        if(actCode==ACT_GET_ORDER||actCode==ACT_PROVIDER_ORDER){
            if(status.equalsIgnoreCase("0")){
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_roundframe_gray));
                button.setTextColor(button.getResources().getColor(R.color.text_gray));
                button.setText("接单");
            }else if(status.equalsIgnoreCase("1")){
                helper.setText(R.id.item_getorder_status,"处理该订单中");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_round_blue_selected));
                button.setTextColor(button.getResources().getColor(R.color.white));
                button.setText("处理中");
            } else if(status.equalsIgnoreCase("2")){
                helper.setText(R.id.item_getorder_status,"等待商家签收");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_round_blue_selected));
                button.setTextColor(button.getResources().getColor(R.color.white));
                button.setText("已处理");
            }else if(status.equalsIgnoreCase("3")) {
                helper.setText(R.id.item_getorder_status,"商家已签收");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_roundframe_gray));
                button.setTextColor(button.getResources().getColor(R.color.text_gray));
                button.setText("已完成");
            }
        }else if(actCode==ACT_USER_ORDER){
            if(status.equalsIgnoreCase("0")){
                helper.setText(R.id.item_getorder_status,"暂无商家接单");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_roundframe_gray));
                button.setTextColor(button.getResources().getColor(R.color.text_gray));
                button.setText("未被接单");
            }
            else if(status.equalsIgnoreCase("1")){
                helper.setText(R.id.item_getorder_status,"商家已接单");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_roundframe_gray));
                button.setTextColor(button.getResources().getColor(R.color.text_gray));
                button.setText("处理中");
            } else if(status.equalsIgnoreCase("2")){
                helper.setText(R.id.item_getorder_status,"订单已处理");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_round_blue_selected));
                button.setTextColor(button.getResources().getColor(R.color.white));
                button.setText("签收");
            }else if(status.equalsIgnoreCase("3")){
                helper.setText(R.id.item_getorder_status,"订单已签收");
                button.setBackground(button.getResources().getDrawable(R.drawable.shape_roundframe_gray));
                button.setTextColor(button.getResources().getColor(R.color.text_gray));
                button.setText("已签收");
            }
        }
        helper.addOnClickListener(R.id.item_getorder_get);
    }

}
