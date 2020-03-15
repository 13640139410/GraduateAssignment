package com.graduateassignment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.graduateassignment.R;

/**
 * 此活动用于浏览一篇文章
 */
public class ArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
    }
}
