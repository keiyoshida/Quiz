package com.example.quiz;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreDao {

	private final JdbcTemplate jdbc;
	
	public ScoreDao(final JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public List<Score> findRecentlyScores(String userName){
		return jdbc.query(
				"SELECT t1.* FROM score t1, users t2 WHERE t1.userId = t2.id AND t2.name = ? ORDER BY startDateTime DESC LIMIT 10;",
				new BeanPropertyRowMapper<>(Score.class),
				userName
		);
	}
	
	public int insert(final Score score) {
		return jdbc.update(
				"INSERT INTO score (userId, score, genre, startDateTime) VALUES (?, ?, ?, ?);",
				score.getUserId(),
				score.getScore(),
				score.getGenre(),
				score.getStartDateTime()
		);
	}
}
