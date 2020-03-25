package com.graduateassignment.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Activity.ArticleActivity;
import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.Adapter.ArticleAdapter;
import com.graduateassignment.Adapter.BrandAdapter;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.Json.Article;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    private View view;
    private List<Brand> brandList = new ArrayList<Brand>();
    private MainActivity mainActivity;
    private RecyclerView brandRecyclerView;//显示品牌列表
    private TextView testTextView;
    private TextView errTextView;//展示错误信息
    private String jsonData;

    public IndexFragment() {}

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
                    showArticleList();
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

    private void showArticleList(){
        BmobQuery<com.graduateassignment.DB.Article> articleBmobQuery = new BmobQuery<>();
        articleBmobQuery
                .include("author,articleCategorys")
                .setLimit(10)
                .addWhereEqualTo("isReptile","0")
                .findObjects(new FindListener<com.graduateassignment.DB.Article>() {
            @Override
            public void done(List<com.graduateassignment.DB.Article> list, BmobException e) {
                if(e==null){
                    //获取RecyclerView
                    RecyclerView recyclerView = (RecyclerView) mainActivity.findViewById(R.id.frag_index_artlist);
                    //获取布局管理器
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
                    //为recyclerView设置布局管理器
                    recyclerView.setLayoutManager(layoutManager);
                    //初始化adapter
                    ArticleAdapter articleAdapter = new ArticleAdapter(R.layout.item_articletitle,list);
                    //为子项设置点击事件
                    articleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            com.graduateassignment.DB.Article article = (com.graduateassignment.DB.Article) adapter.getItem(position);
                            Intent intent = new Intent(mainActivity, ArticleActivity.class);
                            intent.putExtra("article",article);
                            mainActivity.startActivity(intent);
                        }
                    });
                    //为recyclerView设置适配器
                    recyclerView.setAdapter(articleAdapter);
                }else{
                    ToastUtil.show(mainActivity,"错误信息："+e.getMessage());
                    Log.d("asd",e.getMessage());
                }
            }
        });
    }
}
