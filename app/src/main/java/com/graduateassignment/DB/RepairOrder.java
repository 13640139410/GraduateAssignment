package com.graduateassignment.DB;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2020/4/4.
 */

public class RepairOrder extends BmobObject{
    private User customer;
    private MaintenancePoint serviceProvider;
    private PhoneModel phoneModel;
    private String province;
    private String city;
    private String district;
    private String address;
    private String phone;
    private String comment;
    private List<String> problemList;
    private String status;

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public MaintenancePoint getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(MaintenancePoint serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public PhoneModel getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(PhoneModel phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<String> problemList) {
        this.problemList = problemList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        String problems = "";
        for(String problem:this.getProblemList()){
            problems+=problem+"; ";
        }
        String str = "";
        str += "省:" + this.getProvince() +
                "市:" + this.getCity() +
                "区:" + this.getDistrict() +
                "顾客:" + this.getCustomer().getUsername() +
                "地址:" + this.getAddress() +
                "状态:" + this.getStatus() +
                "问题:" + problems +
                "电话:" + this.getPhone() +
                "备注:" + this.getComment() ;
        return str;
    }

}
