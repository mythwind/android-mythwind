package com.google.map;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyMapActivity extends MapActivity {
	private static final String TAG = "GoogleMapDemo.MyMapActivity";
	private MapView mapView;
	private MapController mapController;
	private GeoPoint point;
	private LocationItemsOverlay locationItemsOverlay;
	private LocationManager locationManager;
	private MyLocationOverlay my;
	private ProgressDialog progDialog;
	
	private String editString;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapView);
        // 设置为可缩放
        mapView.setBuiltInZoomControls(true);
        /* MapController 能实现对地图的控制，包括：
         * 设定地图中心点 setCenter(GeoPoint); 以动画方式移动地图的中心点 animateTo(GeoPoint);
         * 设置地图的缩放级别 setZoom(int zoomLevel); 地图的移动和缩放
         */
        mapController = mapView.getController();
        point = new GeoPoint((int)(30.422006 * 1000000), (int)(120.084095 * 1000000));
        mapController.setCenter(point);  // 设置地图的中心点
        /*  mapController.animateTo(point); */
        // 设置地图的缩放级别 14, 其范围为1-21, 1是全球地图, 21是街道地图
        mapController.setZoom(14);
        
        
        myLocation();
        
//        locate(); // 定位
    }
    
    /**   创建菜单，切换模式    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
		//设置menu界面为res/menu/google_map.xml
		inflater.inflate(R.menu.google_map, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.traffic01:
			mapView.setTraffic(true);
			mapView.setSatellite(false);
			break;
		case R.id.traffic02:
			mapView.setTraffic(false);
			mapView.setSatellite(false);
			break;
		case R.id.staellite01:
			mapView.setSatellite(true);
			break;
		case R.id.staellite02:
			mapView.setSatellite(true);
			mapView.setTraffic(true);
			break;
		}
    	return super.onOptionsItemSelected(item);
    }

    /**
     *  告知 Google 服务器我们应用中是否使用了显示"道路信息"有关服务
     *  如果使用该服务就返回 true，否则返回 false
     */
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	/**
	 *  添加图层标识
	 * @author Neu
	 */
	class LocationItemsOverlay extends ItemizedOverlay<OverlayItem> {
		// 保存 OverlayItem 列表
		private List<OverlayItem> items = null;
		// OverlayItem 的图标
		private Drawable marker = null;
		public LocationItemsOverlay(Drawable marker, List<OverlayItem> items) {
			super(marker);
			this.marker = marker;
			this.items = items;
			populate();   //  组装数据
		}
		@Override
		protected OverlayItem createItem(int i) {
			return items.get(i);
		}
		@Override
		public void draw(Canvas canvas, MapView mapview, boolean flag) {
			super.draw(canvas, mapview, flag);
			boundCenterBottom(marker);  // 调整一个 Drawable 的边界
		}
		/*  单击坐标时触发的事件   */
		@Override
		protected boolean onTap(int i) {
			Toast.makeText(MyMapActivity.this, 
					items.get(i).getTitle() + "\n" +  items.get(i).getSnippet(), 
					Toast.LENGTH_SHORT).show();
			return true;
		}
		@Override
		public int size() {
			return items.size();
		}
	}
	
	/**
	 *  定位查询功能
	 */
	private void locate() {
		LayoutInflater factory = LayoutInflater.from(MyMapActivity.this);
		View locationView = factory.inflate(R.layout.find_dialog, null);
		// 取得输入地名的控件
		final EditText editText = (EditText) locationView.findViewById(R.id.dialog_find);
		//  创建对话框
		new AlertDialog.Builder(this).setTitle(R.string.dialog_find).setView(locationView)
			.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					editString = editText.getText().toString();
					progDialog = ProgressDialog.show(MyMapActivity.this, "处理中……", "定位" + editString, true, false);
					new Thread() {
						@Override
						public void run() {
							try {
								Geocoder geocoder = new Geocoder(MyMapActivity.this);
								/**  android 中的查找与定位是通过地理编码 Geocoder 的 getFromLocationName 来实现的 */
								//  通过地名来查询地点
								// getFromLocationName 如果使用Android Google Map Level8 以上，该方法抛出 Service not available 异常
								List<Address> addresses = geocoder.getFromLocationName(editString, 1);
								if(addresses.size() > 0) {
									List<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
									double latitude = addresses.get(0).getLatitude();  //  经度
									double longitude = addresses.get(0).getLongitude();  //  纬度
									// 设定中心点
									GeoPoint centerPoint = new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6));
									mapController.setCenter(centerPoint);
		Log.i(TAG, "latitude:" + latitude + ", \tlongitude" + longitude);
									int maxAddressLineIndex = addresses.get(0).getMaxAddressLineIndex();
									
									String address = "地址：" ;
									for (int i = 0; i <= maxAddressLineIndex; i++) {
										if(null == addresses.get(0)) {
											continue;
										}
										// 通过索引返回地址描述
										address += addresses.get(0).getAddressLine(i) + ",";
									}
									if(address.endsWith(",")) {
										address = address.substring(0, address.length() - 1);
									}
									String title = "";
									if(null != addresses.get(0).getFeatureName()) {
										title = addresses.get(0).getFeatureName();
									}
									
									overlayItems.add(new OverlayItem(centerPoint, title, address));
									Drawable marker = getResources().getDrawable(R.drawable.iconmarker);
									locationItemsOverlay = new LocationItemsOverlay(marker, overlayItems);
									handler.sendEmptyMessage(0);
								} else {
									handler.sendEmptyMessage(1);
								}
							} catch (Exception e) {
								handler.sendEmptyMessage(1);
							}
						};
					}.start();
				}
			}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
	}
	
	/**
	 *  查询周围的功能
	 */
	private void find() {
		LayoutInflater factory = LayoutInflater.from(MyMapActivity.this);
		View locationView = factory.inflate(R.layout.find_dialog, null);
		// 取得输入地名的控件
		final EditText editText = (EditText) locationView.findViewById(R.id.dialog_find);
		//  创建对话框
		new AlertDialog.Builder(this).setTitle(R.string.dialog_find).setView(locationView)
			.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					editString = editText.getText().toString();
					progDialog = ProgressDialog.show(MyMapActivity.this, "处理中……", "定位" + editString, true, false);
					new Thread() {
						@Override
						public void run() {
							try {
								Projection projection = mapView.getProjection();
								// 获取屏幕的大小
								DisplayMetrics dm = new DisplayMetrics();
								getWindowManager().getDefaultDisplay().getMetrics(dm);
								// 屏幕左上角坐标
								Point myPoint01 = new Point(0, 0);
								// 屏幕右下角坐标
								Point myPoint02 = new Point(dm.widthPixels, dm.heightPixels);
								// 屏幕左下角坐标
								Point myPoint03 = new Point(myPoint01.x, myPoint02.y);
								// 屏幕右上角坐标
								Point myPoint04 = new Point(myPoint02.x, myPoint01.y);
								
								/*  projection.fromPixels 将屏幕坐标转化为地理坐标  */
								GeoPoint geoPoint01 = projection.fromPixels(myPoint03.x, myPoint03.y);
								Log.i(TAG, "geoPoint01: " + geoPoint01);
								GeoPoint geoPoint02 = projection.fromPixels(myPoint04.x, myPoint04.y);
								Log.i(TAG, "geoPoint02: " + geoPoint02);
								
								Geocoder geocoder = new Geocoder(MyMapActivity.this);
								/**  android 中的查找与定位是通过地理编码 Geocoder 的 getFromLocationName 来实现的 */
								//  通过地名来查询地点
								List<Address> addresses = geocoder.getFromLocationName(editString, 5, 
										(double) geoPoint01.getLatitudeE6() / 1E6, (double) geoPoint01.getLongitudeE6() / 1E6,
										(double) geoPoint02.getLatitudeE6() / 1E6, (double) geoPoint01.getLongitudeE6() / 1E6);
								if(addresses.size() > 0) {
									List<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
									for (int i = 0; i < addresses.size(); i++) {
										int latitude = (int) (addresses.get(i).getLatitude() * 1E6);  //  经度
										int longitude = (int) (addresses.get(0).getLongitude() * 1E6);  //  纬度
										Log.i(TAG, "latitude:" + latitude + ", \tlongitude" + longitude);
										int maxAddressLineIndex = addresses.get(0).getMaxAddressLineIndex();
										String address = "地址：" ;
										for (int j = 0; j <= maxAddressLineIndex; j++) {
											// 通过索引返回地址描述
											address += addresses.get(i).getAddressLine(j) + ",";
										}
										if(address.endsWith(",")) {
											address = address.substring(0, address.length() - 1);
										}
										// 设定中心点
										GeoPoint centerPoint = new GeoPoint(latitude, longitude);
										overlayItems.add(new OverlayItem(centerPoint, 
												addresses.get(i).getFeatureName(), address));
									}
									Drawable marker = getResources().getDrawable(R.drawable.iconmarker);
									locationItemsOverlay = new LocationItemsOverlay(marker, overlayItems);
									handler.sendEmptyMessage(0);
								} else {
									handler.sendEmptyMessage(1);
								}
							} catch (Exception e) {
								handler.sendEmptyMessage(1);
							}
						}
					}.start();
				}
			}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mapView.getOverlays().clear();
				mapView.getOverlays().add(locationItemsOverlay);
				progDialog.dismiss();
				break;
			case 1:
				Toast.makeText(MyMapActivity.this, "暂时无法获取" + editString + "的信息", 
						Toast.LENGTH_SHORT).show();
				progDialog.dismiss();
				break;
			}
		}
	};
	
	// 定位到我的位置
	private void myLocation() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
				1000, 0, listener);
		my = new MyLocationOverlay();
		mapView.getOverlays().add(my);
	}
	private LocationListener listener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		@Override
		public void onProviderEnabled(String provider) {
		}
		@Override
		public void onProviderDisabled(String provider) {
		}
		@Override
		public void onLocationChanged(Location location) {
			double latitude = location.getLatitude();  //  经度
			double longitude = location.getLongitude();  //  纬度
			GeoPoint myPoint = new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6));
			mapController.animateTo(myPoint);
		}
	};
	
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
			mapView.getProjection().toPixels(point, myScreenCoords);
			paint.setStrokeWidth(1);
			paint.setARGB(255, 255, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home);
			canvas.drawBitmap(bitmap, myScreenCoords.x, myScreenCoords.y, paint);
			canvas.drawText("天府广场", myScreenCoords.x, myScreenCoords.y, paint);
			return true;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(null != locationManager) {
			// 移除GPS状态变化监听事件
			locationManager.removeUpdates(listener);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocation();  // 我的当前位置
	}
	
}
