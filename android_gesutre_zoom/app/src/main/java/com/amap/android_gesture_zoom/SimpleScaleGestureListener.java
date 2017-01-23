package com.amap.android_gesture_zoom;

import android.view.ScaleGestureDetector;

/**
 * Created by liangchao_suxun on 17/1/23.
 */

public class SimpleScaleGestureListener extends AbstractScaleGestureListener {


    public SimpleScaleGestureListener(IZoomableView view) {
        super(view);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = detector.getScaleFactor();
        float scaleDelta = (float) Math.log(scale);

        mView.onZoom(scaleDelta, detector.getFocusX(), detector.getFocusY());

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }
}
