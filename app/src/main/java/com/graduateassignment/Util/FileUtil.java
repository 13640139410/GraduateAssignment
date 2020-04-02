package com.graduateassignment.Util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    public static final int CHOOSE_PHOTO = 2;

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
        return imgPaths;
    }

    /**
     * 4.4及以上的版本处理图片
     * @param context
     * @param data
     * @return
     */
    public static String handleImageOnKitKat(Context context,Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是document类型的Uri， 则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.
                    getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(context,contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri， 则使用普通方式处理
            imagePath = getImagePath(context,uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri， 直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;//返回图片路径
    }

    /**
     * 4.4版本以下的系统处理图片
     * @param context
     * @param data
     * @return
     */
    public static String handleImageBeforeKitKat(Context context,Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(context,uri, null);
        return imagePath;
    }

    public static String getImagePath(Context context,Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 根据传入活动打开相册
     * @param activity
     */
    public static void openAlbum(Activity activity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        Log.d("TestRichEditor","openAlbum()");
        activity.startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    /**
     * 根据文件路径在图片控件中展示图片
     * @param context
     * @param imageView
     * @param imagePath
     */
    public static void displayImage(Context context, ImageView imageView, String imagePath){
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
