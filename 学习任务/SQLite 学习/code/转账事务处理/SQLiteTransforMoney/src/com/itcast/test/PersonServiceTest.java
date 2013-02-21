package com.itcast.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.itcast.domain.Person;
import com.itcast.service.DBHelper;
import com.itcast.service.PersonService;

public class PersonServiceTest extends AndroidTestCase {

	public void testCreateDB() {
		DBHelper dbHelper = new DBHelper(getContext());
		dbHelper.getWritableDatabase();
	}
	public void testSave() {
		PersonService service = new PersonService(this.getContext());
		for (int i = 0; i < 10; i++) {
			Person person = new Person("wang" + i, "1508860299" + i, 100);
			service.save(person);
		}
	}
	public void testDelete() {
		PersonService service = new PersonService(this.getContext());
		service.delete(1);
	}
	public void testUpdate() {
		PersonService service = new PersonService(this.getContext());
		Person person = service.findById(2);
		person.setName("wangshusheng");
		service.update(person);
	}
	public void testFindById() {
		PersonService service = new PersonService(this.getContext());
		Person person = service.findById(2);
		System.out.println(person.toString());
	}
	public void testFindScrollData() {
		PersonService service = new PersonService(this.getContext());
		List<Person> persons = service.findScrollData(0, 5);
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}
	
	public void testTransfor() {
		PersonService service = new PersonService(this.getContext());
		service.transfor();
	}
	
	public void testUpdateAmount() {
		PersonService service = new PersonService(this.getContext());
		Person persons1 = service.findById(2);
		Person persons2 = service.findById(3);
		persons1.setAmount(150);
		persons2.setAmount(50);
		service.update(persons1);
		service.update(persons2);
	}
	
}
