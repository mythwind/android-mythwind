package com.sqlite.first.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.sqlite.first.domain.Person;

/***************************************************************
 * File name:     DatabaseHelper.java     Created on January. 31, 2013
 * Title:		   [SQLite learning  SQLite 学习]
 * Description: [handle data of database 数据库的增删改查类]
 * @author      [shusheng Wang  王树生] 
 * ------------------------------------------------------------
 * modification
 * number  date author reason
 * 1
 *-------------------------------------------------------------
***************************************************************/
public class PersonDAO extends SQLiteBaseProvider {

	/**
	 * constructor  构造器
	 * @param context
	 */
	public PersonDAO(Context context) {
		super(context);
	}

	/**
	 * add person  添加人员
	 * @param person
	 */
	public void addPerson(Person person) {
		System.out.println("PersonDAO.addPerson");
		// open databases   打开数据库
		getWritableDatabase();
		// execute insert SQL  执行添加人员的SQL语句
		sqLiteDatabase.execSQL("insert into person(name,age) values(?,?)",
				new Object[] { person.getName(), person.getAge() });
		//  close  databases   关闭数据库
		closeDatabase();
	}

	/**
	 *  update person info  更新人员信息
	 * @param person
	 */
	public void updatePerson(Person person) {
		System.out.println("PersonDAO.updatePerson");
		getWritableDatabase();
		// execute update SQL 根据 personid 更新人员信息
		sqLiteDatabase.execSQL(
				"update person set name=?,age=? where personid=?",
				new Object[] { person.getName(), person.getAge(),
						person.getPersonId() });
		closeDatabase();
	}
	
	/**
	 *  find all person  查找所有人员
	 * @return
	 */
	public List<Person> findAllPerson() {
		System.out.println("PersonDAO.findAllPerson");
		getReadableDatabase();
		// execute SQL
		// 执行查询语句，将结果集放入 Cursor 游标
		List<Person> persons = new ArrayList<Person>();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from person", null);
		while (cursor.moveToNext()) {
			// 从 cursor 取出人员信息放入 Person 实体类
			persons.add(new Person(cursor.getInt(0), cursor.getString(1),
					cursor.getShort(2)));
		}
		cursor.close();
		closeDatabase();
		return persons;
	}

	/**
	 *  find person by personId 根据 personid 查找人员
	 * @param id
	 * @return
	 */
	public Person findPersonById(Integer id) {
		System.out.println("PersonDAO.findPersonById");
		getReadableDatabase();
		// execute SQL
		// 执行查询语句，将结果集放入 Cursor 游标
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from person where personid=?",
				new String[] { String.valueOf(id) });
		Person person = null;
		if (cursor.moveToNext()) {
			// 从 cursor 取出人员信息放入 Person 实体类
			person = new Person(cursor.getInt(0), cursor.getString(1),
					cursor.getShort(2));
		}
		cursor.close();
		closeDatabase();
		return person;
	}

	/**
	 *  delete  person 删除人员信息，可以一次删除多个人员
	 * @param ids 要删除的人员的ID
	 */
	@SuppressWarnings("unused")
	public void deletePerson(Integer... ids) {
		System.out.println("PersonDAO.deletePerson");
		getWritableDatabase();
		// 根据 ID 删除 person
		if (ids.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (Integer id : ids) {
				sb.append("?").append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sqLiteDatabase.execSQL(
					"delete from person where personid in(" + sb + ")", (Object[]) ids);
		}
		closeDatabase();
	}
	
	/**
	 *  分页代码
	 * @param startResult   start record 开始记录数，从0开始
	 * @param maxResult     records of every page 每页的记录数
	 * @return
	 */
	public List<Person> getScrollData(int startResult, int maxResult) {
		System.out.println("PersonDAO.getScrollData");
		List<Person> persons = new ArrayList<Person>();
		getWritableDatabase();
		// 执行分页的SQL语句
		Cursor cursor=	sqLiteDatabase.rawQuery("select * from person limit ?,?",
				new String[]{String.valueOf(startResult),String.valueOf(maxResult)});
		// cursor 有记录
		while(cursor.moveToNext()){
			// 取出记录，放入 Person 类，然后将 Person 添加到 List<Person> 列表
			persons.add(new Person(cursor.getInt(0),cursor.getString(1),
				cursor.getShort(2)));
		}
		cursor.close();
		closeDatabase();
		return persons;
	}
	
	/**
	 *  get all records 取得所有的记录数
	 * @return 
	 */
	public int getCount() {
		System.out.println("PersonDAO.getCount");
		int count = 0;
		getWritableDatabase();
		// 获取数据库数据放入游标 Cursor
		Cursor cursor=	sqLiteDatabase.rawQuery("select * from person ", null);
		// 获取游标 Cursor 的记录数
		count = cursor.getCount();
		return count;
	}

}
