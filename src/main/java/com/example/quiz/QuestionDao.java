package com.example.quiz;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao {

	private final JdbcTemplate jdbc;
	
	public QuestionDao(final JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public Question findQuestionById(int id){
		return jdbc.queryForObject(
				"SELECT * FROM question WHERE id = ?;",
				new BeanPropertyRowMapper<>(Question.class),
				id
		);
	}
	
	public List<Question> findQuestionAtRandom() {
		return jdbc.query(
				"SELECT * FROM question ORDER BY random() LIMIT 10;",
				new BeanPropertyRowMapper<>(Question.class)
		);
	}
	
	public List<Question> findQuestionByGenreAtRandom(String genre){
		return jdbc.query(
				"SELECT * FROM question WHERE genre = ? ORDER BY random() LIMIT 10;",
				new BeanPropertyRowMapper<>(Question.class),
				genre
		);
	}
}
