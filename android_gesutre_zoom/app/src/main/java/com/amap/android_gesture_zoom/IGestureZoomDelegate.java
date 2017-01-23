package com.amap.android_gesture_zoom;

import android.view.MotionEvent;

/**
 * Created by liangchao_suxun on 17/1/23.
 *
 * 用于处理缩放手势的代理类
 * 利用onTouchEvent处理，才能完成手势缩放的功能
 *
 * SIMPLE_MODE在检测到手势后，并未对手势的缩放量进行判断
 * RECOMM_MODE在检测到手势后，会对手势所放量进行判断，和原生体验保持一致
 */

public interface IGestureZoomDelegate{

    public static int SIMPLE_MODE = 0;

    public static int RECOMM_MODE = 1;

    /**
     * 传入MotionEvent供recognizer处理
     * @param event
     */
    public void onTouchEvent(MotionEvent event);
}
