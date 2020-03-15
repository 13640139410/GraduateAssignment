package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2020/3/2.
 */

public class File extends BmobObject {
    private BmobFile my_file;

    public BmobFile getMy_file() {
        return my_file;
    }
    public void setMy_file(BmobFile my_file) {
        this.my_file = my_file;
    }
}
