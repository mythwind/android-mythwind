package com.sqlite.first.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/***************************************************************
 * File name:     DatabaseHelper.java     Created on January. 31, 2013
 * Title:		   [SQLite learning  SQLite 学习]
 * Description: [基础提供程序主要是把数据访问提供的共有部分抽象出来，让子类继承，便于复用]
 * @author      [shusheng Wang  王树生] 
 * ------------------------------------------------------------
 * modification
 * number  date author reason
 * 1
 *-------------------------------------------------------------
***************************************************************/
public abstract class SQLiteBaseProvider {
	
	// 上下文
	protected Context context;
	/*
	 * SQLiteDatabase 类是核心类，用于管理和操作SQLite数据库，
	 * 创建、删除、执行的SQL命令以及其他常见的数据库管理任务，最终都将由这个类完成。　
	 */
	protected SQLiteDatabase sqLiteDatabase;
	// 创建数据库的类
	protected DatabaseHelper dbHelper;
	
	/**
	 * constructor 构造器
	 * @param context
	 */
	public SQLiteBaseProvider(Context context) {
		this.context = context;
	}
	
	/**
	 *  read from database 读取数据库
	 */
	protected void getReadableDatabase() {
		dbHelper = new DatabaseHelper(context);
		sqLiteDatabase = dbHelper.getReadableDatabase();
	}
	
	/**
	 *  write to database 写入数据库
	 */ 
	protected void getWritableDatabase() {
		dbHelper = new DatabaseHelper(context);
		sqLiteDatabase = dbHelper.getWritableDatabase();
	}
	
	/**
	 *  close database 关闭数据库
	 */
	protected void closeDatabase() {
		sqLiteDatabase.close();
		dbHelper.close();
	}
	
}
