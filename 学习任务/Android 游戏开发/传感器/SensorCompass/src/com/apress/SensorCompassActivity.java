package com.apress;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorCompassActivity extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mAcceleromter;
	private Sensor mField;
	private TextView directionView;
	private TextView valuesView;
	
	private float[] mGravity;
	private float[] mMagnetic;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcceleromter = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        
        directionView = (TextView) findViewById(R.id.direction);
        valuesView = (TextView) findViewById(R.id.values);
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	mSensorManager.registerListener(this, mAcceleromter, SensorManager.SENSOR_DELAY_UI);
    	mSensorManager.registerListener(this, mField, SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    protected void onPause() {
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    }
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			mGravity = event.values.clone();
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			mMagnetic = event.values.clone();
			break;
		default:
			return;
		}
		if(null != mGravity && null != mMagnetic) {
			updateDirection();
		}
	}

	private void updateDirection() {
		float[] temp = new float[9];
		float[] R = new float[9];
		// 加载旋转矩阵到 R
		SensorManager.getRotationMatrix(temp, null, mGravity, mMagnetic);
		// 映射到相机视角
		SensorManager.remapCoordinateSystem(temp, 
				SensorManager.AXIS_X, SensorManager.AXIS_MINUS_Z, R);
		// 返回方向值
		float[] values = new float[3];
		SensorManager.getOrientation(R, values);
		// 转换为角度
		for (int i = 0; i < values.length; i++) {
			Double degrees = (values[i] * 180) / Math.PI;
			values[i] = degrees.floatValue();
		}
		// 显示罗盘方向
		directionView.setText(getDirectionFromDegrees(values[0]));
		valuesView.setText(String.format("Azimuth:%1$1.2f, Pitch:%2$1.2f, Roll:%3$1.2f", 
				values[0], values[1], values[2]));
	}

	private String getDirectionFromDegrees(float degress) {
		if(degress >= -22.5 && degress <= 22.5) return "N";
		if(degress >= 22.5 && degress <= 67.5) return "NE";
		if(degress >= 67.5 && degress <= 112.5) return "E";
		if(degress >= 112.5 && degress <= 157.5) return "SE";
		if(degress >= 157.5 || degress <= -157.5) return "S";
		if(degress >= -157.5 && degress <= -112.5) return "SW";
		if(degress >= -122.5 && degress <= -67.5) return "W";
		if(degress >= -67.5 && degress <= -22.5) return "NW";
		return null;
	}
    
}
