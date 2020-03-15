package com.graduateassignment.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.Json.Article;
import com.graduateassignment.Json.ArticleJson;
import com.graduateassignment.R;
import com.graduateassignment.Util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestJuheFragment extends Fragment {
    public static final int UPDATE_TEXT = 1;
    private MainActivity mainActivity;
    private Button button;
    private TextView textView;
    private String address = "http://v.juhe.cn/toutiao/index?type=keji&key=5b33c12abab23af938447323fc406d70";
    private String responseData = null;
    private String str=null;
    private List<Article> articleList=new ArrayList<Article>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    // 在这里可以进行UI操作
                    int i=7;
                    if(articleList.size()>0) {
                        for (Article article : articleList) {
                            i++;
                            if (i < 10) {
                                str += "title:" + article.getTitle() +
                                        "\ndate:" + article.getDate() +
                                        "\nauthor:" + article.getAuthor_name() +
                                        "\nthumb:" + article.getThumbnail_pic_s02()+"\n";
                            } else
                                break;
                        }
                    }
                    textView.setText(str);
                    break;
                default:
                    break;
            }
        }
    };

    public TestJuheFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test1, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity) getActivity();
        button = (Button) mainActivity.findViewById(R.id.jsonBtn);
        textView = (TextView) mainActivity.findViewById(R.id.test1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp(address);
            }
        });
    }

    private void sendRequestWithOkHttp(final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(address)
                            .build();
                    Response response = client.newCall(request).execute();
                    responseData = response.body().string();
                    Log.d("MainActivity", responseData);
                    //parseJSONWithGSON("["+responseData+"]");
                    articleList = JsonUtil.parseArticleJson(responseData);
                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    handler.sendMessage(message); // 将Message对象发送出去
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //将获取到的数据存入数据库中
    private void putIntoBmob(){

    }
}
