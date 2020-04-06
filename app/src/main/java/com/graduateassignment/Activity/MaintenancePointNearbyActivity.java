package com.graduateassignment.Activity;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.VisibleRegion;
import com.graduateassignment.DB.MaintenancePoint;
import com.graduateassignment.R;
import com.graduateassignment.Util.CustomPopWindow;
import com.graduateassignment.Util.LocationUtil;
import com.graduateassignment.Util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MaintenancePointNearbyActivity extends BaseActivity  implements LocationSource, View.OnClickListener,
        AMap.OnMarkerClickListener,AMapLocationListener {

    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private FloatingActionButton fab;//用于弹出附近维修点列表
    private String mProvince;//我的定位所处的省
    private String mCity;//我的定位所处的市
    private String mDistrict;//我的定位所处的区
    private BmobGeoPoint mBmobGeoPoint = null;//获取第一次定位的值
    private List<MaintenancePoint> maintenancePoints = new ArrayList<>();//我的定位所处区域的所有维修店
    private CustomPopWindow markerPopWindow;//维修点标记弹窗

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_point_nearby);
        fab = (FloatingActionButton) findViewById(R.id.act_maintencepointnearby_fab);
        fab.setOnClickListener(this);
        mapView = (MapView) findViewById(R.id.act_maintencepointnearby_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act_maintencepointnearby_fab:

                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        showPopView(marker);
        if(marker.isInfoWindowShown()){
            ToastUtil.show(MaintenancePointNearbyActivity.this,"显示着infowindow");
        }else{
            marker.showInfoWindow();
            ToastUtil.show(MaintenancePointNearbyActivity.this,"并未显示infowindow");
        }
        return false;
    }

    /**
     * 通过指定MARKER展示指定弹窗
     * @param marker
     */
    private void showPopView(Marker marker){
        MaintenancePoint maintenancePoint = (MaintenancePoint) marker.getObject();
        View contentView = LayoutInflater.from(this).inflate(R.layout.part_maintenancenearby_infowindow,null);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_maintenance_point_nearby,null);
        TextView phoneUi = (TextView) contentView.findViewById(R.id.part_maintenancenearby_infowin_phone);
        TextView nameUi = (TextView) contentView.findViewById(R.id.part_maintenancenearby_infowin_name);
        TextView addressUi =  (TextView) contentView.findViewById(R.id.part_maintenancenearby_infowin_address);
        nameUi.setText(maintenancePoint.getName());
        addressUi.setText("地址："+maintenancePoint.getDistrict());
        phoneUi.setText("电话："+maintenancePoint.getPhone());
        //创建并显示popWindow
        markerPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(contentView.getWidth(),contentView.getHeight())//显示大小
                .create()
                .showAtLocation(parent, Gravity.CENTER,0, -350 );
    }

    /**
     * 根据我的定位获取我附近的维修点的数据
     * @return 我附近的维修点实体集合
     */
    private void getMaintenancePointNearby(){
        ToastUtil.show(MaintenancePointNearbyActivity.this,"方法getMaintenancePointNearby");
        BmobQuery<MaintenancePoint> bmobQuery = new BmobQuery<MaintenancePoint>();
        bmobQuery.addWhereEqualTo("district",mDistrict);
        bmobQuery.findObjects(new FindListener<MaintenancePoint>() {
            @Override
            public void done(List<MaintenancePoint> list, BmobException e) {
                if(e==null){
                    maintenancePoints = list;
                    VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion(); // 获取可视区域、
                    LatLngBounds latLngBounds = visibleRegion.latLngBounds;// 获取可视区域的Bounds
                    //添加标记点
                    for(MaintenancePoint maintenancePoint:list){
                        LatLng latLng = LocationUtil.convertToLatLng(maintenancePoint.getCoordinate());
                        //判断该维修点是否在可视区域中
                        boolean isContain = latLngBounds.contains(latLng);
                        if (isContain) {
                            addMarkerToMap(latLng,maintenancePoint);
                        }
                    }
                }else {
                    ToastUtil.show(MaintenancePointNearbyActivity.this,"错误信息："+e.getMessage());
                }
            }
        });
    }

    private void addMarkerToMap(LatLng latLng,MaintenancePoint maintenancePoint){
        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .draggable(false);
        Marker marker = aMap.addMarker(markerOption);
        marker.setObject(maintenancePoint);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setOnMarkerClickListener(this);//设置Marker点击监听事件
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mProvince = amapLocation.getProvince();//获取我的定位所处的省
                mCity = amapLocation.getCity();//获取我的定位所处的市
                mDistrict = amapLocation.getDistrict();//获取我的定位所处的区
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if(mBmobGeoPoint == null){
                    ToastUtil.show(MaintenancePointNearbyActivity.this,"我所处的区域："+mDistrict);
                    double latitude = amapLocation.getLatitude();
                    double longtitude = amapLocation.getLongitude();
                    //获取第一次定位的值
                    mBmobGeoPoint = new BmobGeoPoint(longtitude,latitude);
                    ToastUtil.show(MaintenancePointNearbyActivity.this, mBmobGeoPoint.getLatitude()+","+mBmobGeoPoint.getLongitude());
                    getMaintenancePointNearby();
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(2000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
