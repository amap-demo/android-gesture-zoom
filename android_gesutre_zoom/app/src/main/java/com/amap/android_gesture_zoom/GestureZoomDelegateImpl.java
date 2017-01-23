package com.amap.android_gesture_zoom;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;

/**
 * Created by liangchao_suxun on 17/1/23.
 * 利用ScaleGestureDetector进行手势检测。检测到手势后，对ZoomView进行缩放
 * 可以选择SIMPLE_MODE和RECOMM_MODE（说明见@{link com.amap.android_gesture_zoom.IGestureZoomDelegate}*）,建议选择后者, 减少误操作，和原生体验保持一致
 * 利用 {link com.amap.api.maps.CameraUpdateFactory zoomBy} 对map进行zoom操作
 */

public class GestureZoomDelegateImpl implements IGestureZoomDelegate {

    private AMap mMap;

    /** 可选模式为SIMPLE_MODE和RECOMM_MODE ， 说明见@{link IGestureZoomDelegate}*/
    private int mMode;


    private MapZoomableView mZoomView;

    /**
     * 利用SimpleGesutreRecognizer 进行手势的识别
     */
    private ScaleGestureDetector mDelegate;

    /**
     * mDelegate的listener
     */
    private ScaleGestureDetector.OnScaleGestureListener mListener;

    public GestureZoomDelegateImpl(Context context, AMap map, int mode) {
        if (context == null || map == null) {
            throw new RuntimeException("Context and Amap cannot be null when you create a " + this.getClass().getSimpleName());
        }
        this.mMode = mode;
        this.mMap = map;

        init(context);
    }

    private void init(Context context) {
        mZoomView = new MapZoomableView(mMap);
        mListener = getListenerByMode(mMode);
        mDelegate = new ScaleGestureDetector(context, mListener);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        mDelegate.onTouchEvent(event);
    }

    private ScaleGestureDetector.OnScaleGestureListener getListenerByMode(int mode) {
        if (mode == 0) {
            return new SimpleScaleGestureListener(mZoomView);
        }

        return new RecommGestureListener(mZoomView);
    }

    private class MapZoomableView implements IZoomableView {
        private AMap map;

        public MapZoomableView(AMap map) {
            this.map = map;
        }

        @Override
        public void onZoom(float scaleDelta, float x, float y) {
            if (map == null) {
                return;
            }

            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(scaleDelta, new Point(Util.createPoint(x, y)));
            mMap.moveCamera(cameraUpdate);

        }
    }
}
