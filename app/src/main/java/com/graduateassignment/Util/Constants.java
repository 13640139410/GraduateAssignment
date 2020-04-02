package com.graduateassignment.Util;

/**
 * Created by admin on 2020/3/6.
 */

import com.amap.api.maps2d.model.LatLng;

public class Constants {

    public static final int ERROR = 1001;// 网络异常
    public static final int ROUTE_START_SEARCH = 2000;
    public static final int ROUTE_END_SEARCH = 2001;
    public static final int ROUTE_BUS_RESULT = 2002;// 路径规划中公交模式
    public static final int ROUTE_DRIVING_RESULT = 2003;// 路径规划中驾车模式
    public static final int ROUTE_WALK_RESULT = 2004;// 路径规划中步行模式
    public static final int ROUTE_NO_RESULT = 2005;// 路径规划没有搜索到结果

    public static final int GEOCODER_RESULT = 3000;// 地理编码或者逆地理编码成功
    public static final int GEOCODER_NO_RESULT = 3001;// 地理编码或者逆地理编码没有数据

    public static final int POISEARCH = 4000;// poi搜索到结果
    public static final int POISEARCH_NO_RESULT = 4001;// poi没有搜索到结果
    public static final int POISEARCH_NEXT = 5000;// poi搜索下一页

    public static final int BUSLINE_LINE_RESULT = 6001;// 公交线路查询
    public static final int BUSLINE_id_RESULT = 6002;// 公交id查询
    public static final int BUSLINE_NO_RESULT = 6003;// 异常情况

    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度

    public static final String BRAND_360 = "360";
    public static final String BRAND_IPHONE = "iPhone";
    public static final String BRAND_IPAD = "iPad";
    public static final String BRAND_IPOD = "iPod";
    public static final String BRAND_HONOR = "荣耀";
    public static final String BRAND_HUAWEI = "华为";
    public static final String BRAND_HUAWEI1 = "HUAWEI";
    public static final String BRAND_HUAWEIMM = "华为麦芒";
    public static final String BRAND_HUAWEICX = "华为畅享";
    public static final String BRAND_HUAWEILY = "华为揽阅";
    public static final String BRAND_HUAWEIPB = "华为平板";
    public static final String BRAND_HUAWEICXPB = "华为畅享平板";
    public static final String BRAND_LENOVO = "联想";
    public static final String BRAND_LUX = "乐视";
    public static final String BRAND_MEIZU = "魅族";
    public static final String BRAND_MEILAN = "魅蓝";
    public static final String BRAND_MI = "小米";
    public static final String BRAND_MOTO = "摩托罗拉";
    public static final String BRAND_NOKIA = "Nokia";
    public static final String BRAND_NUBIA = "nubia";
    public static final String BRAND_HONGMO = "红魔";
    public static final String BRAND_ONEPLUS = "OnePlus";
    public static final String BRAND_OPPO = "OPPO";
    public static final String BRAND_GALAXY = "Galaxy";
    public static final String BRAND_HAMMER = "锤子";
    public static final String BRAND_NUT = "坚果";
    public static final String BRAND_VIVO = "vivo";
    public static final String BRAND_IQOO = "iQOO";
    public static final String BRAND_MIFLAT = "小米平板";
    public static final String BRAND_REDMI = "红米";
    public static final String BRAND_BLACKSHARP = "黑鲨";
    public static final String BRAND_POCO = "POCO";

}
