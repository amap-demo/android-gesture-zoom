package com.amap.android_gesture_zoom;

import android.view.ScaleGestureDetector;

import com.amap.api.maps.AMap;

/**
 * Created by liangchao_suxun on 17/1/23.
 * 手势检测的逻辑是：
 * 1. 获得手势的scale
 * 2. scaleDelta = Math.log(scale)
 * 3. 调用map.zoomBy(scaleDelta, 焦点.x , 焦点.y)，进行zoom操作
 */

public abstract class AbstractScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener  {

    protected IZoomableView mView;

    public AbstractScaleGestureListener(IZoomableView view) {
        this.mView = view;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        ; //ignore
    }
}
