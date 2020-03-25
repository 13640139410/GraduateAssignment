package com.graduateassignment.DB;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2020/3/24.
 */

public class Subscribe extends BmobObject {
    private User user;
    private List<String> idols;
    private List<String> fans;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<String> getIdols() {
        return idols;
    }
    public void setIdols(List<String> idols) {
        this.idols = idols;
    }
    public List<String> getFans() {
        return fans;
    }
    public void setFans(List<String> fans) {
        this.fans = fans;
    }
}
