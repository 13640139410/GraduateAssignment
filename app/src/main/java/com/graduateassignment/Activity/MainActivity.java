package com.graduateassignment.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.graduateassignment.DB.ArticleCategory;
import com.graduateassignment.DB.MaintenancePoint;
import com.graduateassignment.DB.Subscribe;
import com.graduateassignment.DB.User;
import com.graduateassignment.Fragment.IndexFragment;
import com.graduateassignment.Fragment.TestBmobFileFragment;
import com.graduateassignment.R;
import com.graduateassignment.Repair.RepairFromCustomerActivity;
import com.graduateassignment.Repair.RepairFromServerActivity;
import com.graduateassignment.Util.SharedPreferencesUtil;
import com.graduateassignment.Util.ToastUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
                Intent intent;
                switch (index){
                    case 0:
                        getFragment(new IndexFragment());
                        break;
                    case 1:
                        getFragment(new TestBmobFileFragment());
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, MeActivity.class);
                        intent.putExtra("USER", BmobUser.getCurrentUser(User.class));
                        startActivity(intent);
                        break;
                    case 3:
                        RepairAct();
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

    private void RepairAct(){
        User user = BmobUser.getCurrentUser(User.class);
        if(user.getIdentity().equalsIgnoreCase("0")){//如果是普通用户
            Intent intent = new Intent(MainActivity.this, RepairFromCustomerActivity.class);
            intent.putExtra("USER", BmobUser.getCurrentUser(User.class));
            startActivity(intent);
        }else if(user.getIdentity().equalsIgnoreCase("1")){//如果是维修店家用户
            Intent intent = new Intent(MainActivity.this, RepairFromServerActivity.class);
            startActivity(intent);
        }
    }

    private void test(){
        User user = BmobUser.getCurrentUser(User.class);
//        user.setIdentity("0");
//        user.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    ToastUtil.show(MainActivity.this,"更新成功");
//                }else{
//                    ToastUtil.show(MainActivity.this,"更新失败");
//                }
//            }
//        });
//        Subscribe subscribe = new Subscribe();
//        subscribe.setUser(user);
//        subscribe.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    ToastUtil.show(MainActivity.this,"更新成功");
//                }else{
//                    ToastUtil.show(MainActivity.this,"更新失败");
//                }
//            }
//        });
        MaintenancePoint maintenancePoint = new MaintenancePoint();
        maintenancePoint.setObjectId("yuUCiiix");
        maintenancePoint.setOwner(user);
        maintenancePoint.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(MainActivity.this,"更新成功");
                }else{
                    ToastUtil.show(MainActivity.this,"更新失败");
                }
            }
        });
    }
}
