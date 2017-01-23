# android-gesture-zoom
利用自定义手势实现缩放

## 前述 ##
- [高德官网申请Key](http://lbs.amap.com/dev/#/).
- 阅读[参考手册](http://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html).
- 工程基于高德地图Android定位SDK实现

## 扫一扫安装##
![Screenshot]( https://raw.githubusercontent.com/amap-demo/android-service-location/master/LocationServiceDemo/apk/1477653836.png)  

## 使用方法##
###配置搭建AndroidSDK工程###
- [Android Studio工程搭建方法](http://lbs.amap.com/api/android-sdk/guide/creat-project/android-studio-creat-project/#add-jars).
- [通过maven库引入SDK方法](http://lbsbbs.amap.com/forum.php?mod=viewthread&tid=18786).

###使用场景###
该示例主要演示用于自定义手势完成map的zoom操作

###实现步骤###
step1. 通过ScaleGestureDetector获得缩放的scale和焦点<br />
step2. scaleDelta = Math.log(scale)。利用scale和焦点两个参数利用CameraUpdateFactory.zoomBy触发地图的缩放。<br />
step2中可以对scaleDelta进行过滤，以忽略误操作。

###实现原理###
1.初始化MapView并禁用原生手势
```java
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
```
2.将MapView的MotionEvent事件通过GestureZoomDelegateImpl传递给GestureDetector
```java
mMapView.getMap().setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                mGestureZoomDelegate.onTouchEvent(motionEvent);
            }
        });

```

3.AbstractScaleGestureListener用于listen获得ScaleGestureDetector的结果。提供了两个子类。<br />
SimpleScaleGestureListener：检测到手势就进行缩放操作。<br />
RecommGestureListener：检测到手势后，进行一定的判断，防止误操作。<br />
建议使用后者，更贴近于地图的原生体验
```java
public class SimpleScaleGestureListener extends AbstractScaleGestureListener {
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = detector.getScaleFactor();
        float scaleDelta = (float) Math.log(scale);

        mView.onZoom(scaleDelta, detector.getFocusX(), detector.getFocusY());

        return true;
    }
}


public class RecommGestureListener extends AbstractScaleGestureListener  {

    /**手势的scaleDelta需要大于MINIMAL_TO_CHANGE，才可以开始后续的检测*/
    private static double MINIMAL_TO_CHANGE = 0.06;

    /**检测开始后，手势的scaleDelta需要大于MIMIMAL_WHNE_CHANGE。避免失误操作的问题*/
    private static double MINIMAL_WHNE_CHANGE = 0.01;

    /** 用于记录是否存在手势的scaleDelta大于MINIMAL_TO_CHANGE*/
    private boolean flag = false;

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

    //onScaleBegin中，将flag重置为false
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        flag = false;
        return true;
    }
}


```



4.利用OnScaleGestureListener返回的缩放信息触发地图的缩放操作
```java
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
	    
	    // 利用cameraUpdate触发map的缩放操作	    
            CameraUpdate cameraUpdate = CameraUpdateFactory.zoomBy(scaleDelta, new Point(Util.createPoint(x, y)));
            mMap.moveCamera(cameraUpdate);

        }
    }
```
