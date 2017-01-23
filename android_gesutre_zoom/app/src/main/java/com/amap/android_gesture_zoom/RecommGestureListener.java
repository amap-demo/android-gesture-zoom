package com.amap.android_gesture_zoom;

import android.view.ScaleGestureDetector;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;

/**
 * Created by liangchao_suxun on 17/1/23.
 */

public class RecommGestureListener extends AbstractScaleGestureListener  {

    /**手势的scaleDelta需要大于MINIMAL_TO_CHANGE，才可以开始后续的检测*/
    private static double MINIMAL_TO_CHANGE = 0.06;

    /**检测开始后，手势的scaleDelta需要大于MIMIMAL_WHNE_CHANGE。避免失误操作的问题*/
    private static double MINIMAL_WHNE_CHANGE = 0.01;

    /** 用于记录是否存在手势的scaleDelta大于MINIMAL_TO_CHANGE*/
    private boolean flag = false;

    public RecommGestureListener(IZoomableView view) {
        super(view);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        float scaleDelta = (float) Math.log(scaleFactor);

        if (Math.abs(scaleDelta) > MINIMAL_TO_CHANGE) {
            flag = true;
        }

        //如果手势检测开始后，没有大于MINIMAL_TO_CHANGE的,则忽略
        if (!flag) {
            return true;
        }

        //如果手势幅度过小，则忽略
        if (Math.abs(scaleDelta) <= MINIMAL_WHNE_CHANGE) {
            return true;
        }

        mView.onZoom(scaleDelta, detector.getFocusX(), detector.getFocusY());

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        flag = false;
        return true;
    }
}
