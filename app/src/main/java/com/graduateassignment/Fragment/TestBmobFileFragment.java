package com.graduateassignment.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.Activity.MaintenancePointNearbyActivity;
import com.graduateassignment.Activity.TestGdmapActivity;
import com.graduateassignment.Activity.TestGdmapCameraActivity;
import com.graduateassignment.Activity.TestGdmapCustommarkerActivity;
import com.graduateassignment.Activity.TestGdmapDistrictActivity;
import com.graduateassignment.Activity.TestGdmapDistrictboundActivity;
import com.graduateassignment.Activity.TestGdmapEventsActivity;
import com.graduateassignment.Activity.TestGdmapLocationActivity;
import com.graduateassignment.Activity.TestGdmapMarkerActivity;
import com.graduateassignment.Activity.TestGdmapUisettingsActivity;
import com.graduateassignment.Activity.TestJsoupActivity;
import com.graduateassignment.Activity.TestJsoupDataActivity;
import com.graduateassignment.Activity.TestRicheditorActivity;
import com.graduateassignment.Activity.TestGdmapSubpoisearchActivity;
import com.graduateassignment.R;
import com.graduateassignment.Util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestBmobFileFragment extends Fragment {

    private MainActivity mainActivity;
    private Button myMapButton;
    private Button button;
    private Button existButton;
    private Button toGdmapBtn;
    private Button toGdmapeveBtn;
    private Button getToGdmapuiBtn;
    private ImageView imageView;
    private BmobFile bmobFile;
    private File saveFile;
    private Uri imageUri;

    public TestBmobFileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_bmob_file, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity) getActivity();
        button = (Button) mainActivity.findViewById(R.id.frag_test_bmob_file_btn);
        imageView = (ImageView) mainActivity.findViewById(R.id.frag_test_bmob_file_img);
        existButton = (Button) mainActivity.findViewById(R.id.frag_test_isexist);
        toGdmapBtn = (Button) mainActivity.findViewById(R.id.frag_test_bmobfile_togd_basic);
        toGdmapeveBtn = (Button) mainActivity.findViewById(R.id.frag_test_bmobfile_togd_events);
        getToGdmapuiBtn = (Button) mainActivity.findViewById(R.id.frag_test_bmobfile_togd_uisettings);
        //getFile();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestRicheditorActivity.class);
                startActivity(intent);
            }
        });
        existButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileIsExist();
            }
        });
        toGdmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapActivity.class);
                startActivity(intent);
            }
        });
        toGdmapeveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapEventsActivity.class);
                startActivity(intent);
            }
        });
        getToGdmapuiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapUisettingsActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmobfile_togd_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapCameraActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmobfile_togd_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapLocationActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmobfile_togd_district).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapDistrictActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmobfile_togd_districtbound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapDistrictboundActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmob_file_tojsoup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestJsoupActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmob_file_mymap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MaintenancePointNearbyActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmob_file_subpoisearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapSubpoisearchActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmob_file_marker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapMarkerActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmob_file_custommarker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestGdmapCustommarkerActivity.class);
                startActivity(intent);
            }
        });
        mainActivity.findViewById(R.id.frag_test_bmob_file_jsoup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestJsoupDataActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getFile(){
        BmobQuery<com.graduateassignment.DB.File> fileBmobQuery = new BmobQuery<>();
        fileBmobQuery.getObject("D0SuAAAd", new QueryListener<com.graduateassignment.DB.File>() {
            @Override
            public void done(com.graduateassignment.DB.File file, BmobException e) {
                if(e==null){
                    bmobFile = file.getMy_file();
                    show("成功获取文件"+bmobFile.getFilename());
                    Log.d("MainActivity","成功获取文件");
                }else{
                    show("未成功获取文件");
                    Log.d("MainActivity","未成功获取文件");
                }
            }
        });
    }

    private void showFile(BmobFile file){
         saveFile = new File(mainActivity.getExternalCacheDir(), "TestFrag12.png");
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                show("开始下载...");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    show("下载成功,保存路径:"+savePath);
                    //showImg(saveFile);
                    Log.d("MainActivity",savePath);
                }else{
                    show("下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

    private void uploadFile(){
        File file = new File(mainActivity.getExternalCacheDir().getAbsolutePath() + File.separator + "test4.txt");
        String str = null;
        try {
            str = FileUtil.readExternal(mainActivity,"test4.txt");
            Log.d("TestRichEditorActivity",str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null)
                    Log.d("done","上传成功");
                else
                    Log.d("faile","上传失败");
            }
        });
    }

    private void showImg(File saveFile){
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(mainActivity,"com.graduateassignment.fileprovider", saveFile);
        } else {
            imageUri = Uri.fromFile(saveFile);
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mainActivity.getContentResolver().openInputStream(imageUri));
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fileIsExist(){
        File file = new File(mainActivity.getExternalCacheDir(),"TestFrag12.png");
        if(file.exists()){
            show("这个文件确实存在");
            showImg(file);
        }else{
            show("这个文件不存在");
        }
    }

    private void show(String msg){
        Toast.makeText(mainActivity,msg,Toast.LENGTH_SHORT).show();
    }
}
