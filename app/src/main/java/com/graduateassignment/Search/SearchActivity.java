package com.graduateassignment.Search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.graduateassignment.DB.Article;
import com.graduateassignment.R;

import cn.bmob.v3.BmobQuery;

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_ARTICLE = "search_article";
    private EditText editText;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private String search_cate = "";
    private static final String ARG_PARAM1 = "param1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_cate = getIntent().getStringExtra(ARG_PARAM1);
        initView();
    }

    private void initView(){
        editText = (EditText) findViewById(R.id.act_search_edittext);
        recyclerView = (RecyclerView) findViewById(R.id.act_search_recyclerview);
        findViewById(R.id.act_search_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String searchTxt = v.getText().toString();
                search(searchTxt);
                //此处进行搜索
                return false;
            }
        });
    }

    private void search(final String searchTxt){
        if(search_cate.equalsIgnoreCase(SEARCH_ARTICLE)){//若是搜索文章
            BmobQuery<Article> bmobQuery = new BmobQuery<>();
            
        }
    }
}
