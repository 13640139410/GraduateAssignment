package com.graduateassignment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduateassignment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeMsgFragment extends Fragment {

    public static Fragment newInstance(){
        MeMsgFragment meMsgFragment = new MeMsgFragment();
        return meMsgFragment;
    }

    public MeMsgFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me_msg, container, false);
    }

}
