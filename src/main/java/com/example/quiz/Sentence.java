package com.example.quiz;

import lombok.Data;

@Data
public class Sentence {

	private String id;
	
	private String genre;
	
	private String mainText;
	
	private String firstText;
	
	private String secondText;
	
	private String answer;
	
	private boolean result;
	
	private String rightAnswer;
	
	public Sentence() {
	}
	
	public Sentence(
			int id,
			String mainText,
			String genre,
			String firstText,
			String secondText,
			String answer,
			boolean result,
			String rightAnswer) {
		this.id = String.valueOf(id);
		this.mainText = mainText;
		this.genre = genre;
		this.firstText = firstText;
		this.secondText = secondText;
		this.answer = answer;
		this.result = result;
		this.rightAnswer = rightAnswer;
	}
}
