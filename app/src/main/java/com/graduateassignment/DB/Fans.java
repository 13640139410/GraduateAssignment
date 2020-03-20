package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2020/3/20.
 */

public class Fans extends BmobObject {
    private User idol;//被关注的用户
    private User fans;//关注他人的用户

    public User getIdol() {
        return idol;
    }
    public void setIdol(User idol) {
        this.idol = idol;
    }
    public User getFans() {
        return fans;
    }
    public void setFans(User fans) {
        this.fans = fans;
    }
}
