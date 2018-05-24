package com.example.quiz;

import lombok.Data;

@Data
public class User {

	private int id;
	
	private String name;
	
	private String password;
	
	public User() {
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
}
