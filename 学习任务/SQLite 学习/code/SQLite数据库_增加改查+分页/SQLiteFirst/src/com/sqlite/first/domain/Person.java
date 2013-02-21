package com.sqlite.first.domain;

/***************************************************************
 * File name:     Person.java     Created on January. 31, 2013
 * Title:		   [SQLite learning SQLite 学习]
 * Description: [database entity  数据库实体类]
 * @author      [shusheng Wang   王树生] 
 * ------------------------------------------------------------
 * modification
 * number  date author reason
 * 1
 *-------------------------------------------------------------
***************************************************************/
public class Person {
	
	private int personId;  //  id
	private String name;   //  姓名
	private short age;      //  年龄
	
	/**
	 *  default  constructor 默认构造器
	 */
	public Person() {
	}
	/**
	 * constructor 带参数的构造器
	 * @param name
	 * @param age
	 */
	public Person(String name, short age) {
		this.name = name;
		this.age = age;
	}
	/**
	 *  constructor 带参数的构造器
	 * @param personId
	 * @param name
	 * @param age
	 */
	public Person(int personId, String name, short age) {
		this.personId = personId;
		this.name = name;
		this.age = age;
	}

	/**
	 *  setter/getter method
	 *  提供的setter/getter方法
	 * @return
	 */
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getAge() {
		return age;
	}
	public void setAge(short age) {
		this.age = age;
	}
	
	/**
	 *  override toString 
	 *  复写 toString 方法
	 */
	@Override
	public String toString() {
		return "personId=" + personId + ", name=" + name + ", age=" + age;
	}
	
}
