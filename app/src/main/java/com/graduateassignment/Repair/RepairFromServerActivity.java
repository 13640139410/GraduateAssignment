package com.graduateassignment.Repair;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.DB.MaintenancePoint;
import com.graduateassignment.DB.RepairOrder;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class RepairFromServerActivity extends AppCompatActivity {

    public static final int SET_ORDER_CLICK = 1;

    private OrderMsgAdapter orderMsgAdapter;
    private RecyclerView recyclerView;//该用户附近订单的列表
    private MaintenancePoint maintenancePoint;//该用户的维修店
    private List<RepairOrder> repairOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairfromserver);
        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.act_repairfromserver_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.act_repairfromserver_recyclerview);
        //orderMsgAdapter = new OrderMsgAdapter(R.layout.item_getoreder,);
        //获取该用户维修店的所处地区
        BmobQuery<MaintenancePoint> maintenancePointBmobQuery = new BmobQuery<>();
        maintenancePointBmobQuery.addWhereEqualTo("owner", BmobUser.getCurrentUser(User.class));
        maintenancePointBmobQuery.findObjects(new FindListener<MaintenancePoint>() {
            @Override
            public void done(List<MaintenancePoint> list, BmobException e) {
                if(e==null){
                    maintenancePoint = list.get(0);
                    //获取该维修店附近的订单
                    BmobQuery<RepairOrder> andQuery = new BmobQuery<RepairOrder>();
                    List<BmobQuery<RepairOrder>> bmobQueries = new ArrayList<BmobQuery<RepairOrder>>();
                    BmobQuery<RepairOrder> province = new BmobQuery<RepairOrder>();
                    BmobQuery<RepairOrder> city = new BmobQuery<RepairOrder>();
                    BmobQuery<RepairOrder> district = new BmobQuery<RepairOrder>();
                    BmobQuery<RepairOrder> status = new BmobQuery<RepairOrder>();
                    province.addWhereEqualTo("province",maintenancePoint.getProvince());
                    city.addWhereEqualTo("city",maintenancePoint.getCity());
                    district.addWhereEqualTo("district",maintenancePoint.getDistrict());
                    status.addWhereEqualTo("status","0");
                    bmobQueries.add(province);
                    bmobQueries.add(city);
                    bmobQueries.add(district);
                    bmobQueries.add(status);
                    andQuery.and(bmobQueries);
                    andQuery.include("phoneModel,customer");
                    andQuery.findObjects(new FindListener<RepairOrder>() {
                        @Override
                        public void done(List<RepairOrder> list, BmobException e) {
                            if(e==null){
                                repairOrders = list;
                                orderMsgAdapter = new OrderMsgAdapter(R.layout.item_getoreder,repairOrders,OrderMsgAdapter.ACT_GET_ORDER);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RepairFromServerActivity.this);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.addItemDecoration(new DividerItemDecoration(RepairFromServerActivity.this, DividerItemDecoration.VERTICAL));//添加列表的分割线
                                recyclerView.setAdapter(orderMsgAdapter);
                                setOrderClick(orderMsgAdapter,repairOrders);
                            }else{
                                ToastUtil.show(RepairFromServerActivity.this,"获取附近订单数据失败！");
                            }
                        }
                    });
                }else{
                    ToastUtil.show(RepairFromServerActivity.this,"获取维修点数据失败！");
                }
            }
        });
    }

    private void setOrderClick(final OrderMsgAdapter orderMsgAdapter,final List<RepairOrder> repairOrders){
        orderMsgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.item_getorder_get:
                        RepairOrder repairOrder = repairOrders.get(position);
                        if(repairOrder.getStatus().equalsIgnoreCase("0")) {
                            repairOrder.setStatus("1");
                            repairOrder.setServiceProvider(maintenancePoint);
                        } else if(repairOrder.getStatus().equalsIgnoreCase("1")) {
                            repairOrder.setStatus("0");
                            repairOrder.setServiceProvider(new MaintenancePoint());
                        }
                        orderMsgAdapter.notifyDataSetChanged();
                        repairOrder.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                }else {
                                    ToastUtil.show(RepairFromServerActivity.this,"接单失败");
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    private void test(){
        MaintenancePoint maintenancePoint = new MaintenancePoint();
        //maintenancePoint.setObjectId("i5Cyvvvx");
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setObjectId("2547fec1ac");
        repairOrder.setServiceProvider(maintenancePoint);
        repairOrder.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(RepairFromServerActivity.this,"更新成功");
                }else{
                    ToastUtil.show(RepairFromServerActivity.this,"更新失败");
                }
            }
        });
    }
}
