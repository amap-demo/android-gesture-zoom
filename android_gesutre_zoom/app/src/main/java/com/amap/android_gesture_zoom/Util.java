package com.amap.android_gesture_zoom;

import android.graphics.Point;

/**
 * Created by liangchao_suxun on 17/1/23.
 */

public class Util {
    public static Point createPoint(double x, double y) {
        return new Point((int) x, (int) y);
    }
}
