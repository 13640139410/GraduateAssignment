package com.graduateassignment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.Adapter.BrandAdapter;
import com.graduateassignment.DB.Brand;
import com.graduateassignment.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private List<Brand> categoryList;
    private MainActivity mainActivity;
    private BmobQuery<Brand> brandBmobQuery = new BmobQuery<>();

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        mainActivity = (MainActivity) getActivity();
//        findBrandList();

    }

//    private void findBrandList(){
//        brandBmobQuery.setLimit(7).findObjects(new FindListener<Brand>() {
//            @Override
//            public void done(List<Brand> list, BmobException e) {
//                if(e==null){
//                    RecyclerView recyclerView = (RecyclerView) mainActivity.findViewById(R.id.frag_index_brand);
//                    GridLayoutManager layoutManager = new GridLayoutManager(mainActivity, 4);
//                    recyclerView.setLayoutManager(layoutManager);
//                    BrandAdapter adapter = new BrandAdapter(list);
//                    recyclerView.setAdapter(adapter);
//                }else {
//                    Toast.makeText(mainActivity, "没获取到数据", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//    }
}
