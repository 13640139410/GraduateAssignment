package com.graduateassignment.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.Comment;
import com.graduateassignment.DB.Content;
import com.graduateassignment.DB.User;
import com.graduateassignment.Fragment.CommentFragment;
import com.graduateassignment.R;
import com.graduateassignment.Util.SoftKeyBoardListener;
import com.graduateassignment.Util.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 此活动用于浏览一篇文章
 */
public class ArticleActivity extends BaseActivity implements View.OnClickListener{

    private int i=0;//用于解决键盘事件监听处理两次的BUG
    private Article article;//获取传入活动的文章对象
    private Button focusBtn;//关注按钮
    private Content mContent;//内容对象
    private WebView webView;//展示文章内容
    private TextView commentBtn;//评论按钮
    private BottomSheetLayout bottomSheetLayout;//评论弹框
    private EditText commentEditText;//评论框
    private TextView likeTextView;//点赞按钮
    private TextView commentTextView;//评论按钮
    private TextView commitTextView;//发布评论按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        article = (Article) getIntent().getSerializableExtra("article");
        webView = (WebView) findViewById(R.id.act_article_content) ;
        focusBtn = (Button) findViewById(R.id.act_article_focus);
        commentBtn = (TextView) findViewById(R.id.act_article_commentbtn);//评论按钮
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.act_article_commentlayout);
        likeTextView = (TextView) findViewById(R.id.act_article_like);
        commitTextView = (TextView) findViewById(R.id.act_article_submitcomment);
        commentEditText = (EditText) findViewById(R.id.act_article_commentedittext);
        focusBtn.setOnClickListener(this);
        commentBtn.setOnClickListener(this);
        init();
        SoftKeyBoardListener.setListener(ArticleActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //隐藏点赞和评论，显示发布评论
//                likeTextView.setVisibility(View.GONE);
//                commentTextView.setVisibility(View.GONE);
//                commitTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void keyBoardHide(int height) {
//                //显示点赞和评论，隐藏发布评论
//                likeTextView.setVisibility(View.VISIBLE);
//                commentTextView.setVisibility(View.VISIBLE);
//                commitTextView.setVisibility(View.GONE);
            }
        });
        commentEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String content = v.getText().toString();
                User user = BmobUser.getCurrentUser(User.class);
                if(i==0){
                    ++i;
                    return false;
                }
                if(user==null){
                    //获取用户
                    i=0;
                    User user1 = new User();
                    user1.setObjectId("jJGL888D");
                    CreateComment(article,user1,content);
                }else{
                    CreateComment(article,user,content);
                }
                return false;
            }
        });
    }

    /**
     * 初始化文章详情页面
     */
    private void init(){
        //获取文章内容对象ID
        final String objId = article.getContent().getObjectId();
        BmobQuery<Content> contentBmobQuery = new BmobQuery<Content>();
        contentBmobQuery.getObject(objId, new QueryListener<Content>() {
            @Override
            public void done(Content content, BmobException e) {
                mContent = content;
                String url = mContent.getContent().getUrl();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(url);
                webView.setWebViewClient(new WebViewClient());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_article_focus:
                if(focusBtn.getText().toString().equalsIgnoreCase("关注"))
                    focusBtn.setText("已关注");
                else if(focusBtn.getText().toString().equalsIgnoreCase("已关注"))
                    focusBtn.setText("关注");
                break;
            case R.id.act_article_commentbtn:
                new CommentFragment().newInstance(article).show(getSupportFragmentManager(),R.id.act_article_commentlayout);
                break;
            case R.id.act_article_submitcomment:
                //获取评论框内容
                ToastUtil.show(this,"asdasd");
                //final String content =  commentEditText.getText().toString();
//                User user = BmobUser.getCurrentUser(User.class);
//                if(user == null){
//                    ToastUtil.show(this,"没有用户给你获取");
//                }else{
//                    ToastUtil.show(this,"you");
//                }
//                break;
        }
    }

    /**
     * 生成一级评论
     * @param article 评论所属文章
     * @param user 评论所属用户
     * @param content 评论内容
     */
    private void CreateComment(final Article article,final User user, final String content){
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setCommentator(user);
        comment.setCommentLevel("1");
        comment.setContent(content);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    ToastUtil.show(ArticleActivity.this,"评论成功");
                }else{
                    ToastUtil.show(ArticleActivity.this,"评论失败");
                }
            }
        });
    }
}
