package com.graduateassignment.Util;

import android.content.Context;

import com.graduateassignment.DB.User;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 将手机号码输出为格式：*** **** ****的手机号码
     * @param phone 传入的手机号码
     * @return
     */
    public static String phoneToString(String phone){
        String phone1 = phone.substring(0,3) + " " +
                        phone.substring(3,7) + " " +
                        phone.substring(7,11);
        return phone1;
    }

    public static void setUser(User oldUser,User newUser){
        oldUser.setObjectId(newUser.getObjectId());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setSignature(newUser.getSignature());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setMobilePhoneNumber(newUser.getMobilePhoneNumber());
    }
}
