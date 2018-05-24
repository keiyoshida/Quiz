package com.example.quiz;

import lombok.Data;

@Data
public class Question {

	private int id;
	
	private String genre;
	
	private String mainText;
	
	private String firstText;
	
	private String secondText;
	
	private String answer;
}
