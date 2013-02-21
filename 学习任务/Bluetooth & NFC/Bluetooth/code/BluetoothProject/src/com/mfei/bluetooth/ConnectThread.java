package com.mfei.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.Toast;

public class ConnectThread extends Thread {
	private BlueToothServer bts;

	@Override
	public void run() {
		// 取消可见
		MainActivity.bluetoothAdapter.cancelDiscovery();
		String str = MySurfaceView.vc_str.elementAt(MySurfaceView.deviceIndex);
		String[] values = str.split("\\*");// 这里split的参数是采用正则表达式规则
		// split此方法把字符串分割成多个字符串,我们这里分成了两个字符串
		// *号 之前一个，*号 之后 一个
		String address = values[1];// 这里就是取出连接设备的mac地址
		UUID uuid = UUID.fromString(MainActivity.SPP_UUID);// 蓝牙普遍支持SPP协议
		// 实例蓝牙设备
		BluetoothDevice btDevice = MainActivity.bluetoothAdapter.getRemoteDevice(address);
		try {
			MainActivity.bluetoothSocket = btDevice
					.createRfcommSocketToServiceRecord(uuid);
			MainActivity.bluetoothSocket.connect();
		} catch (IOException e) {
			Log.e("Himi", "Connected Error!");
			Toast.makeText(MainActivity.ma, "无法连接此设备!", 1000).show();
			e.printStackTrace();
			return;
		}
		// 当正常连接配对其他蓝牙设备后启动线程，一直监听报文数据
		MySurfaceView.gameState = MySurfaceView.CONNTCTED;
		bts = new BlueToothServer();
		bts.flag = true;
		bts.start();
	}

}

class BlueToothServer extends Thread {
	private InputStream ips;
	boolean flag;

	@Override
	public void run() {
		while (flag) {
			if (MySurfaceView.gameState == MySurfaceView.CONNTCTED) {
				try {
					// 没有接收到数据，这里一直处于阻塞状态
					ips = MainActivity.bluetoothSocket.getInputStream();
					byte[] buffer = new byte[1024];
					if (ips.read(buffer) != -1) {
						String str = new String(buffer, 0, 1);
						if (str.equals("w")) {// 上
							MySurfaceView.other_Arcy -= 5;
						} else if (str.equals("s")) {// 下
							MySurfaceView.other_Arcy += 5;
						} else if (str.equals("a")) {// 左
							MySurfaceView.other_Arcx -= 5;
						} else if (str.equals("d")) {// 右
							MySurfaceView.other_Arcx += 5;
						}
					}
				} catch (IOException e) {
					Log.e("Himi", "inPutStream is Error!!");
					e.printStackTrace();
				}
			}
		}
	}
}
