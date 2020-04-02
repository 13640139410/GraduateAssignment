package com.graduateassignment.Activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.graduateassignment.Adapter.MyFragmentAdapter;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.Subscribe;
import com.graduateassignment.DB.User;
import com.graduateassignment.Fragment.MeArticlesFragment;
import com.graduateassignment.Fragment.MeFansFragment;
import com.graduateassignment.Fragment.MeIdolsFragment;
import com.graduateassignment.Fragment.MeMsgFragment;
import com.graduateassignment.Fragment.MeOrderFragment;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeActivity extends AppCompatActivity {

    public static final String TAG = "TabActivity";
    public static final String []sTitle = new String[]{"  关注  ","  粉丝  ","  投稿  ","  订单  ","  个人  "};
    private static final int WATCH_ME = 0;
    private static final int NOT_LOGIN = 1;
    private static final int WATCH_OTHER = 2;
    private int status;
    private TextView fansNum;//粉丝数
    private TextView idolsNum;//关注数
    private TextView articleNum;//投稿数
    private CircleImageView userIcon;//用户头像
    private TextView userName;//用户名
    private TextView userSignature;//用户签名
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private User beViewedPerson;//获取传入的USER对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        fansNum = (TextView) findViewById(R.id.act_me_fansnum);//粉丝数
        idolsNum = (TextView) findViewById(R.id.act_me_idolsnum);//关注数
        articleNum = (TextView) findViewById(R.id.act_me_articlenum); //投稿数
        userIcon = (CircleImageView) findViewById(R.id.act_me_icon);//用户头像
        userName = (TextView) findViewById(R.id.act_me_name); //用户名
        userSignature = (TextView) findViewById(R.id.act_me_signature); //用户签名
        beViewedPerson = (User) getIntent().getSerializableExtra("USER");
        judgeStatus();
        initView();
    }

    //判断当前操作 是 观看自己，观看他人
    private void judgeStatus(){
        if (BmobUser.getCurrentUser(User.class)==null){//如果传入用户为空，说明未登录
            status = NOT_LOGIN;
        }else if (beViewedPerson.getObjectId().
                equalsIgnoreCase(BmobUser.getCurrentUser(User.class).getObjectId())){//如果传入用户与已登录相同，即观看自己信息
            beViewedPerson = BmobUser.getCurrentUser(User.class);
            status = WATCH_ME;
        }else if(!beViewedPerson.getObjectId().
                equalsIgnoreCase(BmobUser.getCurrentUser(User.class).getObjectId())){//如果传入用户与已登录不相同，即观看他人信息
            status = WATCH_OTHER;
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.act_me_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.act_me_tabLayout);
        //赋值粉丝数 关注数 投稿数 姓名 签名
        if(status==WATCH_ME||status==WATCH_OTHER){
            //赋值姓名签名
            Glide.with(this).load(beViewedPerson.getAvator().getUrl()).crossFade().into((CircleImageView) userIcon);
            userName.setText(beViewedPerson.getUsername());
            userSignature.setText(beViewedPerson.getSignature()==null?"这个人很懒，什么都没写":beViewedPerson.getSignature());
            //赋值粉丝数 关注数
            BmobQuery<Subscribe> subscribeBmobQuery = new BmobQuery<>();
            subscribeBmobQuery.addWhereEqualTo("user",beViewedPerson);
            subscribeBmobQuery.findObjects(new FindListener<Subscribe>() {
                @Override
                public void done(List<Subscribe> list, BmobException e) {
                    if(e==null){
                        Subscribe subscribe = list.get(0);
                        List<String> fans = subscribe.getFans();
                        List<String> idols = subscribe.getIdols();
                        fansNum.setText(fans==null?"0":""+fans.size());
                        idolsNum.setText(idols==null?"0":""+idols.size());
                    }else{
                        ToastUtil.show(MeActivity.this,e.getMessage());
                    }
                }
            });
            //赋值投稿数
            BmobQuery<Article> articleBmobQuery = new BmobQuery<>();
            articleBmobQuery.addWhereEqualTo("author",beViewedPerson);
            articleBmobQuery.findObjects(new FindListener<Article>() {
                @Override
                public void done(List<Article> list, BmobException e) {
                    articleNum.setText(list==null?"0":""+list.size());
                }
            });
        }
        //如果是观看他人模式
        if(status==WATCH_OTHER){
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]).setIcon(R.drawable.icon_idols_32));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]).setIcon(R.drawable.icon_fans_32));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]).setIcon(R.drawable.icon_contribute_32));
        if(status==WATCH_ME||status==NOT_LOGIN){
            mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]).setIcon(R.drawable.icon_order_32));
            mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]).setIcon(R.drawable.icon_personal_32));
        }
        //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[5]));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MeIdolsFragment.newInstance(beViewedPerson));
        fragments.add(MeFansFragment.newInstance(beViewedPerson));
        fragments.add(MeArticlesFragment.newInstance(beViewedPerson));
        if(status==WATCH_ME||status==NOT_LOGIN){
            fragments.add(MeOrderFragment.newInstance());
            fragments.add(MeMsgFragment.newInstance(beViewedPerson));
        }

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(sTitle));
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG,"select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
