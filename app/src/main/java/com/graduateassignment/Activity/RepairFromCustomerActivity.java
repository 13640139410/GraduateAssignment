package com.graduateassignment.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Adapter.PhoneBrandAdapter;
import com.graduateassignment.Adapter.PhoneNameAdapter;
import com.graduateassignment.DB.PhoneModel;
import com.graduateassignment.DB.User;
import com.graduateassignment.Fragment.PhoneNameFragment;
import com.graduateassignment.R;
import com.graduateassignment.Util.Constants;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 顾客通过填写维修订单申请维修服务
 */
public class RepairFromCustomerActivity extends AppCompatActivity implements View.OnClickListener{

    private List<String> brands = new ArrayList<>();
    private int lastPosition=-1;
    private List<PhoneModel> phoneModelList = new ArrayList<>();
    private BmobQuery<PhoneModel> phoneModelBmobQuery = new BmobQuery<>();
    private User user;
    private PhoneBrandAdapter phoneBrandAdapter;
    private PhoneNameAdapter phoneNameAdapter;//一个品牌的型号列表的适配器
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewOfBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_from_customer);
        user = (User) getIntent().getSerializableExtra("USER");
        initBrands();
        initView1();
    }

    private void initView1(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.act_repairfromcustomer_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//添加列表的分割线
        recyclerView.setLayoutManager(linearLayoutManager);
        PhoneBrandAdapter phoneBrandAdapter = new PhoneBrandAdapter(R.layout.item_mobilebrand,brands);
        recyclerView.setAdapter(phoneBrandAdapter);
        phoneBrandAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                replaceFragment(PhoneNameFragment.newInstance((String)adapter.getItem(position)));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_repairfromcustomer_search:
                break;
        }
    }

    private void initBrands(){
        brands.add(Constants.BRAND_360);brands.add(Constants.BRAND_BLACKSHARP);brands.add(Constants.BRAND_GALAXY);
        brands.add(Constants.BRAND_HAMMER);brands.add(Constants.BRAND_HONGMO);brands.add(Constants.BRAND_HONOR);
        brands.add(Constants.BRAND_HUAWEI);brands.add(Constants.BRAND_HUAWEI1);brands.add(Constants.BRAND_HUAWEICX);
        brands.add(Constants.BRAND_HUAWEICXPB);brands.add(Constants.BRAND_HUAWEILY);brands.add(Constants.BRAND_HUAWEIMM);
        brands.add(Constants.BRAND_HUAWEIPB);brands.add(Constants.BRAND_IPAD);brands.add(Constants.BRAND_IPHONE);
        brands.add(Constants.BRAND_IPOD);brands.add(Constants.BRAND_IQOO);brands.add(Constants.BRAND_LENOVO);
        brands.add(Constants.BRAND_LUX);brands.add(Constants.BRAND_MEILAN);brands.add(Constants.BRAND_MEIZU);
        brands.add(Constants.BRAND_MI);brands.add(Constants.BRAND_MIFLAT);brands.add(Constants.BRAND_MOTO);
        brands.add(Constants.BRAND_NOKIA);brands.add(Constants.BRAND_NUBIA);brands.add(Constants.BRAND_NUT);
        brands.add(Constants.BRAND_ONEPLUS);brands.add(Constants.BRAND_OPPO);brands.add(Constants.BRAND_POCO);
        brands.add(Constants.BRAND_VIVO);brands.add(Constants.BRAND_REDMI);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.act_repairfromcustomer_framelayout, fragment);
        transaction.commit();
    }
}
