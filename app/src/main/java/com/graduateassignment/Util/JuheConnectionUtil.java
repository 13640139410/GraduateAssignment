package com.graduateassignment.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.graduateassignment.Activity.TestJsoupActivity;
import com.graduateassignment.Json.Article;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2020/3/8.
 */

public class JuheConnectionUtil {

    public static final String address =  "http://v.juhe.cn/toutiao/index?type=keji&key=5b33c12abab23af938447323fc406d70";

    /**
     * 通过发送OkHttp请求获取html页面,仅针对聚合数据接口获取到的数据
     * @param url
     */
    public static void getJuheHtml(final Context context,final String url){
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
                    parseJuheHtmlAndStore(context,responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 对获取到的html进行解析并存储到Article表中，仅针对聚合数据接口
     * @param html
     */
    public static void parseJuheHtmlAndStore(final Context context, String html){
        Document document = Jsoup.parse(html);
        Element article = document.getElementById("content");
        String articleStr = article.toString();//获取文章内容
        String artH = document.getElementById("artH").attr("value");//获取文章标识ID
        String title = document.title();//获取文章标题
        String date = document.getElementById("datetime_forapp").attr("value");//获取文章日期
        //将字符串转为HTML格式
        String str = FileUtil.convertToHtmlFormat(title, articleStr);
        String strTest = null;
        //将该字符串存入内存中
        try {
            String fileName = artH + title + ".html";//生成文件名称
            FileUtil.writeInternal(context, fileName, str);//创建文件并将内容写入其中
            //strTest = "证明是否成功：\n" + FileUtil.readInternal(context, title+".html");
            String filePath = FileUtil.getFilePathFromWriteInternal(context,fileName);
            File file = new File(filePath);
            final BmobFile bmobFile = new BmobFile(file);
            bmobFile.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null) {
                        //将文件与数据库关联
                        saveFile(context, bmobFile);
                        Log.d("done", "上传成功");
                    } else {
                        Log.d("failed", "上传失败");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFile(final Context context, final BmobFile bmobFile){
        com.graduateassignment.DB.File file = new com.graduateassignment.DB.File();
        file.setMy_file(bmobFile);
        file.update("iaLGIIIT",new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(context,"数据关联成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,"错误信息："+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
