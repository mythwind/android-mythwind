package com.mfei.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.mfei.domain.Person;
import com.mfei.service.PersonService;

public class PersonServiceTest extends AndroidTestCase {
	
	public void testPersons() throws Exception {
		InputStream xml = this.getClass().getClassLoader().getResourceAsStream("person.xml");
		List<Person> persons = PersonService.getPersons(xml);
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}
	
	public void testSave() throws Exception {
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person(1, "liu", 30));
		persons.add(new Person(2, "de", 31));
		persons.add(new Person(3, "hua", 32));
		// <°ü>/files
		File file = new File(getContext().getFilesDir(), "itcast.xml");
		FileOutputStream os = new FileOutputStream(file);
		PersonService.saveData(persons, os);
	}
	
}
