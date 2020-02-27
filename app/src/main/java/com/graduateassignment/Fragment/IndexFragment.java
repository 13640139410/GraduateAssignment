package com.graduateassignment.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.Adapter.BrandAdapter;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    private View view;
    private List<Brand> brandList = new ArrayList<Brand>();
    private MainActivity mainActivity;
    private RecyclerView brandRecyclerView;//显示品牌列表
    private TextView testTextView;
    private String jsonData;

    public IndexFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity) getActivity();
        showBrandList();
    }

    //获取Brand列表并进行展示
    private void showBrandList(){
        BmobQuery<Brand> brandBmobQuery = new BmobQuery<>();
        brandBmobQuery.setLimit(7).findObjects(new FindListener<Brand>() {
            @Override
            public void done(List<Brand> list, BmobException e) {
                if(e==null&&list.size()!=0){
                    Toast.makeText(mainActivity,"获取到了数据"+list.size(),Toast.LENGTH_SHORT).show();
                    initBrandList(list);
                    RecyclerView recyclerView = (RecyclerView) mainActivity.findViewById(R.id.frag_index_brand);
                    GridLayoutManager layoutManager = new GridLayoutManager(mainActivity, 4);
                    recyclerView.setLayoutManager(layoutManager);
                    BrandAdapter brandAdapter = new BrandAdapter(R.layout.item_brand,list);
                    recyclerView.setAdapter(brandAdapter);
                }else{
                    Toast.makeText(mainActivity,"没获取到数据",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initBrandList(List<Brand> list){
        for(Brand brand:list){
            brand.setIcon(R.drawable.icon_huawei_32);
        }
        Brand brand = new Brand();
        brand.setName("全部");
        brand.setIcon(R.drawable.icon_all_32);
        list.add(brand);
    }

//    private void showJsonData(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
////                    OkHttpClient client = new OkHttpClient();
////                    Request request = new Request.Builder()
////                            // 指定访问的服务器地址是电脑本机
////                            .url("http://v.juhe.cn/toutiao/index" +
////                                    "type=keji&key=5b33c12abab23af938447323fc406d70")
////                            .build();
////                    Response response = client.newCall(request).execute();
////                    jsonData = response.body().string();
////                    Toast.makeText(mainActivity,"可以开启子线程",Toast.LENGTH_LONG).show();
//                    jsonData = "asdasd";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

}
