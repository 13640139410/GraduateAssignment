package com.graduateassignment.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.graduateassignment.DB.Comment;
import com.graduateassignment.R;

/**
 * 对特定评论进行回复时，进行点击按钮会弹出此评论
 * A simple {@link Fragment} subclass.
 */
public class ReplycommentFragment extends BottomSheetFragment {

    private static String ARG_PARAM1 = "param1";

    public ReplycommentFragment() { }

    public static ReplycommentFragment newInstance(Comment comment) {
        ReplycommentFragment fragment = new ReplycommentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, comment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_replycomment, container, false);
    }

}
