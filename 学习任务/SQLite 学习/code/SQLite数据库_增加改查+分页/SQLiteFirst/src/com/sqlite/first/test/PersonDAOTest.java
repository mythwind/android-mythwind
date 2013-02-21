package com.sqlite.first.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.sqlite.first.db.PersonDAO;
import com.sqlite.first.domain.Person;

public class PersonDAOTest extends AndroidTestCase {

	private static final String TAG = "PersonDAOTest";

	// 保存数据
	public void testAddPerson() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		for (int i = 1; i <= 10; i++) {
			personDAO.addPerson(new Person("test" + i, (short) i));
		}
	}

	// 根据ID查找数据
	public void testFindPersonById() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		Person person = personDAO.findPersonById(1);
		System.out.println(person.toString());
		Log.i(TAG, person.toString());
	}

	// 根据ID查找数据
	public void testFindAllPerson() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		List<Person> persons = personDAO.findAllPerson();
		for (Person person : persons) {
			System.out.println(person.toString());
			Log.i(TAG, person.toString());
		}
	}

	// 根据ID修改数据
	public void testUpdatePerson() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		Person person = personDAO.findPersonById(19);
		System.out.println(person.toString());
		person.setName("王树生");
		person.setAge((short) 22);
		personDAO.updatePerson(person);
		person = personDAO.findPersonById(19);
		System.out.println(person.toString());
		Log.i(TAG, person.toString());
	}
	
	// 根据ID删除数据
	public void testDeletePerson() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		personDAO.deletePerson(11, 12);
		Person person = personDAO.findPersonById(11);
		System.out.println(person == null);
	}
	
	// 分页查询
	public void testGetScrollData() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		List<Person> persons = personDAO.getScrollData(1, 5);
		for (Person person : persons) {
			System.out.println(person.toString());
		}
		persons = personDAO.getScrollData(8, 6);
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}
	
	public void testGetCount() {
		PersonDAO personDAO = new PersonDAO(this.getContext());
		System.out.println(personDAO.getCount());
	}

}
