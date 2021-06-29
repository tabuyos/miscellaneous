/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.mockito.repository;

import com.tabuyos.mockito.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author tabuyos
 */
@Repository
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public UserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<User> getAllUser() {
    return jdbcTemplate.query("select * from user_details", new BeanPropertyRowMapper<>(User.class));
  }

  public boolean insert() {
    System.out.println("user insert success");
    return true;
  }
}
