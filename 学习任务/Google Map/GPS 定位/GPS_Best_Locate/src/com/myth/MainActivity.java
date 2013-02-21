package com.myth;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	// 声明定位管理器
	private LocationManager locationManager;
	private Location location;
	private TextView textView;
	private String bestProvider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 取得 LocationManager 实例
        locationManager = (LocationManager) getSystemService(
        		Context.LOCATION_SERVICE);
        
        textView = (TextView) findViewById(R.id.textView);
        locate();
    }
    
    private void locate() {
    	StringBuilder builder = new StringBuilder("bestProvider：");
    	
    	//为获取地理位置信息时设置查询条件
        bestProvider = locationManager.getBestProvider(getCriteria(), true);
        //如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        location = locationManager.getLastKnownLocation(bestProvider);
        
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
System.out.println("---bestProvider---" + bestProvider);
		//  请求位置更新
		locationManager.requestLocationUpdates(bestProvider, 0, 1000, listener);
		builder.append("\n").append(bestProvider).append(":");
		
		if(null != location) {
			double latitude = location.getLatitude();  // 取得经度
			double longitude = location.getLongitude();   // 取得纬度
			builder.append("(").append(latitude).append(", ").append(longitude).append(")");
		} else {
			builder.append("没有位置信息");
		}
		textView.setText(builder);
    }
    
    /**
     * Description: 设置获取卫星参数， 获取合适的地理位置服务
     * Create on 2012-11-23 下午4:03:04
     * Method name: getCriteria
     * @return
     */
    private Criteria getCriteria(){
        Criteria criteria=new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细 
        criteria.setAccuracy(Criteria.ACCURACY_FINE);    
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费  
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求  
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }
    
}
