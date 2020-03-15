package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by admin on 2020/2/28.
 */

public class Content extends BmobObject {
    private BmobFile content;

    public BmobFile getContent() {
        return content;
    }
    public void setContent(BmobFile content) {
        this.content = content;
    }
}
