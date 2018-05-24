package com.example.quiz;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Score {

	private int userId;
	
	private int score;
	
	private String genre;
	
	private Timestamp startDateTime;
}
