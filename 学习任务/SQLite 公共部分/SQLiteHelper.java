package com.sise.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	/*   数据库名称和版本  */
	private static final String DB_NAME = "MobileSecretary.db";
	private static final int DB_VERSION = 1;
	
	public SQLiteHelper(Context context) {
		this(context, DB_NAME, null, DB_VERSION);
	}
	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	// 创建数据库建表
	@Override
	public void onCreate(SQLiteDatabase sqlitedatabase) {
		sqlitedatabase.execSQL(DBKey.Schedule.CREATETABLE_SCHEDULE);
	}

	// 数据库升级时调用
	@Override
	public void onUpgrade(SQLiteDatabase sqlitedatabase, int oldVersion, int newVersion) {
		// 删除表
		sqlitedatabase.execSQL("DROP TABLE IF EXISTS " + DBKey.Schedule.TABLE_SCHEDULE);
		// 重新创建
		onCreate(sqlitedatabase);
	}

}
