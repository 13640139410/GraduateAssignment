package com.graduateassignment.Repair;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Adapter.PhoneNameAdapter;
import com.graduateassignment.DB.PhoneModel;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneNameFragment extends Fragment {

    private ProgressBar progressBar;
    private String brand = "";
    private static final String ARG_PARAM1 = "param1";

    public static Fragment newInstance(String phoneBrand){
        PhoneNameFragment phoneNameFragment = new PhoneNameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, phoneBrand);//获取传入的文章对象
        phoneNameFragment.setArguments(args);
        return phoneNameFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            brand = getArguments().getString(ARG_PARAM1);//获取传入的用户
        }
    }

    public PhoneNameFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_name, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.frag_phonename_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.blue), PorterDuff.Mode.SRC_IN);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BmobQuery<PhoneModel> phoneModelBmobQuery = new BmobQuery<>();
        phoneModelBmobQuery.addWhereEqualTo("brand",brand);
        phoneModelBmobQuery.findObjects(new FindListener<PhoneModel>() {
            @Override
            public void done(List<PhoneModel> list, BmobException e) {
                if(e==null){
                    progressBar.setVisibility(View.GONE);
                    RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.frag_phonename_recycylerview);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    PhoneNameAdapter phoneNameAdapter = new PhoneNameAdapter(R.layout.item_phone,list);
                    recyclerView.setAdapter(phoneNameAdapter);
                    phoneNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent intent = new Intent(getActivity(),FillInOrderActivity.class);
                            intent.putExtra(ARG_PARAM1,(PhoneModel)adapter.getItem(position));
                            getActivity().startActivity(intent);
                        }
                    });
                }else{
                    ToastUtil.show(getActivity(),e.getMessage());
                }
            }
        });
    }
}
