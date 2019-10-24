package com.daum.jeremy6019.csv;

import java.util.Date;

public class Player {

	private String name;
	private int age;
	private Date birthday;
	
	
	public Player() {
		super();
	}
	
	public Player(String name, int age, Date birthday) {
		super();
		this.name = name;
		this.age = age;
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "Player [name=" + name + ", age=" + age + ", birthday=" + birthday + "]";
	}

	
}
