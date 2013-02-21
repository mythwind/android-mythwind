package com.itcast.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itcast.domain.Person;

public class PersonService {
	private DBHelper dbHelper;
	
	public PersonService(Context context) {
		this.dbHelper = new DBHelper(context);
	}
	
	
	public void transfor() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction(); // 开启事务
		try {
			db.execSQL("update person set amount = amount - 10 where personid=2");
			db.execSQL("update person set amount = amount + 10 where personid=3");
			// 这一句话一定要，否则事务不起作用
			db.setTransactionSuccessful();  // 设置事务的标识为 True
		} finally {
			db.endTransaction(); // 结束事务
			// 事务的提交或者回滚是由事务的标识决定的，如果事务标识为True则提交，否则回滚。默认为False
		}
	}
	
	/**
	 *  添加记录
	 * @param person
	 */
	public void save(Person person) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into person(name, phone, amount) values(?, ?, ?)", 
				new Object[] { person.getName(), person.getPhone(), person.getAmount() } );
	}
	
	/**
	 * 删除记录
	 * @param id
	 */
	public void delete(Integer id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from person where personid = ?", new Object[] { id });
	}
	
	/**
	 * 更新记录
	 * @param person
	 */
	public void update(Person person) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("update person set name = ?, phone = ?, amount = ? where personid = ?", 
				new Object[] { person.getName(), person.getPhone(), person.getAmount(), person.getId() });
	}
	
	/**
	 *  根据 ID 查询记录
	 * @param id
	 * @return
	 */
	public Person findById(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person where personid = ?", new String[] { id.toString() } );
		if(cursor.moveToFirst()) {
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			return new Person(personid, name, phone, amount);
		}
		cursor.close();
		return null;
	}
	
	/**
	 *  分页查询
	 * @param offset 跳过前面多少条记录
	 * @param maxResult 每页多少条记录
	 * @return
	 */
	public List<Person> findScrollData(int offset, int maxResult) {
		List<Person> persons = new ArrayList<Person>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person order by personid asc limit ?, ?", 
				new String[] { String.valueOf(offset), String.valueOf(maxResult) } );
		while(cursor.moveToNext()) {
			int personid = cursor.getInt(cursor.getColumnIndex("personid"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			int amount = cursor.getInt(cursor.getColumnIndex("amount"));
			persons.add(new Person(personid, name, phone, amount));
		}
		cursor.close();
		return persons;
	}
	
	/**
	 *  获取记录总数
	 * @return
	 */
	public long getCount() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from person", null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
	
}
