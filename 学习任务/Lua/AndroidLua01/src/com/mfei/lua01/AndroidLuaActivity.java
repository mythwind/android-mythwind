package com.mfei.lua01;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidLuaActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView txtView = (TextView) this.findViewById(R.id.textview);
		
		LuaState L = LuaStateFactory.newLuaState();
		L.openLibs();
		L.LdoString(getLuaString("lua/hello.lua"));
		// 实例1.Java调用lua函数
		L.getField(LuaState.LUA_GLOBALSINDEX, "plus");
		// 取得lua里的plus函数
		L.pushNumber(12);
		// 传递参数1给plus函数
		L.pushNumber(2132);
		// 传递参数2给plus函数
		L.call(2, 1);
		// 调用plus函数
		L.setField(LuaState.LUA_GLOBALSINDEX, "a");
		// 将函数的结果保存到一个参数a中
		LuaObject obj = L.getLuaObject("a");
		// 取得参数a
		txtView.setText("" + obj.getString());
		// 打印参数a的值到TextView中
		// lua调用Java对象
		Value value = new Value();
		L.getField(LuaState.LUA_GLOBALSINDEX, "heihei");
		// 获取(或者说，是定位？)lua的heihei函数
		try {
			L.pushObjectValue(value);
			// 将value对象传递给heihei函数
		} catch (LuaException e) {
		}
		L.call(1, 1);
		// 调用heihei函数
		L.setField(LuaState.LUA_GLOBALSINDEX, "v");
		// 函数结果保存到参数v中
		LuaObject v = L.getLuaObject("v");
		// 读取参数v
		try {
			txtView.setText("" + v.getObject());
			// 打印日志
		} catch (LuaException e) {
		}
		L.close(); // 关闭lua
	}

	// 嘿嘿，注意咯，精华来了~这个函数就是用来读取lua文件，
	// 然后保存到一个字符串里（当然，你可以用其他方法，只要能保存成字符串）
	private String getLuaString(String resPath) {
		InputStream isread = null;
		byte[] luaByte = new byte[1];
		try {
			// 就是这里了，我们把lua 都放到asset目录下，这样系统就 //不会找不到文件路径了，哼~
			isread = this.getAssets().open(resPath);
			int len = isread.available();
			luaByte = new byte[len];
			isread.read(luaByte);
		} catch (IOException e1) {
		} finally {
			if (isread != null) {
				try {
					isread.close();
				} catch (IOException e) {
				}
			}
		}
		return EncodingUtils.getString(luaByte, "GBK");
	}

	// 一个简单的内部类
	class Value {
		private int num = 0;
		public void inc() {
			num++;
		}
		public String toString() {
			return "num is " + num;
		}
	}

}