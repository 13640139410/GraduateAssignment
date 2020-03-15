package com.graduateassignment.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.VisibleRegion;
import com.graduateassignment.R;
import com.graduateassignment.Util.Constants;
import com.graduateassignment.Util.ToastUtil;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 地图事件监听活动，监听移动地图或点击地图动作
 */
public class TestGdmapEventsActivity extends Activity implements AMap.OnMapClickListener,
        AMap.OnMapLongClickListener, AMap.OnCameraChangeListener {

    //private BmobGeoPoint
    private AMap aMap;
    private MapView mapView;
    private TextView mTapTextView;
    private TextView mCameraTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gdmap_events);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            setUpMap();
        }
        mTapTextView = (TextView) findViewById(R.id.tap_text);
        mCameraTextView = (TextView) findViewById(R.id.camera_text);
    }

    /**
     * amap添加一些事件监听器
     */
    private void setUpMap() {
        aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
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
     * 对单击地图事件回调
     */
    @Override
    public void onMapClick(LatLng point) {
        mTapTextView.setText("tapped, point=" + point);
    }

    /**
     * 对长按地图事件回调
     */
    @Override
    public void onMapLongClick(LatLng point) {
        mTapTextView.setText("long pressed, point=" + point);
    }

    /**
     * 对正在移动地图事件回调，摄像头的位置是界面的中心点的经纬度
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        mCameraTextView.setText("onCameraChange:" + cameraPosition.toString());
    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mCameraTextView.setText("onCameraChangeFinish:"
                + cameraPosition.toString());
        VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion(); // 获取可视区域、
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;// 获取可视区域的Bounds
        boolean isContain = latLngBounds.contains(Constants.SHANGHAI);// 判断上海经纬度是否包括在当前地图可见区域
        if (isContain) {
            ToastUtil.show(TestGdmapEventsActivity.this, "上海市在地图当前可见区域内");
        } else {
            ToastUtil.show(TestGdmapEventsActivity.this, "上海市超出地图当前可见区域");
        }
    }
}
