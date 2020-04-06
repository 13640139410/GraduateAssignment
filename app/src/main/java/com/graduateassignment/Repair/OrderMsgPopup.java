package com.graduateassignment.Repair;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.graduateassignment.Activity.MeActivity;
import com.graduateassignment.DB.RepairOrder;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by admin on 2020/4/4.
 */

public class OrderMsgPopup extends BasePopupWindow {

    public OrderMsgPopup(Context context){
        super(context);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        Animation showAnimation = new ScaleAnimation(0, 1f, 0, 1f);
        showAnimation.setDuration(200);
        return showAnimation;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation showAnimation = new ScaleAnimation(1f, 0, 1f, 0);
        showAnimation.setDuration(200);
        return showAnimation;
    }

    @Override
    public View onCreateContentView() {
        View view = createPopupById(R.layout.window_ordermsg);
        return view;
    }

    protected void initView(final OrderMsgPopup orderMsgPopup, final RepairOrder repairOrder){
        View view = orderMsgPopup.getContentView();
        String problems = "";
        for(String problem:repairOrder.getProblemList()){
            problems+=problem+"; ";
        }
        TextView address = (TextView) view.findViewById(R.id.window_ordermsg_address);
        TextView phone = (TextView) view.findViewById(R.id.window_ordermsg_phone);
        TextView comment = (TextView) view.findViewById(R.id.window_ordermsg_comment);
        TextView problem = (TextView) view.findViewById(R.id.window_ordermsg_problem);
        address.setText(repairOrder.getProvince()+repairOrder.getCity()+repairOrder.getDistrict()+repairOrder.getAddress());
        phone.setText(repairOrder.getPhone());
        comment.setText(repairOrder.getComment());
        problem.setText(problems);
    }
}
