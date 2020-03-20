package com.graduateassignment.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.graduateassignment.Adapter.CommentAdapter;
import com.graduateassignment.DB.Article;
import com.graduateassignment.DB.Comment;
import com.graduateassignment.DB.CommentLevelOne;
import com.graduateassignment.DB.CommentLevelTwo;
import com.graduateassignment.DB.User;
import com.graduateassignment.R;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;
import razerdp.util.SimpleAnimationUtils;
import razerdp.widget.QuickPopup;

/**
 * 评论碎片
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends BottomSheetFragment {

    private String testStr="";
    private View commentView;
    private Article mArticle;
    private List<Comment> commentsLv1;//一级评论集合
    private List<Comment> commentsLv2;//二级评论集合
    private ArrayList<MultiItemEntity> multiList = new ArrayList<>();//评论实体集合
    private RecyclerView commentRecyclerView;//评论列表
    private CommentAdapter commentAdapter;//评论实体适配器
    private EditText replyEditText;//对某一特定评论进行回复的评论框
    private static final String ARG_PARAM1 = "param1";

    public static CommentFragment newInstance(Article article) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, article);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = (Article) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        commentView = inflater.inflate(R.layout.fragment_comment, container, false);
        //初始化评论列表
        //test();
        initComment(mArticle);
        return commentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        commentRecyclerView = (RecyclerView) getActivity().findViewById(R.id.frag_comment_comments);
    }

    //插入测试数据而用
    private void test(){
        //设置所附属一级评论
//        Comment comment1 = new Comment();
//        comment1.setObjectId("bQlO999C");
        //设置所附属文章
        //Article article = new Article();
        //article.setObjectId("0fb6579669");
        //设置所评论用户
        //User user = new User();
        //user.setObjectId("6cr5LLLb");

        //获取评论
        Comment comment = new Comment();
        comment.setObjectId("HvoMUUUa");

        //赋值
        //comment.setCommentator(user);
        //comment.setArticle(article);
        //comment.setComment(comment1);
        comment.setBeRepliedUser("华为科技");
        comment.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtil.show(getActivity(),"更新成功");
                }else{
                    ToastUtil.show(getActivity(),"更新失败");
                }
            }
        });
    }

    /**
     * 根据传入的文章对象获取该对象的评论
     * @param article 传入的文章对象
     */
    private void initComment(Article article){
        //获取该文章的所有一级评论
        BmobQuery<Comment> commentBmobQuery = new BmobQuery<Comment>();
        commentBmobQuery.addWhereEqualTo("article",article);
        commentBmobQuery.addWhereEqualTo("commentLevel","1");
        commentBmobQuery.include("commentator,article");
        commentBmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0) {
                        commentsLv1 = list;
                        //ToastUtil.show(getActivity(), "获取到了数据" + list.size()+
                                                    //"\n评论内容："+list.get(0).getContent()+
                                                    //"评论级别："+list.get(0).getCommentLevel()+
                                                    //"评论者："+list.get(0).getCommentator().getObjectId());
                        //获取二级评论列表
                        getCommentLevelTwo(commentsLv1);
                    } else{
                        ToastUtil.show(getActivity(),"没有评论");
                    }
                }else{
                    ToastUtil.show(getActivity(),"没获取到数据："+e.getMessage());
                }
            }
        });
    }

    /**
     * 根据传入的一级评论获取所有二级评论
     * @param comments 一级评论集合
     */
    private void getCommentLevelTwo(List<Comment> comments){
        List<BmobQuery<Comment>> queries = new ArrayList<BmobQuery<Comment>>();
        for(Comment comment:comments){
            BmobQuery<Comment> eq = new BmobQuery<Comment>();
            eq.addWhereEqualTo("comment",comment);
            queries.add(eq);
        }
        BmobQuery<Comment> bmobQuery = new BmobQuery<Comment>();
        bmobQuery.or(queries);
        bmobQuery.include("commentator,comment");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){
                    commentsLv2 = list;
                    //ToastUtil.show(getActivity(),"获取到数据："+list.size());
                    if(commentsLv2.size()!=0){
                        multiList = generateMultiItemList(commentsLv1,commentsLv2);
                        showCommentList(multiList);
//                        showMultiList(multiList);
                    }
                }else{
                    ToastUtil.show(getActivity(),"没获取到数据："+e.getMessage());
                }
            }
        });
    }

    private ArrayList<MultiItemEntity> generateMultiItemList(List<Comment> commentsLv1,List<Comment> commentsLv2){
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for(Comment comment:commentsLv1){
            //创建一级评论对象并赋值一级评论
            CommentLevelOne commentLevelOne = new CommentLevelOne(comment);
            String objectId = comment.getObjectId();
            for (Comment comment1:commentsLv2){
                if(comment1.getComment().getObjectId().equalsIgnoreCase(objectId)){
                    //ToastUtil.show(getActivity(),"相同！");
                    CommentLevelTwo commentLevelTwo = new CommentLevelTwo(comment1);
                    commentLevelOne.addSubItem(commentLevelTwo);
                }
            }
            res.add(commentLevelOne);
        }
        return res;
    }

    /**
     * 通过传入的评论实体类集合进行展示
     * @param multiList
     */
    private void showCommentList(ArrayList<MultiItemEntity> multiList){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        commentAdapter = new CommentAdapter(getActivity(), multiList);
        commentRecyclerView.setLayoutManager(manager);
        commentRecyclerView.setAdapter(commentAdapter);
        // 使一级列表默认展开
        commentAdapter.expandAll();
        //for (int i = multiList.size() - 1; i >= 0; i--) {
            //commentAdapter.expand(i, false, false);
        //}
        commentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CommentLevelOne commentLevelOne;
                CommentLevelTwo commentLevelTwo;
                Comment comment;
                switch (view.getId()){
                    case R.id.item_comment_level1_icon:
                        commentLevelOne = (CommentLevelOne) adapter.getItem(position);
                        comment = commentLevelOne.getComment();
                        break;
                    case R.id.item_comment_level1_like:
                        ToastUtil.show(getActivity(),"一级评论点赞");
                        break;
                    case R.id.item_comment_level1_reply:
                        commentLevelOne = (CommentLevelOne) adapter.getItem(position);
                        comment = commentLevelOne.getComment();
                        showReplyToComment(comment);
                        //replyToComment(view);
                        break;
                    case R.id.item_comment_level2_icon:
                        ToastUtil.show(getActivity(),"二级评论用户头像");
                        break;
                    case R.id.item_comment_level2_like:
                        ToastUtil.show(getActivity(),"二级评论点赞");
                        break;
                    case R.id.item_comment_level2_reply:
                        commentLevelTwo = (CommentLevelTwo) adapter.getItem(position);
                        comment = commentLevelTwo.getComment();
                        showReplyToComment(comment);
                        break;
                }
            }
        });
    }

    /**
     * 展示回复评论框的按钮
     * @param comment 传入用户要回复的指定评论
     */
    private void showReplyToComment(final Comment comment){
        //构建一个弹窗
        final QuickPopup quickPopup = QuickPopupBuilder.with(getContext())
                .contentView(R.layout.window_comment_replytocomment)
                .config(new QuickPopupConfig()
                        .gravity(Gravity.BOTTOM)
                        .withShowAnimation(SimpleAnimationUtils.getDefaultScaleAnimation(true))
                        .withDismissAnimation(SimpleAnimationUtils.getDefaultScaleAnimation(false))
                        )
                .show();
        final EditText editText = quickPopup.findViewById(R.id.window_replytocomment_edittext);
        editText.requestFocus();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(getActivity(),"点开了评论框");
            }
        });
        final TextView textView = quickPopup.findViewById(R.id.window_replytocomment_submitbtn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处日后改为获取当前用户
                User user = new User();
                user.setObjectId("jJGL888D");
                //获取评论回复框的内容
                String content = editText.getText().toString();
                //提交评论内容
                submitMyComment(comment,user,content);
                quickPopup.dismiss();
            }
        });
        final TextView beRepliedUser = quickPopup.findViewById(R.id.window_replytocomment_bereplieduser);
        beRepliedUser.setText("回复："+comment.getCommentator().getUsername());

    }

    /**
     * 提交对某一评论的回复
     * @param beRepiledComment 被回复的评论
     * @param user 进行评论的用户
     * @param content 评论内容
     */
    private void submitMyComment(final Comment beRepiledComment, final User user, final String content){
        //生成二级评论
        Comment comment = new Comment();
        comment.setArticle(beRepiledComment.getArticle());
        comment.setCommentator(user);
        comment.setCommentLevel("2");
        if(beRepiledComment.getCommentLevel().equalsIgnoreCase("2")) {
            comment.setComment(beRepiledComment.getComment());
        }else if(beRepiledComment.getCommentLevel().equalsIgnoreCase("1")){
            comment.setComment(beRepiledComment);
        }
        comment.setContent(content);
        comment.setBeRepliedUser(beRepiledComment.getCommentator().getUsername());
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    ToastUtil.show(getActivity(),"评论成功");
                }else {
                    ToastUtil.show(getActivity(),"评论失败");
                }
            }
        });
    }
}
