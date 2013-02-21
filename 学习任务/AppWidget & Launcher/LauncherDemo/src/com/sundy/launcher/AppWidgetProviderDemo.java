package com.sundy.launcher;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidgetProviderDemo extends AppWidgetProvider {

	private static final String TAG = "AppWidgetSample";
	
	/**
	 *  删除一个 appwidget 得到的消息
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.i(TAG, "----- onDeleted -----");
	}

	/**
	 *   最后一个 appwidget 被删除
	 */
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.i(TAG, "----- onDisabled -----");
	}

	/**
	 *  首次添加 appwidget 时候接收的消息
	 */
	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.i(TAG, "----- onEnabled -----");
	}
	
	/**
	 * recevier 默认的消息接收重载
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.i(TAG, "----- onReceive -----");
	}

	/**
	 * 到了时间间隔的时候接收到的消息，appwidgetservice发过来的消息
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Log.i(TAG, "----- onUpdate -----");
		
		// 更新远程界面(launcher里我的所有的appwidget)
		for (int i = 0; i < appWidgetIds.length; i++) {
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_appwidget);
			views.setTextViewText(R.id.widgetbutton, "sundyClick");
			// 加入事件
			views.setOnClickPendingIntent(R.id.widgetbutton, 
					PendingIntent.getActivity(context, 0, new Intent(context, LauncherDemoActivity.class), 0));
			appWidgetManager.updateAppWidget(appWidgetIds[i], views);
		}
	}
	
}
