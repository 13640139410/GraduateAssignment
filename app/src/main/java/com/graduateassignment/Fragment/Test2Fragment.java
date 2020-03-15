package com.graduateassignment.Fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Test2Fragment extends Fragment implements View.OnClickListener{

    private Button button;
    private TextView textView;
    private MainActivity mainActivity;

    public Test2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test2, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity) getActivity();
        button = (Button) mainActivity.findViewById(R.id.frag_test2_btn);
        textView = (TextView) mainActivity.findViewById(R.id.frag_test2_text);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_test2_btn:
                showSDCardPath();
                break;
        }
    }

    private void showSDCardPath(){
        String path = null;
        path = "getExternalCacheDir().getAbsolutePath():\n"+mainActivity.getExternalCacheDir().getAbsolutePath();
        path += "\nEnvironment.getExternalStorageDirectory().getAbsolutePath()\n"
                +Environment.getExternalStorageDirectory().getAbsolutePath();
        path += "\nEnvironment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath():\n"
                +Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        textView.setText("sdcard path:"+path);
    }
}
