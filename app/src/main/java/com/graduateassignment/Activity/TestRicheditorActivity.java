package com.graduateassignment.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.graduateassignment.Adapter.AddArticleCategoryAdapter;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.ArticleCategory;
import com.graduateassignment.DB.Content;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.CustomPopWindow;
import com.graduateassignment.Util.FileUtil;
import com.graduateassignment.Util.SharedPreferencesUtil;
import com.graduateassignment.Util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import jp.wasabeef.richeditor.RichEditor;

/**
 * 编写文章时需输入图片，因为未做无图片判断
 */
public class TestRicheditorActivity extends BaseActivity {

    public static final int CHOOSE_PHOTO = 2;
    private EditText titleEditText;
    private RichEditor mEditor;
    private TextView mPreview;
    private CustomPopWindow mListPopWindow;//添加文章标签的弹窗
    private TextView addcategoryTextView;//添加标签
    private TextView putTextView;//发布文章按钮
    private User testUser;//文章作者
    private String title;//文章标题
    private String thumbNailUrl = null;//文章封面URL
    private AddArticleCategoryAdapter adapter;//文章类别适配器
    private List<String> SelectedList = new ArrayList<>();//所选择的标签
    private List<Integer> SelectedIndexList = new ArrayList<>();//所选择标签索引的集合
    private List<ArticleCategory> SelectedEntityList = new ArrayList<>();//所选择标签的实体集合

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_richeditor);
        getUserTest();//测试所用方法 后续可删除
        titleEditText = (EditText) findViewById(R.id.act_test_richeditor_title);

        Toolbar toolbar = (Toolbar) findViewById(R.id.act_test_richeditor_toolbar);//标题栏
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addcategoryTextView = (TextView) findViewById(R.id.act_test_richeditor_addcategory);//添加标签
        putTextView = (TextView) findViewById(R.id.act_test_richeditor_put); //发布文章
        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(R.color.gray);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //mEditor.setHtml(str);
        //mEditor.setPlaceholder("Insert text here...");
        //mEditor.setInputEnabled(false);

        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        addcategoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopListView();
            }
        });

        putTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });



        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(TestRicheditorActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TestRicheditorActivity.this, new
                            String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });

        findViewById(R.id.act_test_richeditor_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    //展示弹窗
    private void showPopListView(){
        View contentView = LayoutInflater.from(this).inflate(R.layout.window_addarticlecategory,null);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_test_richeditor,null);
        SelectedList.clear();
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(800,1200)//显示大小
                .create()
                .showAtLocation(parent, Gravity.CENTER,0, 0 );
        handleLogic(contentView);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     * @param contentView
     */
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListPopWindow!=null){
                    mListPopWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.window_sure:
                        List<BmobQuery<ArticleCategory>> queryList = new ArrayList<BmobQuery<ArticleCategory>>() ;
                        for(String articleCategory:SelectedList){
                            BmobQuery<ArticleCategory> eq = new BmobQuery<ArticleCategory>();
                            eq.addWhereEqualTo("name",articleCategory);
                            queryList.add(eq);
                        }
                        BmobQuery<ArticleCategory> query = new BmobQuery<ArticleCategory>();
                        BmobQuery<ArticleCategory> or = query.or(queryList);
                        or.findObjects(new FindListener<ArticleCategory>() {
                            @Override
                            public void done(List<ArticleCategory> list, BmobException e) {
                                if(e==null){
                                    //获取拿到的文章类别实体集合
                                    SelectedEntityList = list;
                                    ToastUtil.show(TestRicheditorActivity.this,
                                            "共有几个对象："+SelectedEntityList.size());
                                }else{
                                    ToastUtil.show(TestRicheditorActivity.this,
                                            "错误信息："+e.getMessage());
                                }
                            }
                        });
                        break;
                }
            }
        };
        contentView.findViewById(R.id.window_sure).setOnClickListener(listener);
    }


    private void handleListView(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.window_addcategory_revyvlerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Set<String> articleCategorys = SharedPreferencesUtil.getArticleCategory(this);
        List<String> list = new ArrayList<>();
        for(String articleCategory:articleCategorys){
            list.add(articleCategory);
        }
        adapter = new AddArticleCategoryAdapter(R.layout.item_articlecategory,list);
        setAdapterItemClick();
        recyclerView.setAdapter(adapter);
    }

    private void setAdapterItemClick(){
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //获取所选择的文章类别名称
                String articleCategory = adapter.getItem(position).toString();
                if(SelectedList.size()==0){

                    SelectedList.add(articleCategory);
                    SelectedIndexList.add(position);
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }else if(SelectedList.size()<=4&&!(SelectedList.contains(articleCategory))){

                    SelectedList.add(articleCategory);
                    SelectedIndexList.add(position);
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }else if(SelectedList.contains(articleCategory)&&SelectedList.size()<=5){

                    SelectedIndexList.remove(SelectedIndexList.indexOf(position));
                    SelectedList.remove(SelectedList.indexOf(articleCategory));
                    view.setBackgroundColor(getResources().getColor(R.color.white));

                }else if(!SelectedList.contains(articleCategory)&&SelectedList.size()==5){
                    ToastUtil.show(TestRicheditorActivity.this,"最多只能添加五个标签");
                }
            }
        });
    }

    private void getUserTest(){
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.getObject("oV7M888Q", new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    testUser = user;
                    ToastUtil.show(TestRicheditorActivity.this, "成功获取到用户，名称为"+testUser.getUsername());
                }else{
                    ToastUtil.show(TestRicheditorActivity.this, "未成功获取到用户，错误信息："+e.getMessage());
                }
            }
        });
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        Log.d("TestRichEditor","openAlbum()");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Log.d("MainActivity","handleImageOnKitKat()");
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri， 则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.
                    getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri， 则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri， 直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        Log.d("MainActivity","getImagePath()");
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        Log.d("MainActivity","displayImage()");
        if (imagePath != null) {
            mEditor.insertImage(imagePath,"picvision\" style=\"max-width:65% ");
            //Toast.makeText(this,imagePath,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"无法导入图片",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity","onActivityResult()");
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("MainActivity","onRequestPermissionsResult()");
        switch (requestCode) {
            case 1:if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
                break;
            default:
        }
    }

    /**
     * 提交所编写的文章
     */
    private void submit(){
        title = titleEditText.getText().toString();//标题
        String content = mPreview.getText().toString();//内容
        final String fileName = title + ".html";//文件名
        final String fileHtml = FileUtil.convertToRichHtmlFormat(title,content);//将标题和内容共同转化HTML格式
        List<String> imgPaths = FileUtil.getImgPathListFromHtmlString(fileHtml);//HTML格式字符串中的img src值的集合
        if(imgPaths != null){
            int size = imgPaths.size();
            //将图片上传
            final String[] imgPathArray = new String[size];
            int index = 0;
            for (String imgPath:imgPaths){
                imgPathArray[index] = imgPath;
                ++index;
            }
            BmobFile.uploadBatch(imgPathArray, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    if(list1.size()==imgPathArray.length){
                        //将HTML格式的字符串中的img的src兑换为数据库中的URL
                        final String html = replaceImgSrc(fileHtml,list1);//文件内容
                        final String Name = fileName;//文件名
                        //将HTML格式的字符串存为html文件并上传至数据库中
                        try {
                            thumbNailUrl = list1.get(0);
                            FileUtil.writeInternal(TestRicheditorActivity.this,Name,html);//将内容存入内存中,若文件存在，则覆盖。
                            Toast.makeText(TestRicheditorActivity.this,"存储文件成功",Toast.LENGTH_SHORT).show();
                            String filePath = FileUtil.getFilePathFromWriteInternal(TestRicheditorActivity.this,fileName);//获取文件路径
                            uploadFile(filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onProgress(int i, int i1, int i2, int i3) { }
                @Override
                public void onError(int i, String s) { }
            });
        }
    }

    /**
     * 将HTML格式的字符串中的图片src的值替换为数据库中的URL
     * @param html
     */
    private String replaceImgSrc(String html,List<String> bmobPaths){
        Document document = Jsoup.parse(html);
        Elements imgs = document.getElementsByTag("img");
        if(imgs.size()!=bmobPaths.size()){ //如果HTML所存在img组件数量与数据库路径集合大小不符，返回
            return null;
        }
        for(int i=0;i<bmobPaths.size();i++){
            imgs.get(i).attr("src",bmobPaths.get(i));
        }
        return document.outerHtml();
    }

    /**
     * 通过文件路径上传文件至Bmob数据库中
     * @param filePath
     */
    private void uploadFile(String filePath){
        File saveFile = new File(filePath);
        final BmobFile file = new BmobFile(saveFile);
        file.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null) {
                    Toast.makeText(TestRicheditorActivity.this, "上传文件成功", Toast.LENGTH_SHORT).show();
                    //上传文件成功后，设置关联的文章对象
                    linkToContent(file);
                }
                else
                    Log.d("TestRichEditorActivity","error:"+e.getMessage());
            }

        });
    }

    /**
     * 将File对象与Content对象关联
     * @param file
     */
    private void linkToContent(final BmobFile file) {
        final Content content = new Content();
        ToastUtil.show(TestRicheditorActivity.this,file.getFileUrl());
        content.setContent(file);
        content.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                ToastUtil.show(TestRicheditorActivity.this,"文件关联Content对象成功，ID:"+s);
                linkToArticle(content);
            }
        });
    }

    /**
     * 将Content对象与Article对象关联
     * @param content
     */
    private void linkToArticle(final Content content) {
        Article article = new Article();
        article.setThumbnail_pic_s(thumbNailUrl);
        article.setIsReptile("0");
        article.setTitle(this.title);
        BmobRelation articleCategorys = new BmobRelation();
        List<BmobPointer> bmobPointers = new ArrayList<>();
        if (SelectedEntityList.size() != 0){
            for (ArticleCategory articleCategory : SelectedEntityList) {
                BmobPointer bmobPointer = new BmobPointer(articleCategory);
                bmobPointers.add(bmobPointer);
            }
        articleCategorys.setObjects(bmobPointers);
        article.setArticleCategorys(articleCategorys);
        }else{
            ToastUtil.show(TestRicheditorActivity.this,
                    "还未添加标签");
            return;
        }
        article.setAuthor(BmobUser.getCurrentUser(User.class)==null?this.testUser:BmobUser.getCurrentUser(User.class));
        article.setDate(new BmobDate(new Date()));
        article.setContent(content);
        article.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                ToastUtil.show(TestRicheditorActivity.this,"Content关联Article对象成功，ID:"+s);
            }
        });
    }
}
