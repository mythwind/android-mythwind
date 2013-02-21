package com.myth;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static final String PROXIMITY_ALERT_ACTION = "com.myth.ProximityAlert";
	private Button button;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setProximity();
			}
		});
        
    }
    
    /**
     *  趋近
     */
    private void setProximity() {
    	// 取得 LocationManager 实例
    	LocationManager locationManager = (LocationManager) getSystemService(
        		Context.LOCATION_SERVICE);
    	double latitude = 37.4;  // 取得经度
		double longitude = 55.0;   // 取得纬度
		float radius = 200f;
		long expiration = -1;
		Intent intent = new Intent(PROXIMITY_ALERT_ACTION);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, -1, intent, 0);
		// 添加趋近警告
		locationManager.addProximityAlert(latitude, longitude, radius, expiration, pendingIntent);
    }
    
}
