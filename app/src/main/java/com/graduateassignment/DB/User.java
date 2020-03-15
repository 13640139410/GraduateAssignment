package com.graduateassignment.DB;

import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 2020/2/28.
 */

public class User extends BmobUser {
    private String identity;

    public String getIdentity() { return identity; }
    public void setIdentity(String identity) { this.identity = identity; }
}
