package com.graduateassignment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Activity.ArticleActivity;
import com.graduateassignment.Adapter.ArticleAdapter;
import com.graduateassignment.Adapter.PersonalAdapter;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.ArticleCategory;
import com.graduateassignment.DB.Subscribe;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeArticlesFragment extends Fragment {

    private User mUser;//访问的用户
    private List<Article> articles;//访问用户的投稿集合
    private RecyclerView recyclerView;//关注的用户列表
    private TextView emptyTextView;//空数据文字
    private ArticleAdapter articleAdapter;//关注列表适配器
    private static final String ARG_PARAM1 = "param1";
    private List<ArticleCategory> articleCategories;

    public static Fragment newInstance(User user){
        MeArticlesFragment meArticlesFragment = new MeArticlesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        meArticlesFragment.setArguments(args);
        return meArticlesFragment;
    }

    public MeArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_PARAM1);//获取传入的用户
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me_articles, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.frag_mearticle_listview);
        emptyTextView = (TextView) getActivity().findViewById(R.id.frag_mearticle_emptytext);
        if(mUser!=null) {
            getArticleCategorys();
        }else{
            //设置空布局
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText("登录后就可以查看自己发表的文章了哦~~!");
        }
    }

    private void getArticleCategorys(){
        BmobQuery<ArticleCategory> articleCategoryBmobQuery = new BmobQuery<>();
        articleCategoryBmobQuery.findObjects(new FindListener<ArticleCategory>() {
            @Override
            public void done(List<ArticleCategory> list, BmobException e) {
                if(e==null){
                    articleCategories = list;
                    initView();
                }else{
                    ToastUtil.show(getActivity(),"获取文章类别失败");
                }
            }
        });
    }

    //初始化布局
    private void initView(){
        BmobQuery<Article> articleBmobQuery = new BmobQuery<>();
        articleBmobQuery
                .include("author,articleCategorys")
                .addWhereEqualTo("author",mUser)
                .findObjects(new FindListener<Article>() {
                    @Override
                    public void done(List<Article> list, BmobException e) {
                        if(e==null){
                            if(list.isEmpty()){
                                //设置空布局
                                recyclerView.setVisibility(View.GONE);
                                emptyTextView.setVisibility(View.VISIBLE);
                            }
                            //获取布局管理器
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            //为recyclerView设置布局管理器
                            recyclerView.setLayoutManager(layoutManager);
                            //初始化adapter
                            ArticleAdapter articleAdapter = new ArticleAdapter(R.layout.item_articletitle,list,articleCategories);
                            //为子项设置点击事件
                            articleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Article article = (Article) adapter.getItem(position);
                                    Intent intent = new Intent(getActivity(), ArticleActivity.class);
                                    intent.putExtra("article",article);
                                    getActivity().startActivity(intent);
                                }
                            });
                            //为recyclerView设置适配器
                            recyclerView.setAdapter(articleAdapter);
                        }else{

                        }
                    }
                });
    }
}
