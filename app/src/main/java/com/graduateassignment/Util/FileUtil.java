package com.graduateassignment.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by admin on 2020/3/4.
 */

public class FileUtil {

    /**
     * 将内容写入sd卡中
     * @param filename 要写入的文件名
     * @param content  待写入的内容
     * @throws IOException
     */
    public static boolean writeExternal(Context context, String filename, String content) throws IOException {

        //获取外部存储卡的可用状态
        String storageState = Environment.getExternalStorageState();

        //判断是否存在可用的的SD Card
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            //路径： /storage/emulated/0/Android/data/com.yoryky.demo/cache/yoryky.txt
            filename = context.getExternalCacheDir().getAbsolutePath()  + File.separator + filename;

            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(content.getBytes());
            outputStream.close();
            return true;
        }
        return false;
    }

    /**
     * 从sd card文件中读取数据
     * @param filename 待读取的sd card
     * @return
     * @throws IOException
     */
    public static String readExternal(Context context, String filename) throws IOException {
        StringBuilder sb = new StringBuilder("");
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filename = context.getExternalCacheDir().getAbsolutePath() + File.separator + filename;
            //打开文件输入流
            FileInputStream inputStream = new FileInputStream(filename);

            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            //读取文件内容
            while(len > 0){
                sb.append(new String(buffer,0,len));

                //继续将数据放到buffer中
                len = inputStream.read(buffer);
            }
            //关闭输入流
            inputStream.close();
        }
        return sb.toString();
    }

    /**
     * 写Internal Card文件
     * @param context
     * @param filename
     * @param content
     * @throws IOException
     */
    public static void writeInternal(Context context, String filename, String content) throws IOException{
        //获取文件在内存卡中files目录下的路径
        File file = context.getFilesDir();
        filename = file.getAbsolutePath() + File.separator + filename;
        Log.d("FileUtil.writeInternal", filename);

        //打开文件输出流
        FileOutputStream outputStream = new FileOutputStream(filename);

        //写数据到文件中
        outputStream.write(content.getBytes());
        outputStream.close();
    }

    /**
     * 读内存卡中文件方法
     * @param context
     * @param filename 文件名
     * @return
     * @throws IOException
     */
    public static String readInternal(Context context,String filename) throws IOException{
        StringBuilder sb = new StringBuilder("");

        //获取文件在内存卡中files目录下的路径
        File file = context.getFilesDir();
        filename = file.getAbsolutePath() + File.separator + filename;

        //打开文件输入流
        FileInputStream inputStream = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        int len = inputStream.read(buffer);
        //读取文件内容
        while(len > 0){
            sb.append(new String(buffer,0,len));

            //继续将数据放到buffer中
            len = inputStream.read(buffer);
        }
        //关闭输入流
        inputStream.close();
        return sb.toString();
    }

    /**
     * 通过文件名获取通过WriteInternal存储的文件的完整路径
     * @param fileName
     * @return
     */
    public static String getFilePathFromWriteInternal(Context context,String fileName){
        File file = context.getFilesDir();
        fileName = file.getAbsolutePath() + File.separator + fileName;
        return fileName;
    }

    /**
     * 将一段字符串拼接为完整的HTML源文件格式,针对聚合获取到的数据
     * @param content
     * @return
     */
    public static String convertToHtmlFormat(String title,String content){
        //拼接头部
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"zh-cn\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "<meta content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no\" name=\"viewport\">\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "<meta name=\"format-detection\" content=\"email=no\">\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\n" +
                "<meta name=\"applicable-device\" content=\"pc,mobile\">\n" +
                "<meta name=\"MobileOptimized\" content=\"width\"/>\n" +
                "<meta name=\"HandheldFriendly\" content=\"true\"/>\n" +
                "<link rel=\"stylesheet\" href=\"https://mini.eastday.com/toutiaoh5/css/photoswipe/photoswipe.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://mini.eastday.com/toutiaoh5/css/common.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://mini.eastday.com/toutiaoh5/css/page_details_v5.min.css\">\n";
        //拼接标题
        html += "<title>" + title + "</title>\n" +
                "</head>\n" +
                "<body>\n";
        //拼接内容
        html += content + "\n";
        //拼接尾部
        html += "</body>\n" +
                "</html>";
        return html;
    }

    /**
     * 通过传入的标题title和内容content转化为HTML格式的字符串，并返回
     * @param title 标题
     * @param content 内容
     * @return HTML格式的字符串
     */
    public static String convertToRichHtmlFormat(String title, String content){
        //拼接头部
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"zh-cn\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "<meta content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no\" name=\"viewport\">\n" +
                "<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "<meta name=\"format-detection\" content=\"telephone=no\">\n" +
                "<meta name=\"format-detection\" content=\"email=no\">\n" +
                "<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\" />\n" +
                "<meta name=\"applicable-device\" content=\"pc,mobile\">\n" +
                "<meta name=\"MobileOptimized\" content=\"width\"/>\n" +
                "<meta name=\"HandheldFriendly\" content=\"true\"/>\n";
        //拼接标题
        html += "<title>" + title + "</title>\n" +
                "</head>\n" +
                "<body>\n";
        //拼接内容
        html += content + "\n";
        //拼接尾部
        html += "</body>\n" +
                "</html>";
        return html;
    }

    /**
     * 从HTML格式的字符串中获取Img的src值
     * @param html 传入的HTML格式字符串
     * @return 包含了每一个Img的src值的字符串集合
     */
    public static List<String> getImgPathListFromHtmlString(String html){
        List<String> imgPaths = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements imgs = document.select("img");
        if (imgs.size()==0) //如果没有插入图片，则返回空值
            return null;
        for(int i=0; i<imgs.size(); i++){
            String imgPath = imgs.get(i).attr("src");
            imgPaths.add(imgPath);
        }
//        Element article = document.getElementById("content");
//        String articleStr = article.toString();
//        String title = document.title();
//        String date = document.getElementById("datetime_forapp").attr("value");
//        //将字符串转为HTML格式
//        String str = FileUtil.convertToHtmlFormat(title, articleStr);
//        String strTest = null;
//        //将该字符串存入内存中
//        try {
//            FileUtil.writeInternal(this, title+".html", str);
//            strTest = "证明是否成功：\n" + FileUtil.readInternal(this, title+".html");
//            String filePath = FileUtil.getFilePathFromWriteInternal(this, title+".html");
//            File file = new File(filePath);
//            final BmobFile bmobFile = new BmobFile(file);
//            bmobFile.upload(new UploadFileListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e==null) {
//                        //将文件与数据库关联
//                        //saveFile(bmobFile);
//                        Log.d("done", "上传成功");
//                    } else {
//                        Log.d("failed", "上传失败");
//                    }
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //showResponse(strTest);
        return imgPaths;
    }
}
