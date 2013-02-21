package com.apress;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG  = "BlueTooth.MainActivity";
	private static final String SEARCH_NAME = "bluetooth.recipe";
	private static final UUID MY_UUID = UUID.fromString(
			"071681A7-D613-42CB-2F25-6034AF8369FC");
	
	private static final int REQUEST_ENABLE = 1;
	private static final int REQUEST_DISCAVERABLE = 2;
	
	// 蓝牙适配器设备
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothSocket bluetoothSocket;
	
	// 远程蓝牙设备连接
	private BluetoothServerSocket bluetoothServer;
	
	private EditText emailField;
	private Button listenButton;
	private Button scanButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);
		
		// 获取蓝牙的 Adapter
		configureBluetooth();
		findViews();
	}
	
	private void findViews() {
		emailField = (EditText) findViewById(R.id.email);
		listenButton = (Button) findViewById(R.id.listen);
		scanButton = (Button) findViewById(R.id.scan);
		
		listenButton.setOnClickListener(listener);
		scanButton.setOnClickListener(listener);
	}

	private void configureBluetooth() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter == null) {
			Toast.makeText(MainActivity.this, "Bluetooth is not suported", 
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		if(!bluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE);
		}
	}
	
	private Button.OnClickListener listener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.listen:
				// 确保设备是可以被发现的
				if(bluetoothAdapter.getScanMode() != 
					BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
					Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
					discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
					startActivityForResult(discoverableIntent, REQUEST_DISCAVERABLE);
					return;
				}
				startListening();
			case R.id.scan:
				bluetoothAdapter.startDiscovery();
				setProgressBarIndeterminateVisibility(true);
				break;
			}
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if(null != bluetoothSocket) {
				bluetoothSocket.close();
			}
			/***--------------- 关闭蓝牙 ------------**/
			if(bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.disable();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(BluetoothDevice.ACTION_FOUND)) {
				// 将已找到的设备添加到列表
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(TextUtils.equals(device.getName(), SEARCH_NAME)) {
					// 匹配到设备，连接
					bluetoothAdapter.cancelDiscovery();
					try {
						bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
						bluetoothSocket.connect();
						ConnectTask connectTask = new ConnectTask();
						connectTask.execute(bluetoothSocket);
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}
				}
			} else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				setProgressBarIndeterminateVisibility(false);
			}
		}
	};
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE:
			if(Activity.RESULT_OK != resultCode) {
				Toast.makeText(MainActivity.this, "Bluetooth is not enabled", 
						Toast.LENGTH_SHORT).show();
				finish();
			}
			break;
		case REQUEST_DISCAVERABLE:
			if(Activity.RESULT_CANCELED == resultCode) {
				Toast.makeText(MainActivity.this, "Must be discoverable", 
						Toast.LENGTH_SHORT).show();
			} else {
				startListening();
			}
			break;
		default:
			break;
		}
	}

	/**
	 *  启动服务器端口并监听
	 */
	private void startListening() {
		AcceptTask acceptTask = new AcceptTask();
		acceptTask.execute(MY_UUID);
		setProgressBarIndeterminateVisibility(true);
	}
	
	private class AcceptTask extends AsyncTask<UUID, Void, BluetoothSocket> {
		@Override
		protected BluetoothSocket doInBackground(UUID... params) {
			String name = bluetoothAdapter.getName();
			try {
				// 监听时，将发现名设为具体值
				bluetoothAdapter.setName(SEARCH_NAME);
				bluetoothServer = 
					bluetoothAdapter.listenUsingRfcommWithServiceRecord("BluetoothRecipe", params[0]);
				BluetoothSocket connected = bluetoothServer.accept();
				// 重置蓝牙适配器名称
				bluetoothAdapter.setName(name);
				return connected;
			} catch (Exception e) {
				bluetoothAdapter.setName(name);
				Log.e(TAG, e.getMessage());
				return null;
			}
		}
		@Override
		protected void onPostExecute(BluetoothSocket result) {
			super.onPostExecute(result);
			if(result == null) {
				return;
			}
			bluetoothSocket = result;
			ConnectTask connectTask = new ConnectTask();
			connectTask.execute(bluetoothSocket);
		}
	}
	
	private class ConnectTask extends AsyncTask<BluetoothSocket, Void, String> {
		@Override
		protected String doInBackground(BluetoothSocket... params) {
			InputStream in = null;
			OutputStream out = null;
			try {
				// 发送数据
				out = params[0].getOutputStream();
				out.write(emailField.getText().toString().getBytes());
				// 接收其他数据
				in = params[0].getInputStream();
				byte[] buffer = new byte[1024];
				in.read(buffer);
				// 从结果创建一个空
				String result = new String(buffer);
				bluetoothSocket.close();
				return result.trim();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				return null;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Toast.makeText(MainActivity.this, result, 
					Toast.LENGTH_SHORT).show();
			setProgressBarIndeterminateVisibility(false);
		}
	}
	
	/**
	 *  反射读取远程设备的 UUID
	 */
	public ParcelUuid servicesFromDevice(BluetoothDevice device) {
		try {
			Class<?> cls = Class.forName("android.bluetooth.BluetoothDevice");
			Class<?>[] par = {};
			Method method = cls.getMethod("getUuids", par);
			Object[] args = {};
			ParcelUuid retval = (ParcelUuid) method.invoke(device, args);
			return retval;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return null;
		}
	}

}
