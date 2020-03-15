package com.graduateassignment.Json;

/**
 * Created by admin on 2020/2/27.
 */

public class ArticleResult {
    private String stat;
    private ArticleData data;

    public String getStat() {
        return stat;
    }
    public void setStat(String stat) {
        this.stat = stat;
    }
    public ArticleData getData() {
        return data;
    }
    public void setData(ArticleData data) {
        this.data = data;
    }
}
