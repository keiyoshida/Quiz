package com.example.quiz;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserData {

	private int score;
	
	private String genre;
	
	private Timestamp startDateTime;
	
	public UserData(int score, String genre, Timestamp startDateTime) {
		this.score = score;
		this.genre = genre;
		this.startDateTime = startDateTime;
	}
}
