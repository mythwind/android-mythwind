package com.apress;

import java.nio.charset.Charset;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	// NFC 适配器
	NfcAdapter mNfcAdapter;
	TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		textView = (TextView) findViewById(R.id.textView);
		
		// 检测是否有NFC适配器，并且获得手机设备默认的NfcAdapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		// 手机设备不支持 NFC
		if (mNfcAdapter == null) {
			Toast.makeText(this, "设备不支持NFC", Toast.LENGTH_LONG).show();
			textView.setText("设备不支持NFC");
			return;
		}
		// 手机设备没有启用NFC功能
		if (!mNfcAdapter.isEnabled()) {
			Toast.makeText(this, "请在系统设置中先启用NFC功能", Toast.LENGTH_LONG).show();
			textView.setText("请在系统设置中先启用NFC功能");
			return;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 得到是否检测到ACTION_NDEF_DISCOVERED触发
		// NfcAdapter.ACTION_TAG_DISCOVERED
		// NfcAdapter.ACTION_TECH_DISCOVERED
		// NDEF：NFC 数据交换格式
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
			// 处理扫描到的 NdefMessage
			processIntent(getIntent());
		}
	}

	// 字符序列转换为16进制字符串
	private String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("0x");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			System.out.println(buffer);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString();
	}

	// 关键处理函数，处理扫描到的NdefMessage
	private void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// 获取扫描到的一组 NdefMessage 的第一个值
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		// record 0 contains the MIME type, record 1 is the AAR, if present
		textView.setText(new String(msg.getRecords()[0].getPayload()));

		// 取出封装在intent中的TAG
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		for (String tech : tagFromIntent.getTechList()) {
			System.out.println(tech);
		}
		boolean auth = false;
		// 非接触式读卡器，读取TAG
		MifareClassic mfc = MifareClassic.get(tagFromIntent);
		try {
			String metaInfo = "";
			// 启用I/O操作从这个TagTechnology对象来读取数据
			mfc.connect();
			// 获取TAG的类型
			int type = mfc.getType();
			// 获取TAG中包含的扇区数
			int sectorCount = mfc.getSectorCount();
			String typeS = "";
			switch (type) {
			// 经典卡
			case MifareClassic.TYPE_CLASSIC:
				typeS = "TYPE_CLASSIC";
				break;
			case MifareClassic.TYPE_PLUS:
				typeS = "TYPE_PLUS";
				break;
			case MifareClassic.TYPE_PRO:
				typeS = "TYPE_PRO";
				break;
			// 未知类型是兼容卡
			case MifareClassic.TYPE_UNKNOWN:
				typeS = "TYPE_UNKNOWN";
				break;
			}
			metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
					+ mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
			for (int j = 0; j < sectorCount; j++) {
				// Authenticate a sector with key A.
				auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT);
				int bCount;
				int bIndex;
				if (auth) {
					metaInfo += "Sector " + j + ":验证成功\n";
					// 读取扇区中的块
					bCount = mfc.getBlockCountInSector(j);
					bIndex = mfc.sectorToBlock(j);
					for (int i = 0; i < bCount; i++) {
						byte[] data = mfc.readBlock(bIndex);
						metaInfo += "Block " + bIndex + " : "
								+ bytesToHexString(data) + "\n";
						bIndex++;
					}
				} else {
					metaInfo += "Sector " + j + ":验证失败\n";
				}
			}
			textView.setText(metaInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 重载Activity类方法处理当新Intent到来事件
	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		return mimeRecord;
	}

}
