package com.graduateassignment.Fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.graduateassignment.Activity.MainActivity;
import com.graduateassignment.R;

import java.io.FileNotFoundException;

import jp.wasabeef.richeditor.RichEditor;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestRichEditorFragment extends Fragment {

    private static final String str = "<article id=\"J_article\" class=\"J-article article\">\n" +
            "<div id=\"title\">\n" +
            "<div class=\"article-title\">\n" +
            "<h1 class=\"title\">Python开发能从事数据分析吗</h1>\n" +
            "</div>\n" +
            "<div class=\"article-src-time\">\n" +
            "<span class=\"src\">2020-03-07 20:07&nbsp;&nbsp;&nbsp;&nbsp;来源：大连千锋教育</span>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div id=\"content\" class=\"J-article-content article-content\">\n" +
            "<p class=\"section txt\">Python不仅是人工智能时代最佳的编程语言，同时也是数据分析、科学运算的首选编程语言。学习Python就业方向多，比如Web网站开发、人工智能等。</p>\n" +
            "<figure class=\"section img\">\n" +
            "<a class=\"img-wrap\" style=\"padding-bottom: 73.43%;\" data-href=\"https://08imgmini.eastday.com/mobile/20200307/20200307200737_258516f41ec87f67accd51332a28a7f6_1.jpeg\" data-size=\"700x514\">\n" +
            "<img width=\"100%\" alt=\"\" src=\"https://08imgmini.eastday.com/mobile/20200307/20200307200737_258516f41ec87f67accd51332a28a7f6_1.jpeg\" data-weight=\"700\" data-width=\"700\" data-height=\"514\">\n" +
            "</a>\n" +
            "</figure>\n" +
            "<p class=\"section txt\">Python数据分析师需要掌握哪些技能，从各大招聘网站对其的基本任职要求可以了解一二：</p>\n" +
            "<p class=\"section txt\">1、熟练掌握Python语言，有数据工程领域开发经验，拥有数据分析建模理论、熟悉数据分析建模过程；</p>\n" +
            "<p class=\"section txt\">2、熟悉NumPy、SciPy和Pandas等数据分析工具的使用；</p>\n" +
            "<p class=\"section txt\">3、熟悉数据可视化工具；</p>\n" +
            "<p class=\"section txt\">4、有丰富的数据分析，数据挖掘项目经验。</p>\n" +
            "<p class=\"section txt\">有部分同学想知道如何利用Python进行数据分析，首先，你要掌握一些常见的算法。比如knn、决策树、支持向量机、朴素贝叶斯等等，这些算法都是机器学习领域非常常见的算法，也具有比较广泛的应用场景。其次，还需要掌握一系列库的使用，包括Numpy（矩阵运算库）、Scipy（统计运算库）、Matplotlib（绘图库）、pandas（数据集操作）、Sympy（数值运算库）等库，这些库在Python进行数据分析时有广泛的应用。</p>\n" +
            "\n" +
            "</div>\n" +
            "</article>\n";
    public static final int CHOOSE_PHOTO = 2;
    private MainActivity mainActivity;
    private RichEditor mEditor;
    private TextView mPreview;

    public TestRichEditorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_richeditor, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mainActivity = (MainActivity) getActivity();
        mEditor = (RichEditor) mainActivity.findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        //mEditor.setPlaceholder("Insert text here...");
        mEditor.setHtml(str);
        mPreview = (TextView) mainActivity.findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });

        mainActivity.findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        mainActivity.findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        mainActivity.findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        mainActivity.findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        mainActivity.findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        mainActivity.findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        mainActivity.findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        mainActivity.findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        mainActivity.findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        mainActivity.findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        mainActivity.findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        mainActivity.findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        mainActivity.findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        mainActivity.findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        mainActivity.findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        mainActivity.findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        mainActivity.findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        mainActivity.findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        mainActivity.findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        mainActivity.findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        mainActivity.findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        mainActivity.findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        mainActivity.findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
//                mEditor.insertImage("https://bmob-cdn-27889.bmobpay.com/2020/03/01/3ce0e904409453e18029eea251e1d8a1.png",
//                        "picvision\" style=\"max-width:100% ");
                if (ContextCompat.checkSelfPermission(mainActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
                        PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(mainActivity, new
                            String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }
            }
        });

        mainActivity.findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLink("https://www.baidu.com", "wasabeef");
            }
        });
        mainActivity.findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
        mainActivity.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreview.setText(mEditor.getHtml());
            }
        });
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        Log.d("MainActivity","openAlbum()");
        mainActivity.startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Log.d("MainActivity","handleImageOnKitKat()");
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mainActivity, uri)) {
            // 如果是document类型的Uri， 则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.
                    getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://dow0-opopklolonloads/public_downloads"), Long.valueOf(docId));
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
        Cursor cursor = mainActivity.getContentResolver().query(uri, null, selection, null, null);
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
            //mEditor.insertImage(imagePath,"picvision\" style=\"max-width:100% ");
            Toast.makeText(mainActivity,imagePath,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mainActivity,"无法导入图片",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mainActivity, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
                break;
            default:
        }
    }
}
