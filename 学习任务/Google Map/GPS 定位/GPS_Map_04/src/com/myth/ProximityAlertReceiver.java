package com.myth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class ProximityAlertReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 趋近关键字
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		// 从 intent 获取额外信息，判断是否进入设置区域
		boolean isEnter = intent.getBooleanExtra(key, false);
		if(isEnter) {
			Toast.makeText(context, "你已经进入海淀", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, "运行正常，但是没有进入设置区域", Toast.LENGTH_SHORT).show();
		}
	}

}
