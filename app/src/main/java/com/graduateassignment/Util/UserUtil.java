package com.graduateassignment.Util;

import android.content.Context;

import com.graduateassignment.DB.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2020/3/21.
 */

public class UserUtil {

    /**
     * 判断用户是否登录，若未登录，则跳转至登录界面
     */
    public static boolean isLogin(){
        if(BmobUser.getCurrentUser(User.class)==null){
            return false;
        }else{
            return true;
        }
    }
}
