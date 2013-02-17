package com.sise.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 *  基础提供程序主要是把数据访问提供的共有部分抽象出来，让子类继承，便于复用
 * @author Neu
 */
public abstract class SQLiteBaseProvider {
	protected Context context;
	protected SQLiteDatabase sqLiteDatabase;
	protected SQLiteHelper sqliteHelper;
	
	protected SQLiteBaseProvider(Context context) {
		this.context = context;
	}
	
	// 读取数据库
	protected void getReadableDatabase() {
		sqliteHelper = new SQLiteHelper(context);
		sqLiteDatabase = sqliteHelper.getReadableDatabase();
	}
	
	// 写入数据库
	protected void getWritableDatabase() {
		sqliteHelper = new SQLiteHelper(context);
		sqLiteDatabase = sqliteHelper.getWritableDatabase();
	}
	
	//  关闭数据库
	protected void closeDatabase() {
		sqLiteDatabase.close();
		sqliteHelper.close();
	}
	
}
