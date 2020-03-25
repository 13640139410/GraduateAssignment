package com.graduateassignment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoupDataActivity extends AppCompatActivity implements View.OnClickListener{

    private Button getBtn;
    private Button modifyBtn;
    private TextView beforeTxt;
    private TextView afterTxt;
    private String text = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title>test</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\t<p id=\"content\">我是content</p>\n" +
            "\t<a href=\"www.baidu.com\">百度</a>\n" +
            "\t<a href=\"www.bilibili.com\">哔哩哔哩</a>\n" +
            "</body>\n" +
            "</html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jsoup_data);
        getBtn = (Button) findViewById(R.id.act_jsouptest_get);
        modifyBtn = (Button) findViewById(R.id.act_jsouptest_modify);
        beforeTxt = (TextView) findViewById(R.id.act_jsouptest_textview_before);
        afterTxt = (TextView) findViewById(R.id.act_jsouptest_textview_after);
        beforeTxt.setText(text);
        getBtn.setOnClickListener(this);
        modifyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_jsouptest_get:
                jsoupget();
                break;
            case R.id.act_jsouptest_modify:
                jsoupmodify();
                break;
        }
    }

    private void jsoupget(){
        String html = beforeTxt.getText().toString();
        Document document = Jsoup.parse(html);
        Element content = document.getElementById("content");
        afterTxt.setText("id为content元素文本内容为："+content.text()+
                            "\nid为content元素html内容为："+content.outerHtml());
    }

    private void jsoupmodify(){
        String html = beforeTxt.getText().toString();
        Document document = Jsoup.parse(html);
        document.title("修改了标题");
        Element a = document.getElementsByTag("a").first();
        a.attr("href","lol.duowan.com");
        a.text("LOL多玩论坛");
        afterTxt.setText(document.outerHtml());
    }
}
