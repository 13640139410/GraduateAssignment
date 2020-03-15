package com.graduateassignment.DB;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by admin on 2020/3/13.
 */

public class MaintenancePoint extends BmobObject {
    private BmobGeoPoint coordinate;
    private String name;
    private String region;
    private User owner;
    private String phone;

    public BmobGeoPoint getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(BmobGeoPoint coordinate) {
        this.coordinate = coordinate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
