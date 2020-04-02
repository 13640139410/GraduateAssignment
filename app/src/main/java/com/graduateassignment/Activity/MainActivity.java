package com.graduateassignment.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.graduateassignment.DB.ArticleCategory;
import com.graduateassignment.DB.User;
import com.graduateassignment.Fragment.IndexFragment;
import com.graduateassignment.Fragment.TestBmobFileFragment;
import com.graduateassignment.Fragment.TestRichEditorFragment;
import com.graduateassignment.R;
import com.graduateassignment.Util.SharedPreferencesUtil;
import com.graduateassignment.Util.ToastUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseActivity {

    private NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArticlePreferenceInit();
        PageNavigationView tab = (PageNavigationView) findViewById(R.id.tab);
        navigationController = tab.material()
                .addItem(R.drawable.ic_menu_mapmode, "首页",getResources().getColor(R.color.colorPrimaryDark))
                .addItem(android.R.drawable.ic_menu_add, "发布",getResources().getColor(R.color.colorPrimaryDark))
                .addItem(R.drawable.ic_menu_manage, "我的",getResources().getColor(R.color.colorPrimaryDark))
                .addItem(R.drawable.ic_menu_mapmode,"维修",getResources().getColor(R.color.colorPrimaryDark))
                .build();
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switch (index){
                    case 0:
                        getFragment(new IndexFragment());
                        break;
                    case 1:
                        getFragment(new TestBmobFileFragment());
                        break;
                    case 2:
                        Intent intent = new Intent(MainActivity.this, MeActivity.class);
                        intent.putExtra("USER", BmobUser.getCurrentUser(User.class));
                        startActivity(intent);
                        break;
                    case 3:

                        break;
                }
            }
            @Override
            public void onRepeat(int index) {
//                switch (index){
//                    case 0:
//
//                        break;
//                    case 1:
//
//                        break;
//                    case 2:
//
//                        break;
//                }
            }
        });
        getFragment(new IndexFragment());
    }

    private void ArticlePreferenceInit(){
        final Set<String> articleCategorySet = SharedPreferencesUtil.getArticleCategory(this);
        //如果未缓存文章类别，则进行缓存
        if(articleCategorySet == null) {
            BmobQuery<ArticleCategory> articleCategoryBmobQuery = new BmobQuery<ArticleCategory>();
            articleCategoryBmobQuery.findObjects(new FindListener<ArticleCategory>() {
                @Override
                public void done(List<ArticleCategory> list, BmobException e) {
                    ToastUtil.show(MainActivity.this,"获取到了数据");
                    Set<String> stringSet = new HashSet<String>();
                    for(ArticleCategory articleCategory: list){
                        stringSet.add(articleCategory.getName());
                    }
                    SharedPreferencesUtil.putArticleCategory(MainActivity.this,stringSet);
                }
            });
        }
    }

    private void getFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        transaction.commit();
    }
}
