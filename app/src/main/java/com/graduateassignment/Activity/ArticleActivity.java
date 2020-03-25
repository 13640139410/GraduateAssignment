package com.graduateassignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.Comment;
import com.graduateassignment.DB.Content;
import com.graduateassignment.DB.Subscribe;
import com.graduateassignment.DB.User;
import com.graduateassignment.Fragment.CommentFragment;
import com.graduateassignment.R;
import com.graduateassignment.Util.SoftKeyBoardListener;
import com.graduateassignment.Util.ToastUtil;
import com.graduateassignment.Util.UserUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 此活动用于浏览一篇文章
 */
public class ArticleActivity extends BaseActivity implements View.OnClickListener{

    private static String HAS_SUBSCRIBED = "已关注";
    private static String NOT_SUBSCRIBED = "关注";

    private int i=0;//用于解决键盘事件监听处理两次的BUG
    private Article article;//获取传入活动的文章对象
    private User author;//文章作者
    private Button focusBtn;//关注按钮
    private Content mContent;//内容对象
    private WebView webView;//展示文章内容
    private CircleImageView authorIconCircleImageView;//作者头像
    private TextView authorNameTextView;//作者名称
    private TextView authorSignatureTextView;//作者签名
    private TextView commentBtn;//评论按钮
    private BottomSheetLayout bottomSheetLayout;//评论弹框
    private EditText commentEditText;//评论框
    private TextView likeTextView;//点赞按钮
    private TextView commentTextView;//评论按钮
    private TextView commitTextView;//发布评论按钮
    private Subscribe meSubscribe;//当前已登录用户的订阅列表
    private Subscribe authorSubscribe;//作者的订阅列表
    private List<String> idols;//获取当前已登录用户的关注列表
    private List<String> fans;//获取作者的粉丝列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        article = (Article) getIntent().getSerializableExtra("article");
        author = article.getAuthor();
        authorIconCircleImageView = (CircleImageView) findViewById(R.id.act_article_author_avator);    //作者头像
        authorNameTextView = (TextView) findViewById(R.id.act_article_author_name);                     //作者名称
        authorSignatureTextView = (TextView) findViewById(R.id.act_article_author_signature);         //作者签名
        focusBtn = (Button) findViewById(R.id.act_article_focus);                                         //关注按钮
        webView = (WebView) findViewById(R.id.act_article_content) ;                                      //文章主体
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.act_article_commentlayout);          //评论碎片
        commentEditText = (EditText) findViewById(R.id.act_article_commentedittext);                   //评论框
        likeTextView = (TextView) findViewById(R.id.act_article_like);                                   //点赞按钮
        commentBtn = (TextView) findViewById(R.id.act_article_commentbtn);                              //评论按钮
        commitTextView = (TextView) findViewById(R.id.act_article_submitcomment);                      //发布评论按钮
        authorIconCircleImageView.setOnClickListener(this);
        focusBtn.setOnClickListener(this);                                                                  //点击事件 关注按钮
        commentBtn.setOnClickListener(this);                                                                //点击事件 评论碎片按钮
        commitTextView.setOnClickListener(this);                                                           //点击事件
        //点击事件 发布评论按钮
        //test();
        init();
//        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
//            @Override
//            public void keyBoardShow(int height) {
//                //隐藏点赞和评论，显示发布评论
////                likeTextView.setVisibility(View.GONE);
////                commentTextView.setVisibility(View.GONE);
////                commitTextView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void keyBoardHide(int height) {
////                //显示点赞和评论，隐藏发布评论
////                likeTextView.setVisibility(View.VISIBLE);
////                commentTextView.setVisibility(View.VISIBLE);
////                commitTextView.setVisibility(View.GONE);
//            }
//        });
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
                    toLoginActivity();
                }else{
                    CreateComment(article,user,content);
                }
                return false;
            }
        });
    }

    private void test(){
//        final User user = BmobUser.getCurrentUser(User.class);
//        List<String> idols = user.getIdols();
//        idols.clear();
//        user.setIdols(idols);
//        user.update(new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null)
//                    ToastUtil.show(ArticleActivity.this,"设空成功");
//                else
//                    ToastUtil.show(ArticleActivity.this,"设空失败:"+e.getMessage());
//            }
//        });
//        BmobQuery<Subscribe> meBmobQuery = new BmobQuery<>();
//        meBmobQuery.addWhereEqualTo("user",BmobUser.getCurrentUser(User.class));
//        meBmobQuery.findObjects(new FindListener<Subscribe>() {
//            @Override
//            public void done(List<Subscribe> list, BmobException e) {
//                if(e==null){
//                    idols = list.get(0).getIdols();
//                    if(e==null)
//                        idols = new ArrayList<String>();
//                }else{
//                    ToastUtil.show(ArticleActivity.this,e.getMessage());
//                }
//            }
//        });
    }

    /**
     * 初始化文章详情页面
     */
    private void init(){
        User me = BmobUser.getCurrentUser(User.class);
        //初始化作者头像，名称，签名
        if(author.getAvator()!=null){
            Glide.with(this).load(author.getAvator().getUrl()).centerCrop().into(authorIconCircleImageView);
        }else{
            authorIconCircleImageView.setImageResource(R.drawable.icon_notlogin_32);
        }
        authorNameTextView.setText(author.getUsername());
        authorSignatureTextView.setText(author.getSignature());

        if(me!=null){//如果用户已登录
            if(BmobUser.getCurrentUser(User.class).getObjectId().equalsIgnoreCase(author.getObjectId())){
                focusBtn.setEnabled(false);
            }
            //获取当前已登录用户的关注列表和作者的粉丝列表
            BmobQuery<Subscribe> mebmobQuery = new BmobQuery<>();
            mebmobQuery.addWhereEqualTo("user",BmobUser.getCurrentUser(User.class));
            mebmobQuery.findObjects(new FindListener<Subscribe>() {
                @Override
                public void done(List<Subscribe> list, BmobException e) {
                    if(e==null) {
                        meSubscribe = list.get(0);
                        idols = list.get(0).getIdols();
                        if(idols==null){
                            idols=new ArrayList<String>();
                            focusBtn.setText(NOT_SUBSCRIBED);//设置关注按钮
                        }
                        ToastUtil.show(ArticleActivity.this,"获取到了当前用户的关注列表");
                    } else {
                        ToastUtil.show(ArticleActivity.this,e.getMessage());
                    }
                }
            });
            BmobQuery<Subscribe> authorbmobQuery = new BmobQuery<>();
            authorbmobQuery.addWhereEqualTo("user",author);
            authorbmobQuery.findObjects(new FindListener<Subscribe>() {
                @Override
                public void done(List<Subscribe> list, BmobException e) {
                    if(e==null) {
                        authorSubscribe = list.get(0);
                        fans = list.get(0).getFans();
                        if(fans==null){
                            fans=new ArrayList<String>();
                            focusBtn.setText(NOT_SUBSCRIBED);
                        }else if(fans.contains(BmobUser.getCurrentUser(User.class).getObjectId())){
                            focusBtn.setText(HAS_SUBSCRIBED);
                        }
                        ToastUtil.show(ArticleActivity.this,"获取到了作者的粉丝列表");
                    } else {
                        ToastUtil.show(ArticleActivity.this,e.getMessage());
                    }
                }
            });
        }else{
            commentEditText.setEnabled(false);
            commentEditText.setHint("请登录后进行评论~");
        }

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
            case R.id.act_article_author_avator://点击用户头像
                if(BmobUser.getCurrentUser()!=null){//若用户已登录
                    Intent intent = new Intent(ArticleActivity.this,MeActivity.class);
                    intent.putExtra("USER",author);
                    startActivity(intent);
                }else{//若用户未登录
                    toLoginActivity();
                }
                break;
            case R.id.act_article_focus://点击关注按钮
                if(UserUtil.isLogin()){
                    //若用户已登录
                    if(focusBtn.getText().toString().equalsIgnoreCase(NOT_SUBSCRIBED)) {//如果用户未关注
                        focusBtn.setText(HAS_SUBSCRIBED);
                        focusAuthor();//关注作者
                    } else if(focusBtn.getText().toString().equalsIgnoreCase(HAS_SUBSCRIBED)) {//如果用户已关注
                        focusBtn.setText(NOT_SUBSCRIBED);
                        notFocusAuthor();//取关作者
                    }
                }else{
                    toLoginActivity();
                }
                break;
            case R.id.act_article_commentbtn:
                new CommentFragment().newInstance(article).show(getSupportFragmentManager(),R.id.act_article_commentlayout);
                break;
            case R.id.act_article_submitcomment:
                ToastUtil.show(this,"我点了发布按钮");
                break;
        }
    }

    //去往登录活动
    private void toLoginActivity(){
        //若用户未登录
        Intent intent = new Intent(ArticleActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 关注行为
     */
    private void focusAuthor(){
        idols.add(author.getObjectId());
        meSubscribe.setIdols(idols);
        meSubscribe.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(ArticleActivity.this,"关注成功");
                }else{
                    ToastUtil.show(ArticleActivity.this,"关注失败");
                }
            }
        });
        fans.add(BmobUser.getCurrentUser().getObjectId());
        authorSubscribe.setFans(fans);
        authorSubscribe.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(ArticleActivity.this,"被关注成功");
                }else{
                    ToastUtil.show(ArticleActivity.this,"被关注失败");
                }
            }
        });
    }

    /**
     * 取关行为
     */
    private void notFocusAuthor(){
        idols.remove(author.getObjectId());
        meSubscribe.setIdols(idols);
        meSubscribe.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(ArticleActivity.this,"取关成功");
                }else{
                    ToastUtil.show(ArticleActivity.this,"取关失败");
                }
            }
        });
        fans.remove(BmobUser.getCurrentUser().getObjectId());
        authorSubscribe.setFans(fans);
        authorSubscribe.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(ArticleActivity.this,"被取关成功");
                }else{
                    ToastUtil.show(ArticleActivity.this,"被取关失败");
                }
            }
        });
    }

    /**
     * 生成一级评论
     * @param article 评论所属文章
     * @param user 评论所属用户
     * @param content 评论内容
     */
    private void CreateComment(final Article article,final User user, final String content){
        Comment comment = new Comment();
        comment.setArticle(article);comment.setCommentator(user);comment.setCommentLevel("1");
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
