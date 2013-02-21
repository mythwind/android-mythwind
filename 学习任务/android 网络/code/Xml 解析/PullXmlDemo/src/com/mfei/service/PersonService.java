package com.mfei.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.mfei.domain.Person;

public class PersonService {
	
	/**
	 *  获取数据
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static List<Person> getPersons(InputStream xml) throws Exception {
		List<Person> persons = null;
		Person person = null;
		// 获取到 XmlPullParser
		XmlPullParser pullParser = Xml.newPullParser();
		// 为 Pull 解析器设置要解析的 xml 数据
		pullParser.setInput(xml, "UTF-8");
		int event = pullParser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			// xml 文档开始数据
			case XmlPullParser.START_DOCUMENT:
				persons = new ArrayList<Person>();
				break;
			case XmlPullParser.START_TAG:
				if("person".equals(pullParser.getName())) {
					int id = new Integer(pullParser.getAttributeValue(0));
					person = new Person();
					person.setId(id);
				}
				if("name".equals(pullParser.getName())) {
					String name = pullParser.nextText(); // <name>123</name>  取得123
					person.setName(name);
				}
				if("age".equals(pullParser.getName())) {
					int age = new Integer(pullParser.nextText());// <age>123</age>  取得123
					person.setAge(age);
				}
				break;
			case XmlPullParser.END_TAG:
				if("person".equals(pullParser.getName())) {
					persons.add(person);
					person = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return persons;
	}
	
	/**
	 *  保存数据
	 * @param persons
	 * @param os
	 * @throws Exception
	 */
	public static void saveData(List<Person> persons, OutputStream os) throws Exception {
		// 得到序列化器
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(os, "UTF-8");
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, "persons");
		for (Person person : persons) {
			serializer.startTag(null, "person");
			serializer.attribute(null, "id", person.getId().toString());
			serializer.startTag(null, "name");
			serializer.text(person.getName());
			serializer.endTag(null, "name");
			serializer.startTag(null, "age");
			serializer.text(person.getAge().toString());
			serializer.endTag(null, "age");
			serializer.endTag(null, "person");
		}
		serializer.endTag(null, "persons");
		serializer.endDocument();
		os.flush();
		os.close();
	}
	
}
