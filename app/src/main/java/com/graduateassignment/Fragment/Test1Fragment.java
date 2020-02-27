package com.graduateassignment.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Test1Fragment extends Fragment {

    private MainActivity mainActivity;
    private Button button;
    private TextView textView;

    class DownloadTask extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            String responseData = null;
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        // 指定访问的服务器地址是电脑本机
                        .url("http://v.juhe.cn/toutiao/index?type=keji&key=5b33c12abab23af938447323fc406d70")
                        .build();
                Response response = null;
                response = client.newCall(request).execute();
                responseData = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseData;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String string) {
            if(string!=null)
                textView.setText(string);
            else
                textView.setText("并未获取到数据");
        }

    }

    public Test1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        button = (Button) mainActivity.findViewById(R.id.jsonBtn);
        textView = (TextView) mainActivity.findViewById(R.id.test1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTask().execute();
            }
        });
    }
}
