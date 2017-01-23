package com.amap.android_gesture_zoom;

/**
 * Created by liangchao_suxun on 17/1/23.
 * 可进行zoom的接口，缩放的量由scaleDelta制定。scaleDelta=Math.log(手势.scale)
 */

public interface IZoomableView {

    /**
     * 按照scale缩放，并改变中心点。对应于@{link CameraUpdateFactory}的zoomBy方法
     * 我们建议在缩放前后，焦点保持不变
     * @param scaleDelta scaleDelta=Math.log(手势.scale)
     * @param x 焦点的x坐标
     * @param y 焦点的y坐标
     */
    public void onZoom(float scaleDelta, float x, float y);

}
