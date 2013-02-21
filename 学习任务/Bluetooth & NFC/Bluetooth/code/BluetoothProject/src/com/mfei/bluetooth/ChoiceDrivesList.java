package com.mfei.bluetooth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class ChoiceDrivesList extends Activity {
	// 所有蓝牙设备的名字
	private String[] names;
	// 提示
	private Toast toast;
	// 对话框显示当前搜索到的蓝牙设备
	private AlertDialog.Builder dialog;

	public ChoiceDrivesList() {
		names = new String[MySurfaceView.vc_str.size()];
		for (int i = 0; i < MySurfaceView.vc_str.size(); i++) {
			names[i] = MySurfaceView.vc_str.elementAt(i);
		}
	}

	public void displayToast(String str, int type) {
		try {
			toast = null;
			if (type == 0) {
				toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
			} else {
				toast = Toast.makeText(this, str, Toast.LENGTH_LONG);
			}
			toast.setGravity(Gravity.TOP, 0, 220);
			toast.show();
		} catch (Exception e) {
			Log.e("ChoiceDrivesList", e.getMessage());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new AlertDialog.Builder(ChoiceDrivesList.this);
		dialog.setIcon(android.R.drawable.btn_dialog);
		dialog.setSingleChoiceItems(names, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						MySurfaceView.deviceIndex = which;
					}
				})
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("连接", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						displayToast("正在连接设备："
								+ MySurfaceView.vc_str.elementAt(MySurfaceView.deviceIndex),
								1);
						MySurfaceView.gameState = MySurfaceView.CONNTCTING;
						new ConnectThread().start();
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						finish();
					}
				}).setTitle("请选择连接设备!");
		dialog.show();
	}

}
