package com.example.quiz;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	private final JdbcTemplate jdbc;
	
	public UserDao(final JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	public User findUserByName(String userName) {
		try {
			return jdbc.queryForObject("SELECT * FROM users WHERE name = ?;", new BeanPropertyRowMapper<>(User.class), userName);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public User findUserById(int userId) {
		return jdbc.queryForObject("SELECT * FROM users WHERE id = ?;", new BeanPropertyRowMapper<>(User.class), userId);
	}
	
	public int insert(final User user) {
		return jdbc.update("INSERT INTO users (name, password) VALUES (?, ?)", user.getName(), user.getPassword());
	}
}
