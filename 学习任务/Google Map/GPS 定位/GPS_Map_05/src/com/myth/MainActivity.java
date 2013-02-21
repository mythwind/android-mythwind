package com.myth;

import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	// 声明定位管理器
	private LocationManager locationManager;
	
	private TextView textView;
	private Button button01;
	private Button button02;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // 取得 LocationManager 实例
        locationManager = (LocationManager) getSystemService(
        		Context.LOCATION_SERVICE);
        
        textView = (TextView) findViewById(R.id.textView);
        button01 = (Button) findViewById(R.id.button01);
        button02 = (Button) findViewById(R.id.button02);
        
        button01.setOnClickListener(listener);
        button02.setOnClickListener(listener);
       
    }
    
    private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.button01:
				forword();
				break;
			case R.id.button02:
				reverse();
				break;
			}
		}
	};
    
    /**
     *   正向编码
     */
    private void forword() {
    	// 实例化 Geocoder
    	Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    	String address = "北京天安门";
    	List<Address> locations = null;
    	try {
    		JSONObject obj = MapUtil.getLocationInfo(address);
    		GeoPoint point = MapUtil.getGeoPoint(obj);
    		textView.setText(point.toString());
    	} catch (Exception e) {
			Log.e("Geocoder", e.getMessage());
		}
    }
    
    /**
     *   反向编码
     */
    private void reverse() {
    	double longitude = 116.46;
		double latitude = 39.92;
    	// 实例化 Geocoder
    	Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    	List<Address> locations = null;
    	try {
    		// 获取位置类别
    		locations = geocoder.getFromLocation(latitude, longitude, 10);
    		StringBuilder sb = new StringBuilder();
    		if(locations.size() > 0) {
    			Address addr = locations.get(0);
    			for (int i = 0; i < addr.getMaxAddressLineIndex(); i++) {
					sb.append(addr.getAddressLine(i)).append("\n");  // address
					sb.append(addr.getLocality()).append("\n");  // locale
					sb.append(addr.getPostalCode()).append("\n");  // post code
					sb.append(addr.getCountryName());  //country name
				}
    			textView.setText(sb.toString());
    		}
    	} catch (Exception e) {
			Log.e("Geocoder", e.getMessage());
		}
    }
    
}
