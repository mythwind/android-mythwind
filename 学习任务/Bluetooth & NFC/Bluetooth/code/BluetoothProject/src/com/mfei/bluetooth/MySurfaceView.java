package com.mfei.bluetooth;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	//未连接蓝牙设备
	public static final int NONE = 1;
	//正在连接蓝牙设备
	public static final int CONNTCTING = 2;
	//已连接蓝牙设备
	public static final int CONNTCTED = 3;
	
	//用于存储搜索到的蓝牙设备
	public static Vector<String> vc_str;
	//当前蓝牙连接状态
	public static int gameState = NONE;
	//连接蓝牙设备的下标索引
	public static int deviceIndex;
	public static int myArc_x = 50, myArc_y = 150, other_Arcx = 110, other_Arcy = 150;
	
	private Thread thread;
	private SurfaceHolder surfaceHolder;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	
	private OutputStream outputStream;
	
	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		vc_str = new Vector<String>();
		this.setKeepScreenOn(true);
		surfaceHolder = this.getHolder();
		surfaceHolder.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		this.setLongClickable(true);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
		Log.i("MySurfaceView", "MySurfaceView");
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		thread = new Thread(this, "himi_Thread_one");
		thread.start();
		Log.i("MySurfaceView", "surfaceCreated");
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.i("MySurfaceView", "surfaceChanged");
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		Log.i("MySurfaceView", "surfaceDestroyed");
	}
	
	public void myDraw() {
		try {
			canvas = surfaceHolder.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				paint.setColor(Color.RED);
				switch (gameState) {
				case NONE:
					if (vc_str != null) {
						for (int i = 0; i < vc_str.size(); i++) {
							paint.setTextSize(12);
							canvas.drawText(vc_str.elementAt(i), 3, 150 + i * 30, paint);
						}
					}
					break;
				case CONNTCTING:
					paint.setTextSize(20);
					canvas.drawText("正在连接设备:", 3, 150, paint);
					paint.setTextSize(12);
					canvas.drawText(vc_str.elementAt(deviceIndex), 3, 190, paint);
					break;
				case CONNTCTED:
					paint.setTextSize(20);
					paint.setTextSize(12);
					canvas.drawText("已成功连接:" + vc_str.elementAt(deviceIndex), 10, 110, paint);
					paint.setColor(Color.RED);
					canvas.drawCircle(myArc_x, myArc_y, 20, paint);
					paint.setColor(Color.BLUE);
					canvas.drawCircle(other_Arcx, other_Arcy, 20, paint);
					paint.setColor(Color.BLACK);
					canvas.drawText("我方圆形", myArc_x - 20, myArc_y - 25, paint);
					canvas.drawText("对方圆形", other_Arcx - 20, other_Arcy - 25, paint);
					break;
				}
			}
		} catch (Exception e) {
			Log.v("MySurfaceView", "draw is Error!");
			e.printStackTrace();
		} finally {
			if (canvas != null)
				surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gameState == CONNTCTED) {
			try {
				outputStream = MainActivity.bluetoothSocket.getOutputStream();
				byte bx[] = null;
				byte by[] = null;
				myArc_x = (int) event.getX();
				myArc_y = (int) event.getY();
				bx = new String("X=" + myArc_x).getBytes();
				by = new String("X=" + myArc_x).getBytes();
				if (bx != null && by != null) {
					outputStream.write(bx);
					outputStream.write(by);
				}
			} catch (IOException e) {
				Log.e("MySurfaceView", e.getMessage());
			}
		}
		return true;
	}
	
	private void logic() {
	}
	public void run() {
		while (flag) {
			logic();
			myDraw();
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				Log.e("MySurfaceView", ex.getMessage());
			}
		}
	}
	
}
