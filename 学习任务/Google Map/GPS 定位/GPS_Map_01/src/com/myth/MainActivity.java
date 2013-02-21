package com.myth;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	// 声明定位管理器
	private LocationManager locationManager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // 取得 LocationManager 实例
        locationManager = (LocationManager) getSystemService(
        		Context.LOCATION_SERVICE);
        locate();
    }
    
    private void locate() {
    	TextView textView = (TextView) findViewById(R.id.textView);
    	
    	StringBuilder builder = new StringBuilder("可利用的providers：");
    	List<String> providers = locationManager.getProviders(true);
    	// 位置服务监听器
    	LocationListener listener = new LocationListener() {
    		//  状态改变时调用
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
			//  provider 生效时调用
			@Override
			public void onProviderEnabled(String provider) {
			}
			//  provider 无效时调用
			@Override
			public void onProviderDisabled(String provider) {
			}
			//  位置改变时调用
			@Override
			public void onLocationChanged(Location location) {
			}
		};
		/**  根据 provider 获取位置信息(经纬度)  */
		for (String provider : providers) {
			//  请求位置更新
			locationManager.requestLocationUpdates(provider, 0, 1000, listener);
			builder.append("\n").append(provider).append(":");
			Location location = locationManager.getLastKnownLocation(provider);
			if(null != location) {
				double latitude = location.getLatitude();  // 取得经度
				double longitude = location.getLongitude();   // 取得纬度
				builder.append("(").append(latitude).append(", ").append(longitude).append(")");
			} else {
				builder.append("没有位置信息");
			}
		}
		textView.setText(builder);
		
    }
    
}
