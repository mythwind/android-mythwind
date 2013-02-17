package com.sqlite.first.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sqlite.first.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/***************************************************************
 * File name:     DatabaseHelper.java     Created on January. 31, 2013
 * Title:		   [SQLite learning SQLite 学习]
 * Description: [database create and update  创建和升级数据库的方法]
 * @author      [shusheng Wang   王树生] 
 * ------------------------------------------------------------
 * modification
 * number  date author reason
 * 1
 *-------------------------------------------------------------
***************************************************************/
public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "DatabaseHelper";
	// 数据库名称
	private static final String DBNAME = "first.db";
	// 数据库版本
	private static final int VERSION = 1;
	// 上下文
	private Context mContext = null;

	/**
	 *  constructor   构造方法
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		this.mContext = context;
	}
	
	/**
	 *   创建数据库时调用，在安装应用程序的时候会执行此方法
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("DatabaseHelper.onCreate");
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			// 开启事务
			db.beginTransaction();
			// 读取 raw 资源的 sql 语句文件
			inputStreamReader = new InputStreamReader(
					mContext.getResources().openRawResource(R.raw.initdatabase));
			bufferedReader = new BufferedReader(inputStreamReader);
			String updateSql = "";
			// 按行读取，如果读取到的不为空，则执行 sql 语句
			while (null != (updateSql = bufferedReader.readLine())) {
				System.out.println(updateSql);
				if (!TextUtils.isEmpty(updateSql)) {
					Log.d(TAG, updateSql);
					db.execSQL(updateSql);
				}
			}
			// 事务处理成功
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			try {
				// 关闭事务
				db.endTransaction();
				// 关闭数据流
				if(null != inputStreamReader) {
					inputStreamReader.close();
				}
				if(null != bufferedReader) {
					bufferedReader.close();
				}
				if(null != mContext) {
					mContext = null;
				}
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
	}

	/**
	 *  升级数据库时调用
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("DatabaseHelper.onUpgrade");
	}

}
