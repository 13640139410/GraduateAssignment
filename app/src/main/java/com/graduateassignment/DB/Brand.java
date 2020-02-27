package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2020/2/25.
 */

public class Brand extends BmobObject{

    private transient int icon;
    private String name;

    public int getIcon() {
        return icon;
    }
    public Brand setIcon(int icon) {
        this.icon = icon;
        return this;
    }
    public String getName() {
        return name;
    }
    public Brand setName(String name) {
        this.name = name;
        return this;
    }
}
