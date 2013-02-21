package com.mfei.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * 蓝牙 需要在AndroidMainfest.xml中添加权限
 */
public class MainActivity extends Activity {
	// 按钮组件
	public static Button btConnect, btOpen, btIsVisible, btSearch;
	// 蓝牙适配器
	public static BluetoothAdapter bluetoothAdapter;
	// UUID协议
	public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	// 蓝牙连接
	public static BluetoothSocket bluetoothSocket;
	// 单利本类
	public static MainActivity ma;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这里没有设置全屏，为了便于观察当前蓝牙是否打开的状态变化
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		//实例按钮
		btOpen = (Button) findViewById(R.id.Btn_OpenBt);
		btIsVisible = (Button) findViewById(R.id.Btn_BtIsVisible);
		btSearch = (Button) findViewById(R.id.Btn_SearchDrives);
		btConnect = (Button) findViewById(R.id.Btn_ConnectDrives);
		//为按钮绑定监听器
		btOpen.setOnClickListener(buttonListener);
		btIsVisible.setOnClickListener(buttonListener);
		btSearch.setOnClickListener(buttonListener);
		btConnect.setOnClickListener(buttonListener);
		//实例蓝牙适配器
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter == null) {
			Toast.makeText(MainActivity.this, "设备不支持蓝牙", Toast.LENGTH_LONG).show();
			finish();
		} else {
			if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
				btOpen.setText("打开蓝牙");
			} else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
				btOpen.setText("关闭蓝牙");
			}
			// 注册Receiver来获取蓝牙设备相关的结果
			IntentFilter intent = new IntentFilter();
			intent.addAction(BluetoothDevice.ACTION_FOUND);// 远程设备发现动作。
			intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);//远程设备的键态的变化动作。
			intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);//蓝牙扫描本地适配器模改变动作。
			intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//状态改变动作
			registerReceiver(searchDevices, intent);//注册接收
		}
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (MySurfaceView.gameState != MySurfaceView.CONNTCTED) {
				if (v == btOpen) {//蓝牙开关
					if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
						bluetoothAdapter.enable();
						btOpen.setText("关闭蓝牙");
					} else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
						bluetoothAdapter.disable();
						btOpen.setText("打开蓝牙");
					}
				} else if (v == btIsVisible) {//蓝牙是否可见
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 110);
					//第二个参数是本机蓝牙被发现的时间，系统默认范围[1-300],超过范围默认300，小于范围默认120
					startActivity(intent);
				} else if (v == btSearch) {//搜索蓝牙
					if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {// 如果蓝牙还没打开
						Toast.makeText(MainActivity.this, "请先打开蓝牙", 1000).show();
						return;
					}
					setTitle("本机蓝牙地址：" + bluetoothAdapter.getAddress());
					MySurfaceView.vc_str.removeAllElements();
					bluetoothAdapter.startDiscovery();
				} else if (v == btConnect) {
					if (MySurfaceView.vc_str.size() == 0) {
						Toast.makeText(MainActivity.this, "当前没有设备", Toast.LENGTH_LONG).show();
					} else {
						Intent intent = new Intent();//别忘记声明咱们要打开的这个Activity
						intent.setClass(MainActivity.this, ChoiceDrivesList.class);
						MainActivity.this.startActivity(intent);
					}
				}
			} else {
				Toast.makeText(MainActivity.this, "这里只是一个Demo示例，很多情况没有进行处理，为了等出现误操作造成异常，请重新运行项目！", Toast.LENGTH_LONG).show();
				MainActivity.this.finish();
			}
		}
	};
	
	//监听动作
	private BroadcastReceiver searchDevices = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			//搜索设备时，取得设备的MAC地址
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				String str = "设备：" + device.getName() + "*" + device.getAddress();
				if (MySurfaceView.vc_str != null) {
					if (MySurfaceView.vc_str.size() != 0) {
						for (int j = 0; j < MySurfaceView.vc_str.size(); j++) {
							// 防止重复添加
							if (MySurfaceView.vc_str.elementAt(j).equals(str) == false) {
								// 容器添加发现的设备名称和mac地址
								MySurfaceView.vc_str.addElement(str);
							}
						}
					} else {
						MySurfaceView.vc_str.addElement(str);
					}
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		if(bluetoothAdapter != null) {
			this.unregisterReceiver(searchDevices);
		}
		super.onDestroy();
		System.exit(0);
	}

}
