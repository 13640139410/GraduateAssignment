package com.graduateassignment.Util;

import com.amap.api.maps2d.model.LatLng;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by admin on 2020/3/13.
 */

public class LocationUtil {

    public static LatLng convertToLatLng(BmobGeoPoint bmobGeoPoint){
        double latitude = bmobGeoPoint.getLatitude();
        double longtitude = bmobGeoPoint.getLongitude();
        return new LatLng(latitude, longtitude);
    }
}
