package com.graduateassignment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Activity.ArticleActivity;
import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.Search.SearchActivity;
import com.graduateassignment.Adapter.ArticleAdapter;
import com.graduateassignment.DB.ArticleCategory;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.DB.User;
import com.graduateassignment.Notification.NotificationActivity;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private View view;
    private List<Brand> brandList = new ArrayList<Brand>();
    private MainActivity mainActivity;
    private List<ArticleCategory> articleCategories;
    private RecyclerView brandRecyclerView;//显示品牌列表
    private TextView testTextView;
    private TextView errTextView;//展示错误信息
    private String jsonData;
    private CircleImageView icon;
    private User me;
    private EditText search;
    private ImageView notification;

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
        initSearchView();
    }

    private void initSearchView(){
        me = BmobUser.getCurrentUser(User.class);
        icon = (CircleImageView) getActivity().findViewById(R.id.frag_index_icon);
        search = (EditText) getActivity().findViewById(R.id.frag_index_search);
        notification = (ImageView) getActivity().findViewById(R.id.frag_index_email);
        if(me!=null){
            Glide.with(this).load(me.getAvator().getUrl()).crossFade().into((CircleImageView) icon);
        }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(ARG_PARAM1,SearchActivity.SEARCH_ARTICLE);
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(me!=null){
                    Intent intent = new Intent(getActivity(), NotificationActivity.class);
                    startActivity(intent);
                }
            }
        });
        getArticleCategorys();
    }

    private void getArticleCategorys(){
        BmobQuery<ArticleCategory> articleCategoryBmobQuery = new BmobQuery<>();
        articleCategoryBmobQuery.findObjects(new FindListener<ArticleCategory>() {
            @Override
            public void done(List<ArticleCategory> list, BmobException e) {
                if(e==null){
                    articleCategories = list;
                    showArticleList();
                }else{
                    ToastUtil.show(getActivity(),"获取文章类别失败");
                }
            }
        });
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
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                    //为recyclerView设置布局管理器
                    recyclerView.setLayoutManager(gridLayoutManager);
                    //初始化adapter
                    ArticleAdapter articleAdapter = new ArticleAdapter(R.layout.item_articletitle,list,articleCategories);
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

    private void test(){

    }
}
