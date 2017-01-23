package com.amap.android_gesture_zoom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
/**
 * Author liangchao 2017/01/23
 * Demo:用户利用自定义手势放大或者缩小地图
 */
public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private IGestureZoomDelegate mGestureZoomDelegate = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(savedInstanceState);
    }

    /**
     * 屏蔽map的原生手势
     * 初始化mGestureZoomDelegate
     */
    private void init(Bundle bundle) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(bundle);

        disableAllMapGesutres(mMapView);

        mGestureZoomDelegate = new GestureZoomDelegateImpl(this, mMapView.getMap(), IGestureZoomDelegate.RECOMM_MODE);

        mMapView.getMap().setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                mGestureZoomDelegate.onTouchEvent(motionEvent);
            }
        });
    }

    /**
     * 禁用sdk实现的手势
     *
     * @param mapView
     */
    private void disableAllMapGesutres(MapView mapView) {
        if (mapView == null) {
            return;
        }

        mapView.getMap().getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
