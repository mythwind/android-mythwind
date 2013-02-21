package com.myth;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	// 声明定位管理器
	private LocationManager locationManager;
	private TextView textView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // 取得 LocationManager 实例
        locationManager = (LocationManager) getSystemService(
        		Context.LOCATION_SERVICE);
        
        textView = (TextView) findViewById(R.id.textView);
        
        // 获得位置信息
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        printMsg(location);
        
    }
    
    /**
     *  定位
     * @param location
     */
    private void printMsg(Location location) {
    	StringBuilder builder = new StringBuilder("可利用的providers：");
		if(null != location) {
			double latitude = location.getLatitude();  // 取得经度
			double longitude = location.getLongitude();   // 取得纬度
			builder.append("\n").append("(").append(latitude).append(", ").append(longitude).append(")");
			
			if(location.hasAccuracy()) {
				builder.append("\n经度：").append(location.getAccuracy());
			}
			if(location.hasAltitude()) {
				builder.append("\n高度：").append(location.getAltitude());
			}
			if(location.hasBearing()) {
				builder.append("\n方向：").append(location.getBearing());
			}
			if(location.hasSpeed()) {
				builder.append("\n速度：").append(location.getSpeed());
			}
			
		} else {
			builder.append("没有位置信息");
		}
		textView.setText(builder);
		
    }
    
}
