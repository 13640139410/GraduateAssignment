package com.graduateassignment.Test;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.graduateassignment.DB.PhoneModel;
import com.graduateassignment.R;
import com.graduateassignment.Util.FileUtil;
import com.graduateassignment.Util.ToastUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListListener;

public class GetmobiledataActivity extends AppCompatActivity implements View.OnClickListener{

    private String fileName = "mobiledata.txt";
    private List<BmobObject> phoneModelList = new ArrayList<>();
    private List<String> brandList = new ArrayList<>();
    private int times=21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmobiledata);
        findViewById(R.id.act_getmobiledata_downloaddata).setOnClickListener(this);
        findViewById(R.id.act_getmobiledata_changetojson).setOnClickListener(this);
        findViewById(R.id.act_getmobiledata_puttobmob).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_getmobiledata_downloaddata:
                downloadData();
                break;
            case R.id.act_getmobiledata_changetojson:
                changetoJson();
                break;
            case R.id.act_getmobiledata_puttobmob:
                putToBmob();
                break;
        }
    }

    private void downloadData(){
        BmobFile bmobfile =new BmobFile("mobiledata.txt","",
                "https://bmob-cdn-27889.bmobpay.com/2020/04/01/a5ad0c9840463877802c527cc35cda2a.txt");
        File file = this.getFilesDir();
        //filename = file.getAbsolutePath() + File.separator + filename;Environment.getExternalStorageDirectory()
        File saveFile = new File(file.getAbsolutePath(), fileName);
        bmobfile.download(saveFile, new DownloadFileListener() {
            @Override
            public void onStart() {ToastUtil.show(GetmobiledataActivity.this,"下载中...");}
            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    ToastUtil.show(GetmobiledataActivity.this,"下载成功,保存路径:"+savePath);
                    Log.i("bmob","下载成功,保存路径:"+savePath);
                }else{
                    ToastUtil.show(GetmobiledataActivity.this,"下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }
            @Override
            public void onProgress(Integer value, long newworkSpeed) {Log.i("bmob","下载进度："+value+","+newworkSpeed);}
        });
    }

    private void changetoJson(){
        try {
            String brandData="";
            String mobileData="";
            readInternal(this,fileName);
            for(String str:brandList){
                brandData +="\n品牌名称:"+str+str.length();
            }
            for(BmobObject phoneModel:phoneModelList){
                PhoneModel obj = (PhoneModel)phoneModel;
                mobileData +="\n型号："+obj.getModel()+
                            "\n名称："+obj.getName()+
                            "\n品牌："+obj.getBrand()+"\n";
            }
            TextView textView = (TextView) findViewById(R.id.act_getmobiledata_mobiledata);
            textView.setText(brandData + mobileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInternal(Context context, String filename) throws IOException{
        StringBuilder sb = new StringBuilder("");

        //获取文件在内存卡中files目录下的路径
        File file = context.getFilesDir();
        filename = file.getAbsolutePath() + File.separator + filename;

        //打开文件输入流
        FileInputStream inputStream = new FileInputStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String lineStr;//按行读取 将每次读取一行的结果赋值给lineStr
        int i=0;
        while ((lineStr = bufferedReader.readLine()) != null) {
            if(i!=0){
                String brand = lineStr.substring(lineStr.lastIndexOf(":")+1,lineStr.length());
                PhoneModel phoneModel = new PhoneModel();
                phoneModel.setModel(lineStr.substring(0,lineStr.indexOf(":")));
                phoneModel.setName(lineStr.substring(lineStr.indexOf(":")+1,lineStr.lastIndexOf(":")));
                brand = judge(brand);
                phoneModel.setBrand(brand);
                if(isPhone(brand)){
                    if(!brandList.contains(brand)){ brandList.add(brand); }
                    phoneModelList.add(phoneModel);
                }
            }
            i++;
        }
        bufferedReader.close();//关闭IO
    }

    private String judge(String brand){
        if(brand.indexOf("乐")!=-1) {brand = "乐视";}
        else if((brand.indexOf("荣耀")!=-1&&brand.length()!=2)||brand.indexOf("Honor")!=-1){brand="荣耀";}
        else if(brand.indexOf("坚果")!=-1){brand="坚果";}
        else if(brand.indexOf("Moto")!=-1||brand.indexOf("moto")!=-1||brand.indexOf("motorola")!=-1){brand="摩托罗拉";}
        else if(brand.indexOf("Meizu")!=-1||brand.indexOf("MEIZU")!=-1){brand="魅族";}
        else if(brand.indexOf("Mi")!=-1&&brand.length()==2){brand="小米";}
        else if(brand.indexOf("红米手机")!=-1||brand.indexOf("Redmi")!=-1){brand="红米";}
        else if(brand.indexOf("黑鲨游戏手机")!=-1){brand="黑鲨";}
        else if(brand.indexOf("红魔电竞游戏手机")!=-1){brand="红魔";}
        else if(brand.indexOf("VIVO")!=-1){brand="vivo";}
        return brand;
    }

    private boolean isPhone(String brand){
        if(brand.indexOf("Apple")!=-1){
            return false;
        }else if(brand.indexOf("小米电视")!=-1){
            return false;
        }else if(brand.indexOf("小米全面屏电视")!=-1){
            return false;
        }else if(brand.indexOf("小米壁画电视")!=-1){
            return false;
        }else if(brand.indexOf("小米电视主机")!=-1){
            return false;
        }else if(brand.indexOf("小米家庭影院")!=-1){
            return false;
        }else if(brand.indexOf("小米盒子")!=-1){
            return false;
        }else if(brand.indexOf("新小米盒子")!=-1){
            return false;
        }else if(brand.indexOf("小米盒子增强版")!=-1){
            return false;
        }else{}
        return true;
    }

    private void putToBmob(){
        List<BmobObject> list = new ArrayList<>();
        for(int j=(times-1)*50;j<times*50;j++){
            if(j>=phoneModelList.size()){
                ToastUtil.show(this,"最后一次传输");
                break;
            }
            PhoneModel phoneModel = (PhoneModel) phoneModelList.get(j);
            list.add(phoneModel);
        }
        times++;
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            ToastUtil.show(GetmobiledataActivity.this, "第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," + result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            ToastUtil.show(GetmobiledataActivity.this, "第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex.getErrorCode());

                        }
                    }
                } else {
                    ToastUtil.show(GetmobiledataActivity.this, "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
