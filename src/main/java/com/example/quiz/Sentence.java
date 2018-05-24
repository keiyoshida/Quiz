package com.example.quiz;

import lombok.Data;

@Data
public class Sentence {

	private int id;
	
	private String genre;
	
	private String mainText;
	
	private String firstText;
	
	private String secondText;
	
	private String answer;
	
	private boolean result;
	
	private String rightAnswer;
	
	public Sentence(
			int id,
			String mainText,
			String genre,
			String firstText,
			String secondText,
			String answer,
			boolean result,
			String rightAnswer) {
		this.id = id;
		this.mainText = mainText;
		this.firstText = firstText;
		this.secondText = secondText;
		this.answer = answer;
		this.result = result;
		this.rightAnswer = rightAnswer;
	}
}
