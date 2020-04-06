package com.graduateassignment.personalMsg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.DB.MaintenancePoint;
import com.graduateassignment.DB.RepairOrder;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Repair.OrderMsgAdapter;
import com.graduateassignment.Util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<RepairOrder> repairOrders;
    private MaintenancePoint maintenancePoint;
    private OrderMsgAdapter orderMsgAdapter;

    public OrderFragment() { }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.personalmsg_fragment_order, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initRecycler();
    }

    private void initRecycler(){
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.personalmsg_fragment_order_recyclerview);
        if(BmobUser.getCurrentUser(User.class).getIdentity().equalsIgnoreCase("1")){
            final BmobQuery<MaintenancePoint> maintenancePointBmobQuery = new BmobQuery<>();
            maintenancePointBmobQuery.addWhereEqualTo("owner",BmobUser.getCurrentUser(User.class));
            maintenancePointBmobQuery.findObjects(new FindListener<MaintenancePoint>() {
                @Override
                public void done(List<MaintenancePoint> list, BmobException e) {
                    if(e==null){
                        maintenancePoint = list.get(0);
                        BmobQuery<RepairOrder> repairOrderBmobQuery = new BmobQuery<RepairOrder>();
                        repairOrderBmobQuery.addWhereEqualTo("serviceProvider",maintenancePoint);
                        repairOrderBmobQuery.include("serviceProvider,phoneModel");
                        repairOrderBmobQuery.findObjects(new FindListener<RepairOrder>() {
                            @Override
                            public void done(List<RepairOrder> list, BmobException e) {
                                repairOrders = list;
                                setRecyclerView(repairOrders,OrderMsgAdapter.ACT_PROVIDER_ORDER);
                            }
                        });
                    }
                    else{
                        ToastUtil.show(getActivity(),"加载失败~");
                    }
                }
            });
        }else if(BmobUser.getCurrentUser(User.class).getIdentity().equalsIgnoreCase("0")){
            BmobQuery<RepairOrder> repairOrderBmobQuery = new BmobQuery<>();
            repairOrderBmobQuery.addWhereEqualTo("customer",BmobUser.getCurrentUser(User.class));
            repairOrderBmobQuery.include("serviceProvider,phoneModel");
            repairOrderBmobQuery.findObjects(new FindListener<RepairOrder>() {
                @Override
                public void done(List<RepairOrder> list, BmobException e) {
                    if(e==null){
                        repairOrders = list;
                        setRecyclerView(repairOrders,OrderMsgAdapter.ACT_USER_ORDER);
                    }else{
                        ToastUtil.show(getActivity(),"加载失败~");
                    }
                }
            });
        }
    }

    private void setRecyclerView(List<RepairOrder> repairOrders,int actCode){
        orderMsgAdapter = new OrderMsgAdapter(R.layout.item_getoreder,repairOrders,actCode);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(orderMsgAdapter);
        setClickEvent(orderMsgAdapter,repairOrders);
    }

    private void setClickEvent(final OrderMsgAdapter orderMsgAdapter, final List<RepairOrder>repairOrders){
        orderMsgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_getorder_get:
                        RepairOrder repairOrder = orderMsgAdapter.getItem(position);
                        final String status = repairOrder.getStatus();
                        final int actCode = orderMsgAdapter.getActCode();
                        if(status.equalsIgnoreCase("1")&&actCode==OrderMsgAdapter.ACT_PROVIDER_ORDER){
                            repairOrder.setStatus("2");
                            orderMsgAdapter.notifyDataSetChanged();
                            repairOrder.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e!=null){ ToastUtil.show(getActivity(),"操作失败！"); }
                                }
                            });
                        }else if(status.equalsIgnoreCase("2")&&actCode==OrderMsgAdapter.ACT_USER_ORDER){
                            repairOrder.setStatus("3");
                            orderMsgAdapter.notifyDataSetChanged();
                            repairOrder.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e!=null){ ToastUtil.show(getActivity(),"操作失败！"); }
                                }
                            });
                        }
                        break;
                }
            }
        });
    }
}
