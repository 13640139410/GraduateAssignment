package com.graduateassignment.DB;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2020/2/28.
 */

public class User extends BmobUser {
    private BmobFile avator;
    private String signature;
    private String identity;

    public BmobFile getAvator() {
        return avator;
    }
    public void setAvator(BmobFile avator) {
        this.avator = avator;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getIdentity() { return identity; }
    public void setIdentity(String identity) { this.identity = identity; }
}
