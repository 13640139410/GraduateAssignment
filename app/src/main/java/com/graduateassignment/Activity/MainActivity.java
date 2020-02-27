package com.graduateassignment.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.graduateassignment.Fragment.IndexFragment;
import com.graduateassignment.Fragment.Test1Fragment;
import com.graduateassignment.Fragment.Test2Fragment;
import com.graduateassignment.Fragment.TestFragment;
import com.graduateassignment.R;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseActivity {

//    private NavigationController navigationController;
    private NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageNavigationView tab = (PageNavigationView) findViewById(R.id.tab);
        navigationController = tab.material()
                .addItem(R.drawable.ic_menu_mapmode, "明细",getResources().getColor(R.color.colorPrimaryDark))
                .addItem(android.R.drawable.ic_menu_add, "添加",getResources().getColor(R.color.colorPrimaryDark))
                .addItem(R.drawable.ic_menu_manage, "我的",getResources().getColor(R.color.colorPrimaryDark))
                .build();
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switch (index){
                    case 0:
                        getFragment(new IndexFragment());
                        break;
                    case 1:
                        getFragment(new Test1Fragment());
                        break;
                    case 2:
                        getFragment(new Test2Fragment());
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
//        PageNavigationView tab = (PageNavigationView) findViewById(R.id.tab);
//        navigationController = tab.material()
//                .addItem(R.drawable.ic_menu_mapmode, "首页",getResources().getColor(R.color.colorPrimaryDark))
//                .addItem(android.R.drawable.ic_menu_add, "维修",getResources().getColor(R.color.colorPrimaryDark))
//                .addItem(R.drawable.ic_menu_manage, "我的",getResources().getColor(R.color.colorPrimaryDark))
//                .build();
//        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
//            @Override
//            public void onSelected(int index, int old) {
//                switch (index) {
//                    case 0://如果是0，即明细
//                        getFragment(new TestFragment());
//                        break;
//                    case 1://如果是1，即添加账单
//
//                        break;
//                    case 2://如果是2，即我的
//
//                        break;
//                }
//            }
//            @Override
//            public void onRepeat(int index) {
//                switch (index){
//                    case 0://如果是0，即明细
//                        getFragment(new TestFragment());
//                        break;
//                    case 1://如果是1，即添加账单
//
//                        break;
//                    case 2://如果是2，即我的
//
//                        break;
//                }
//            }
//        });
    }

    private void getFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        transaction.commit();
    }
//    private void getFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment_main, fragment);
//        transaction.commit();
//    }
}
