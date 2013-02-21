package com.apress;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor mAcceleromter;
	private TextView valuesView;
	private View mTop;
	private View mLeft;
	private View mRight;
	private View mBottom;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcceleromter = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        valuesView = (TextView) findViewById(R.id.values);
        mTop = findViewById(R.id.top);
        mLeft = findViewById(R.id.left);
        mRight = findViewById(R.id.right);
        mBottom = findViewById(R.id.bottom);
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	mSensorManager.registerListener(this, mAcceleromter, SensorManager.SENSOR_DELAY_UI);
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
		float[] values = event.values;
		float x = values[0] / 10;
		float y = values[1] / 10;
		int scaleFactor;
		if(x > 0) {
			scaleFactor = (int) Math.min(x * 255, 255);
			mRight.setBackgroundColor(Color.TRANSPARENT);
			mLeft.setBackgroundColor(Color.argb(scaleFactor, 255, 0, 0));
		} else {
			scaleFactor = (int) Math.min(Math.abs(x) * 255, 255);
			mRight.setBackgroundColor(Color.argb(scaleFactor, 255, 0, 0));
			mLeft.setBackgroundColor(Color.TRANSPARENT);
		}
		if(y > 0) {
			scaleFactor = (int) Math.min(y * 255, 255);
			mTop.setBackgroundColor(Color.TRANSPARENT);
			mBottom.setBackgroundColor(Color.argb(scaleFactor, 255, 0, 0));
		} else {
			scaleFactor = (int) Math.min(Math.abs(y) * 255, 255);
			mTop.setBackgroundColor(Color.argb(scaleFactor, 255, 0, 0));
			mBottom.setBackgroundColor(Color.TRANSPARENT);
		}
		valuesView.setText(String.format("X:%1$1.2f, Y:%2$1.2f, Z:%3$1.2f", values[0], values[1], values[2]));
	}
    
}
