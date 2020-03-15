package com.graduateassignment.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.graduateassignment.DB.File;
import com.graduateassignment.DB.Article;
import com.graduateassignment.R;
import com.graduateassignment.Util.FileUtil;
import com.graduateassignment.Util.JsonUtil;
import com.graduateassignment.Util.JuheConnectionUtil;
import com.graduateassignment.Util.ToastUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestJsoupActivity extends AppCompatActivity implements View.OnClickListener {

    private final String url = "http:\\/\\/mini.eastday.com\\/mobile\\/200308163800204.html";
    public static final int UPDATE_MSG = 1;
    private TextView textView;
    private Button button;
    private Button button1;
    private String test = null;
    StringBuilder sb = null;
    private List<Article> dbArticles = new ArrayList<>();
    private List<com.graduateassignment.Json.Article> jsArticles = new ArrayList<>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MSG:
                    //textView.setText(sb);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jsoup);
        button = (Button) findViewById(R.id.act_test_jsoup_btn);
        button1 = (Button) findViewById(R.id.act_test_jsoup_store);
        textView = (TextView) findViewById(R.id.act_test_jsoup_textview);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_test_jsoup_btn:
                //JuheConnectionUtil.getJuheHtml(this, url);
                showJuheData();
                break;
            case R.id.act_test_jsoup_store:
                putBmob();
                break;
        }
    }

    //展示获取到的聚合JSON数据
    private void showJuheData(){
        JuheConnectionUtil.sendOkHttpRequest(JuheConnectionUtil.address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("失败了呢");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String text = response.body().string();
                jsArticles = JsonUtil.parseArticleJson(text);
                dbArticles = JsonUtil.convertToDBArticleList(jsArticles);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str = null;
                        for(Article article : dbArticles){
                            str+="\ntitle:" + article.getTitle() +
                                    "\nurl:" + article.getUrl() +
                                    "\ndate:" + article.getDate().getDate() +
                                    "\nisReptile:" + article.getIsReptile() +
                                    "\nthumbnail:" + article.getThumbnail_pic_s();
                        }
                        textView.setText(str);
                    }
                });
            }
        });
    }

    //将获取到的聚合数据存入数据库中
    private void putBmob(){
        List<BmobObject> articles = new ArrayList<>();
        Toast.makeText(this,""+dbArticles.size(),Toast.LENGTH_LONG).show();
        if(!(dbArticles.size()>0)){
            return;
        }
        for(Article article:dbArticles){
            articles.add(article);
        }
        new BmobBatch().insertBatch(articles).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Toast.makeText(TestJsoupActivity.this,"第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TestJsoupActivity.this,"第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex.getErrorCode(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(TestJsoupActivity.this,"失败：" + e.getMessage() + "," + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRequestWithOkHttp(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseHtmlAndStore(responseData);
                    //showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendRequestWithHttpURLConnection() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://mini.eastday.com//mobile//200307200737732.html");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //在此处获取指定的HTML字符串，存储为文件，发送到数据库中
                    //showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseHtmlAndStore(String html){
        Document document = Jsoup.parse(html);
        Element article = document.getElementById("content");
        String articleStr = article.toString();
        String title = document.title();
        String date = document.getElementById("datetime_forapp").attr("value");
        //将字符串转为HTML格式
        String str = FileUtil.convertToHtmlFormat(title, articleStr);
        String strTest = null;
        //将该字符串存入内存中
        try {
            FileUtil.writeInternal(this, title+".html", str);
            strTest = "证明是否成功：\n" + FileUtil.readInternal(this, title+".html");
            String filePath = FileUtil.getFilePathFromWriteInternal(this, title+".html");
            File file = new File(filePath);
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null) {
                        //将文件与数据库关联
                        saveFile(bmobFile);
                        Log.d("done", "上传成功");
                    } else {
                        Log.d("failed", "上传失败");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        showResponse(strTest);
    }

    private void saveFile(final BmobFile bmobFile){
        com.graduateassignment.DB.File file = new com.graduateassignment.DB.File();
        file.setMy_file(bmobFile);
        file.update("iaLGIIIT",new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(TestJsoupActivity.this,"数据关联成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(TestJsoupActivity.this,"错误信息："+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作， 将结果显示到界面上
                textView.setText(response);
            }
        });
    }


}
