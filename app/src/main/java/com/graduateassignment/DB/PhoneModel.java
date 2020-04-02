package com.graduateassignment.DB;

/**
 * Created by admin on 2020/4/1.
 */

import cn.bmob.v3.BmobObject;

/**
 * 手机型号类
 */
public class PhoneModel extends BmobObject{
    private String model;
    private String name;
    private String brand;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
