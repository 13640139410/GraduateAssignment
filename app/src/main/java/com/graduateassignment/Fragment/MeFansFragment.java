package com.graduateassignment.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Activity.MeActivity;
import com.graduateassignment.Adapter.PersonalAdapter;
import com.graduateassignment.DB.Subscribe;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.util.SimpleAnimationUtils;
import razerdp.widget.QuickPopup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFansFragment extends Fragment {

    private User mUser;//访问的用户
    private List<String> fansIds;//关注的用户ID集合
    private List<User> fans;//关注的用户集合
    private Subscribe mSubscribe;//用户的订阅列表
    private RecyclerView recyclerView;//关注的用户列表
    private TextView emptyTextView;//空数据文字
    private PersonalAdapter personalAdapter;//关注列表适配器
    private static final String ARG_PARAM1 = "param1";

    public static Fragment newInstance(User user){
        MeFansFragment meFansFragment = new MeFansFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);//获取传入的文章对象
        meFansFragment.setArguments(args);
        return meFansFragment;
    }

    public MeFansFragment() {}

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
        return inflater.inflate(R.layout.fragment_me_fans, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.frag_myfans_listview);
        emptyTextView = (TextView) getActivity().findViewById(R.id.frag_myfans_emptytext);
        if(mUser!=null) {
            initView();
        }else{
            //设置空布局
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText("登录后就可以查看关注自己的用户了~~!");
        }
    }

    private void initView(){
        //先获取该用户的订阅关系列表
        BmobQuery<Subscribe> subscribeBmobQuery = new BmobQuery<>();
        subscribeBmobQuery.addWhereEqualTo("user",mUser);
        subscribeBmobQuery.findObjects(new FindListener<Subscribe>() {
            @Override
            public void done(List<Subscribe> list, BmobException e) {
                if(e==null){
                    mSubscribe = list.get(0);
                    fansIds = mSubscribe.getFans();
                    final List<String> inFansIds = fansIds;
                    if(fansIds==null||fansIds.size()==0){
                        recyclerView.setVisibility(View.GONE);
                        emptyTextView.setVisibility(View.VISIBLE);
                        return;
                    }
                    List<BmobQuery<User>> queries = new ArrayList<BmobQuery<User>>();
                    //循环添加条件
                    for(String objId:fansIds){
                        BmobQuery<User> bmobQuery = new BmobQuery<>();
                        bmobQuery.addWhereEqualTo("objectId",objId);
                        queries.add(bmobQuery);
                    }
                    BmobQuery<User> idolsBmobQuery = new BmobQuery<>();
                    idolsBmobQuery.or(queries);
                    idolsBmobQuery.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null){
                                fans = list;
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(linearLayoutManager);
                                personalAdapter = new PersonalAdapter(R.layout.item_personal_cardview,fans);
                                recyclerView.setAdapter(personalAdapter);
                                setClickEvent(inFansIds);
                            }else{
                                ToastUtil.show(getActivity(),e.getMessage());
                                Log.e("asd",e.getMessage());
                            }
                        }
                    });
                }else{
                    ToastUtil.show(getActivity(),e.getMessage());
                }
            }
        });
    }

    //设置关注列表点击事件
    private void setClickEvent(final List<String> inFansIds){
        personalAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, final View view, final int position) {
                switch (view.getId()){
                    case R.id.item_personal_cardview_icon://点击关注列表用户的头像
                        Intent intent = new Intent(getActivity(), MeActivity.class);
                        intent.putExtra("USER",(User)adapter.getItem(position));
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case R.id.item_personal_cardview_focus:
                        break;
                }
            }
        });
    }
}
