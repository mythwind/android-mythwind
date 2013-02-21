package com.itcast.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.itcast.domain.Person;
import com.itcast.service.DBHelper;
import com.itcast.service.OtherPersonService;

public class OtherPersonServiceTest extends AndroidTestCase {
	
	public void testCreateDB() {
		DBHelper dbHelper = new DBHelper(getContext());
		dbHelper.getWritableDatabase();
	}
	public void testSave() {
		OtherPersonService service = new OtherPersonService(this.getContext());
		for (int i = 0; i < 10; i++) {
			Person person = new Person("wang" + i, "1508860299" + i, 100);
			service.save(person);
		}
	}
	public void testDelete() {
		OtherPersonService service = new OtherPersonService(this.getContext());
		service.delete(1);
	}
	public void testUpdate() {
		OtherPersonService service = new OtherPersonService(this.getContext());
		Person person = service.findById(2);
		person.setName("wangshusheng");
		service.update(person);
	}
	public void testFindById() {
		OtherPersonService service = new OtherPersonService(this.getContext());
		Person person = service.findById(2);
		System.out.println(person.toString());
	}
	public void testFindScrollData() {
		OtherPersonService service = new OtherPersonService(this.getContext());
		List<Person> persons = service.findScrollData(2, 5);
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}
	
}
