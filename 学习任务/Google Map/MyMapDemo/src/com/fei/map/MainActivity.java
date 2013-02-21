package com.fei.map;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MainActivity extends MapActivity {
	
//	private static final String TAG = "GoogleMapDemo.MyMapActivity";
	private MapView mMapView;
	private MapController mMapController;
	private GeoPoint mGeoPoint;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mMapView = (MapView) findViewById(R.id.mapView);
        // 设置为交通模式
        mMapView.setTraffic(true);
        // 设置为卫星模式
       //  mapView.setSatellite(true);
        // 设置为街景模式
        // mapView.setStreetView(false);
        /* MapController 能实现对地图的控制，包括：
         * 设定地图中心点 setCenter(GeoPoint); 以动画方式移动地图的中心点 animateTo(GeoPoint);
         * 设置地图的缩放级别 setZoom(int zoomLevel); 地图的移动和缩放
         */
        mMapController = mMapView.getController();
        mMapView.setEnabled(true);
        // 设置为可缩放
        mMapView.setBuiltInZoomControls(true);
        
        mGeoPoint = new GeoPoint((int)(30.422006 * 1000000), (int)(120.084095 * 1000000));
        //mMapController.setCenter(point);  // 设置地图的中心点
        mMapController.animateTo(mGeoPoint); // 定位到该点
        // 设置地图的缩放级别 14, 其范围为1-21, 1是全球地图, 21是街道地图
        mMapController.setZoom(14);
        
        MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
        List<Overlay> list = mMapView.getOverlays();
        list.add(myLocationOverlay);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
    
	/**
	 *  画图层的类
	 * @author Neu
	 */
	class MyLocationOverlay extends Overlay {
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
				long when) {
			super.draw(canvas, mapView, shadow);
			Paint paint = new Paint();
			Point myScreenCoords = new Point();
			// 将经纬度转换成实际屏幕坐标
			mapView.getProjection().toPixels(mGeoPoint, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home);
			canvas.drawBitmap(bitmap, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("天府广场", myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}
	
}
