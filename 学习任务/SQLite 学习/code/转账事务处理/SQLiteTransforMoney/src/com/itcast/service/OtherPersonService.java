package com.itcast.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itcast.domain.Person;

public class OtherPersonService {
private DBHelper dbHelper;
	
	public OtherPersonService(Context context) {
		this.dbHelper = new DBHelper(context);
	}
	
	/**
	 *  添加记录
	 * @param person
	 */
	public void save(Person person) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", person.getName());
		values.put("phone", person.getPhone());
		values.put("amount", person.getAmount());
		db.insert("person", null, values);
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
		ContentValues values = new ContentValues();
		values.put("name", person.getName());
		values.put("phone", person.getPhone());
		values.put("amount", person.getAmount());
		db.update("person", values, "personid = ?", new String[] { person.getId().toString() } );
	}
	
	/**
	 *  根据 ID 查询记录
	 * @param id
	 * @return
	 */
	public Person findById(Integer id) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("person", null, "personid = ?", new String[] { id.toString() }, null, null, null);
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
		Cursor cursor = db.query("person", null, null, null, null, null, "personid asc", offset + ", " + maxResult);
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
		Cursor cursor = db.query("person", new String[] { "count(*)" }, null, null, null, null, null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
	
}
