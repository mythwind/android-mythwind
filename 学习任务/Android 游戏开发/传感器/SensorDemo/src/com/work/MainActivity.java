package com.work;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	
	private boolean mSensorRegister;
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private TextView textView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSensorRegister = false;
        
        textView = (TextView) findViewById(R.id.textView01);
        // 初始化屏幕显示字符串
        textView.setText("摇摆手机，观察传感器数值变化(m/s^2)\nx=0, y=0, z=0");
		// 初始化加速度传感器  get SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // 加速传感器
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**   传感器精确度发生变化时调用  */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	/**   传感器数据发生变化时调用  */
	@Override
	public void onSensorChanged(SensorEvent event) {
		// 立体坐标系的加速度
		int x = (int) event.values[SensorManager.DATA_X];
		int y = (int) event.values[SensorManager.DATA_Y];
		int z = (int) event.values[SensorManager.DATA_Z];
		// x1 与 x 效果是一样的
		int x1 = (int) event.values[0];
		int y1 = (int) event.values[1];
		int z1 = (int) event.values[2];
		// 修改屏幕显示字符串
		textView.setText("摇摆手机，观察传感器数值变化(m/s^2)\nx="
				+ x + ", y=" + y + ", z=" + z + "\n"
				+ "x1=" + x1 + ", y1=" + y1 + ", z1=" + z1);
	}

	/**   在 onPause 中取消注册  */
	@Override
	protected void onPause() {
		if (mSensorRegister) {
			mSensorManager.unregisterListener(this);
		}
		super.onPause();
	}

	/**   一般在 onResume 中注册监听  */
	@Override
	protected void onResume() {
		if(!mSensorRegister) {
			// 启动传感器，第三个参数是灵敏度
			mSensorManager.registerListener(this, mSensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
		super.onResume();
	}
	
}
