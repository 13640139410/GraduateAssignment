package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2020/3/8.
 */

public class ArticleCategory extends BmobObject {
    private String name;
    private BmobFile icon_48;
    private BmobFile icon_96;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BmobFile getIcon_48() {
        return icon_48;
    }
    public void setIcon_48(BmobFile icon_48) {
        this.icon_48 = icon_48;
    }
    public BmobFile getIcon_96() {
        return icon_96;
    }
    public void setIcon_96(BmobFile icon_96) {
        this.icon_96 = icon_96;
    }
}
